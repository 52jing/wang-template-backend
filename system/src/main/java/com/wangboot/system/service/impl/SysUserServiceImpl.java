package com.wangboot.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.core.auth.authorization.IAuthorizer;
import com.wangboot.core.auth.authorization.authorizer.GrantAllAuthorizer;
import com.wangboot.core.auth.authorization.authorizer.SimpleAuthorizer;
import com.wangboot.core.auth.authorization.resource.ApiResource;
import com.wangboot.core.auth.user.IUserModel;
import com.wangboot.core.cache.CacheUtil;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.reliability.blacklist.IBlacklistHolder;
import com.wangboot.core.utils.StrUtils;
import com.wangboot.core.web.param.IParamConfig;
import com.wangboot.framework.ParamConstants;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.framework.utils.WUtils;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.system.entity.*;
import com.wangboot.system.entity.relation.SysUserRoleRel;
import com.wangboot.system.entity.relation.table.SysUserRoleRelTableDef;
import com.wangboot.system.entity.table.SysRoleTableDef;
import com.wangboot.system.entity.table.SysUserTableDef;
import com.wangboot.system.mapper.SysUserMapper;
import com.wangboot.system.service.SysPolicyService;
import com.wangboot.system.service.SysRoleService;
import com.wangboot.system.service.SysUserRoleRelService;
import com.wangboot.system.service.SysUserService;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 服务层实现。
 *
 * @author wwtg99
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService {

  private static final String CACHE_PREFIX = "user_cache_";
  private static final String LOCK_PREFIX = "user_lock_";

  /** 缓存10秒 */
  private static final long CACHE_TTL = 10000;

  private final PasswordEncoder passwordEncoder;

  private final SysUserRoleRelService userRoleRelService;

  private final SysRoleService roleService;

  private final SysPolicyService policyService;

  private final IBlacklistHolder blacklistHolder;

  private final IParamConfig paramConfig;

  @Override
  @Nullable
  public IUserModel getUserModelByUsername(String username) {
    if (!StringUtils.hasText(username)) {
      return null;
    }
    // 先获取缓存，没有缓存从数据库获取
    return CacheUtil.getOrSet(
        CACHE_PREFIX + username,
        SysUser.class,
        CACHE_TTL,
        () -> getOne(query().where(SysUserTableDef.SYS_USER.USERNAME.eq(username))));
  }

  @Override
  @Nullable
  public IUserModel getUserModelById(String id) {
    if (!StringUtils.hasText(id)) {
      return null;
    }
    // 先获取缓存，没有缓存从数据库获取
    return CacheUtil.getOrSet(CACHE_PREFIX + id, SysUser.class, CACHE_TTL, () -> this.getById(id));
  }

  @Override
  public boolean setPassword(
      @Nullable IUserModel user, String newPassword, boolean passwordChangeFlag) {
    if (Objects.isNull(user)) {
      return false;
    }
    // 检查密码策略
    if (!WUtils.getPasswordStrategy().checkStrategies(newPassword)) {
      throw new ErrorCodeException(ErrorCode.PASSWORD_CHECK_FAILED);
    }
    return this.updateChain()
        .set(SysUserTableDef.SYS_USER.PASSWORD, passwordEncoder.encode(newPassword))
        .set(SysUserTableDef.SYS_USER.NEED_CHANGE_PWD, passwordChangeFlag)
        .where(SysUserTableDef.SYS_USER.ID.eq(user.getUserId()))
        .update();
  }

  @Override
  public boolean verifyPassword(@Nullable IUserModel userModel, String pwd) {
    if (Objects.isNull(userModel)) {
      return false;
    }
    return this.passwordEncoder.matches(pwd, userModel.getPassword());
  }

  @Override
  public boolean logout(@Nullable IUserModel user) {
    if (Objects.isNull(user)) {
      return false;
    }
    // 清除缓存
    CacheUtil.remove(CACHE_PREFIX + user.getUserId());
    return true;
  }

  @Override
  public boolean enableUser(String userId) {
    if (!StringUtils.hasText(userId)) {
      return false;
    }
    return this.updateChain()
        .set(SysUserTableDef.SYS_USER.ACTIVE, true)
        .where(SysUserTableDef.SYS_USER.ID.eq(userId))
        .update();
  }

  @Override
  public boolean disableUser(String userId) {
    if (!StringUtils.hasText(userId)) {
      return false;
    }
    return this.updateChain()
        .set(SysUserTableDef.SYS_USER.ACTIVE, false)
        .where(SysUserTableDef.SYS_USER.ID.eq(userId))
        .update();
  }

  @Override
  public boolean lockUser(String userId) {
    if (!StringUtils.hasText(userId)) {
      return false;
    }
    Long lockSecs =
        StrUtils.getLong(
            this.paramConfig.getParamConfig(ParamConstants.LOGIN_FAILED_LOCK_SECONDS_KEY));
    if (Objects.nonNull(lockSecs)) {
      this.blacklistHolder.addBlacklist(LOCK_PREFIX + userId, lockSecs);
    }
    return true;
  }

  @Override
  public boolean unlockUser(String userId) {
    if (!StringUtils.hasText(userId)) {
      return false;
    }
    this.blacklistHolder.removeBlacklist(LOCK_PREFIX + userId);
    return true;
  }

  @Override
  public boolean isUserLocked(String userId) {
    if (!StringUtils.hasText(userId)) {
      return false;
    }
    return this.blacklistHolder.inBlacklist(LOCK_PREFIX + userId);
  }

  @Override
  @Nullable
  public IAuthorizer getAuthorizer(@Nullable IUserModel userModel) {
    if (Objects.isNull(userModel)) {
      return null;
    }
    if (userModel.checkSuperuser()) {
      return new GrantAllAuthorizer(true);
    } else {
      List<SysPermission> permissions = this.getUserPermissions(userModel.getUserId());
      List<ApiResource> resources =
          permissions.stream().map(p -> ApiResource.of(p.getName())).collect(Collectors.toList());
      return new SimpleAuthorizer(userModel, resources);
    }
  }

  @Override
  public boolean updateProfile(@Nullable SysUser user) {
    if (Objects.isNull(user)) {
      return false;
    }
    return this.updateChain()
        .set(SysUserTableDef.SYS_USER.NICKNAME, user.getNickname())
        .set(SysUserTableDef.SYS_USER.EMAIL, user.getEmail())
        .set(SysUserTableDef.SYS_USER.TEL, user.getTel())
        .set(SysUserTableDef.SYS_USER.PROVINCE, user.getProvince())
        .set(SysUserTableDef.SYS_USER.CITY, user.getCity())
        .set(SysUserTableDef.SYS_USER.AREA, user.getArea())
        .set(SysUserTableDef.SYS_USER.TOWN, user.getTown())
        .set(SysUserTableDef.SYS_USER.ADDRESS, user.getAddress())
        .where(SysUserTableDef.SYS_USER.ID.eq(user.getUserId()))
        .update();
  }

  @Override
  @NonNull
  public List<SysRole> getUserRoles(@Nullable Collection<? extends Serializable> ids) {
    if (Objects.isNull(ids)) {
      return Collections.emptyList();
    }
    QueryWrapper wrapper =
        query()
            .select(SysRoleTableDef.SYS_ROLE.ALL_COLUMNS)
            .from(SysRoleTableDef.SYS_ROLE.as("r"))
            .leftJoin(SysUserRoleRelTableDef.SYS_USER_ROLE_REL)
            .as("ur")
            .on(SysUserRoleRelTableDef.SYS_USER_ROLE_REL.ROLE_ID.eq(SysRoleTableDef.SYS_ROLE.ID))
            .where(SysUserRoleRelTableDef.SYS_USER_ROLE_REL.USER_ID.in(ids));
    return this.getMapper().selectListByQueryAs(wrapper, SysRole.class);
  }

  @Override
  public boolean addUserRoles(String id, @Nullable List<SysRole> entities) {
    if (!StringUtils.hasText(id) || Objects.isNull(entities) || entities.isEmpty()) {
      return false;
    }
    List<SysUserRoleRel> rels =
        entities.stream()
            .map(d -> SysUserRoleRel.builder().userId(id).roleId(d.getId()).build())
            .collect(Collectors.toList());
    return this.userRoleRelService.saveBatch(rels);
  }

  @Override
  public boolean setUserRoles(String id, @Nullable List<SysRole> entities) {
    if (!StringUtils.hasText(id) || Objects.isNull(entities) || entities.isEmpty()) {
      return false;
    }
    QueryWrapper wrapper = query().where(SysUserRoleRelTableDef.SYS_USER_ROLE_REL.USER_ID.eq(id));
    this.userRoleRelService.remove(wrapper);
    return addUserRoles(id, entities);
  }

  @Override
  public boolean removeUserRoles(String id, @Nullable List<String> entityIds) {
    if (!StringUtils.hasText(id) || Objects.isNull(entityIds) || entityIds.isEmpty()) {
      return false;
    }
    QueryWrapper wrapper =
        query()
            .where(SysUserRoleRelTableDef.SYS_USER_ROLE_REL.USER_ID.eq(id))
            .and(SysUserRoleRelTableDef.SYS_USER_ROLE_REL.ROLE_ID.in(entityIds));
    return this.userRoleRelService.remove(wrapper);
  }

  @Override
  @NonNull
  public List<SysPolicy> getUserPolicies(String id) {
    if (!StringUtils.hasText(id)) {
      return Collections.emptyList();
    }
    List<SysRole> roles = getUserRoles(Collections.singletonList(id));
    if (roles.isEmpty()) {
      return Collections.emptyList();
    }
    List<String> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());
    return roleService.getRolePolicies(roleIds);
  }

  @Override
  @NonNull
  public List<SysPermission> getUserPermissions(String id) {
    List<SysPolicy> policies = getUserPolicies(id);
    if (policies.isEmpty()) {
      return Collections.emptyList();
    }
    List<String> policyIds = policies.stream().map(SysPolicy::getId).collect(Collectors.toList());
    return policyService.getPolicyPermissions(policyIds);
  }

  @Override
  @NonNull
  public List<SysDataScope> getUserDataScopes(String id) {
    List<SysPolicy> policies = getUserPolicies(id);
    if (policies.isEmpty()) {
      return Collections.emptyList();
    }
    List<String> policyIds = policies.stream().map(SysPolicy::getId).collect(Collectors.toList());
    return policyService.getPolicyDataScopes(policyIds);
  }

  @Override
  @NonNull
  public List<SysMenu> getUserMenus(String id) {
    List<SysPolicy> policies = getUserPolicies(id);
    if (policies.isEmpty()) {
      return Collections.emptyList();
    }
    List<String> policyIds = policies.stream().map(SysPolicy::getId).collect(Collectors.toList());
    return policyService.getPolicyMenus(policyIds);
  }

  @Override
  @NonNull
  public SysUser checkBeforeDeleteObject(@NonNull SysUser entity) {
    entity = SysUserService.super.checkBeforeDeleteObject(entity);
    // 存在关联角色，则删除关联关系
    QueryWrapper wrapper =
        userRoleRelService
            .query()
            .where(SysUserRoleRelTableDef.SYS_USER_ROLE_REL.USER_ID.eq(entity.getId()));
    if (userRoleRelService.count(wrapper) > 0) {
      userRoleRelService.remove(wrapper);
    }
    return entity;
  }

  @Override
  @NonNull
  public Collection<SysUser> checkBeforeBatchDeleteObjects(@NonNull Collection<SysUser> entities) {
    entities = SysUserService.super.checkBeforeBatchDeleteObjects(entities);
    // 存在关联角色，则删除关联关系
    Collection<String> ids = entities.stream().map(IdEntity::getId).collect(Collectors.toList());
    QueryWrapper wrapper =
        userRoleRelService.query().where(SysUserRoleRelTableDef.SYS_USER_ROLE_REL.USER_ID.in(ids));
    if (userRoleRelService.count(wrapper) > 0) {
      userRoleRelService.remove(wrapper);
    }
    return entities;
  }
}
