package com.wangboot.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.exception.DeleteCascadeFailedException;
import com.wangboot.system.entity.SysPolicy;
import com.wangboot.system.entity.SysRole;
import com.wangboot.system.entity.relation.SysRolePolicyRel;
import com.wangboot.system.entity.relation.table.SysRolePolicyRelTableDef;
import com.wangboot.system.entity.relation.table.SysUserRoleRelTableDef;
import com.wangboot.system.entity.table.*;
import com.wangboot.system.mapper.SysRoleMapper;
import com.wangboot.system.service.SysRolePolicyRelService;
import com.wangboot.system.service.SysRoleService;
import com.wangboot.system.service.SysUserRoleRelService;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 服务层实现。
 *
 * @author wwtg99
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService {

  private final SysRolePolicyRelService rolePolicyRelService;

  private final SysUserRoleRelService userRoleRelService;

  @Override
  @Nullable
  public SysRole getDataById(@NonNull String id) {
    return this.getMapper().selectOneWithRelationsById(id);
  }

  @Override
  @NonNull
  public SysRole checkBeforeDeleteObject(@NonNull SysRole entity) {
    entity = SysRoleService.super.checkBeforeDeleteObject(entity);
    // 存在关联用户或策略，则不允许删除
    QueryWrapper wrapper =
        rolePolicyRelService
            .query()
            .where(SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL.ROLE_ID.eq(entity.getId()));
    if (rolePolicyRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(entity.getId());
    }
    wrapper =
        userRoleRelService
            .query()
            .where(SysUserRoleRelTableDef.SYS_USER_ROLE_REL.ROLE_ID.eq(entity.getId()));
    if (userRoleRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(entity.getId());
    }
    return entity;
  }

  @Override
  @NonNull
  public Collection<SysRole> checkBeforeBatchDeleteObjects(@NonNull Collection<SysRole> entities) {
    entities = SysRoleService.super.checkBeforeBatchDeleteObjects(entities);
    // 存在关联用户或策略，则不允许删除
    Collection<String> ids = entities.stream().map(IdEntity::getId).collect(Collectors.toList());
    QueryWrapper wrapper =
        rolePolicyRelService
            .query()
            .where(SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL.ROLE_ID.in(ids));
    if (rolePolicyRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(String.join(",", ids));
    }
    wrapper =
        userRoleRelService.query().where(SysUserRoleRelTableDef.SYS_USER_ROLE_REL.ROLE_ID.in(ids));
    if (userRoleRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(String.join(",", ids));
    }
    return entities;
  }

  @Override
  @NonNull
  public List<SysPolicy> getRolePolicies(@Nullable Collection<String> ids) {
    if (Objects.isNull(ids) || ids.isEmpty()) {
      return Collections.emptyList();
    }
    QueryWrapper wrapper =
        query()
            .select(SysPolicyTableDef.SYS_POLICY.ALL_COLUMNS)
            .from(SysPolicyTableDef.SYS_POLICY.as("r"))
            .leftJoin(SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL)
            .as("ur")
            .on(
                SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL.POLICY_ID.eq(
                    SysPolicyTableDef.SYS_POLICY.ID))
            .where(SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL.ROLE_ID.in(ids));
    return this.getMapper().selectListByQueryAs(wrapper, SysPolicy.class);
  }

  @Override
  public boolean addRolePolicies(String id, List<SysPolicy> entities) {
    if (!StringUtils.hasText(id) || Objects.isNull(entities) || entities.isEmpty()) {
      return false;
    }
    List<SysRolePolicyRel> rels =
        entities.stream()
            .map(d -> SysRolePolicyRel.builder().roleId(id).policyId(d.getId()).build())
            .collect(Collectors.toList());
    return this.rolePolicyRelService.saveBatch(rels);
  }

  @Override
  public boolean setRolePolicies(String id, List<SysPolicy> entities) {
    if (!StringUtils.hasText(id) || Objects.isNull(entities) || entities.isEmpty()) {
      return false;
    }
    QueryWrapper wrapper =
        query().where(SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL.ROLE_ID.eq(id));
    this.rolePolicyRelService.remove(wrapper);
    return addRolePolicies(id, entities);
  }

  @Override
  public boolean removeRolePolicies(String id, List<String> entityIds) {
    if (!StringUtils.hasText(id) || Objects.isNull(entityIds) || entityIds.isEmpty()) {
      return false;
    }
    QueryWrapper wrapper =
        query()
            .where(SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL.ROLE_ID.eq(id))
            .and(SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL.POLICY_ID.in(entityIds));
    return this.rolePolicyRelService.remove(wrapper);
  }
}
