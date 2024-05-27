package com.wangboot.system.service;

import com.wangboot.model.flex.IFlexRestfulService;
import com.wangboot.system.entity.SysDataScope;
import com.wangboot.system.entity.SysMenu;
import com.wangboot.system.entity.SysPermission;
import com.wangboot.system.entity.SysPolicy;
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
public interface SysPolicyService extends IFlexRestfulService<String, SysPolicy> {

  /** 获取权限 */
  @NonNull
  List<SysPermission> getPolicyPermissions(@Nullable Collection<String> ids);

  /** 添加策略权限 */
  boolean addPolicyPermissions(String id, @Nullable List<SysPermission> entities);

  /** 设置策略权限 */
  boolean setPolicyPermissions(String id, @Nullable List<SysPermission> entities);

  /** 移除策略权限 */
  boolean removePolicyPermissions(String id, @Nullable List<String> entityIds);

  /** 获取数据权限 */
  @NonNull
  List<SysDataScope> getPolicyDataScopes(@Nullable Collection<? extends Serializable> ids);

  /** 添加策略数据权限 */
  boolean addPolicyDataScopes(String id, @Nullable List<SysDataScope> entities);

  /** 设置策略数据权限 */
  boolean setPolicyDataScopes(String id, @Nullable List<SysDataScope> entities);

  /** 移除策略数据权限 */
  boolean removePolicyDataScopes(String id, @Nullable List<String> entityIds);

  /** 获取菜单 */
  @NonNull
  List<SysMenu> getPolicyMenus(@Nullable Collection<? extends Serializable> ids);

  /** 添加策略菜单 */
  boolean addPolicyMenus(String id, @Nullable List<SysMenu> entities);

  /** 设置策略菜单 */
  boolean setPolicyMenus(String id, @Nullable List<SysMenu> entities);

  /** 移除策略菜单 */
  boolean removePolicyMenus(String id, @Nullable List<String> entityIds);
}
