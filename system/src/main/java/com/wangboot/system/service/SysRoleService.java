package com.wangboot.system.service;

import com.wangboot.model.flex.IFlexRestfulService;
import com.wangboot.system.entity.SysPolicy;
import com.wangboot.system.entity.SysRole;
import java.util.Collection;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 服务层。
 *
 * @author wwtg99
 */
public interface SysRoleService extends IFlexRestfulService<String, SysRole> {

  /** 获取策略 */
  @NonNull
  List<SysPolicy> getRolePolicies(@Nullable Collection<String> ids);

  /** 添加角色策略 */
  boolean addRolePolicies(String id, @Nullable List<SysPolicy> entities);

  /** 设置角色策略 */
  boolean setRolePolicies(String id, @Nullable List<SysPolicy> entities);

  /** 移除角色策略 */
  boolean removeRolePolicies(String id, @Nullable List<String> entityIds);
}
