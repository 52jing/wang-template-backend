package com.wangboot.system.service;

import com.wangboot.core.auth.authorization.IAuthorizerService;
import com.wangboot.core.auth.user.IUserService;
import com.wangboot.model.flex.IFlexRestfulService;
import com.wangboot.system.entity.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 服务层。
 *
 * @author wwtg99
 */
public interface SysUserService
    extends IUserService, IAuthorizerService, IFlexRestfulService<String, SysUser> {

  boolean updateProfile(@Nullable SysUser user);

  /** 获取用户角色 */
  @NonNull
  List<SysRole> getUserRoles(@Nullable Collection<? extends Serializable> ids);

  /** 添加用户角色 */
  boolean addUserRoles(String id, @Nullable List<SysRole> entities);

  /** 设置用户角色 */
  boolean setUserRoles(String id, @Nullable List<SysRole> entities);

  /** 移除用户角色 */
  boolean removeUserRoles(String id, @Nullable List<String> entityIds);

  @NonNull
  List<SysPolicy> getUserPolicies(String id);

  @NonNull
  List<SysPermission> getUserPermissions(String id);

  @NonNull
  List<SysDataScope> getUserDataScopes(String id);

  @NonNull
  List<SysMenu> getUserMenus(String id);
}
