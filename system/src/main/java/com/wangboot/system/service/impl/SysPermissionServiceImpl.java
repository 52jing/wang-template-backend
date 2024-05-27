package com.wangboot.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.exception.DeleteCascadeFailedException;
import com.wangboot.system.entity.SysPermission;
import com.wangboot.system.entity.relation.table.SysPolicyPermissionRelTableDef;
import com.wangboot.system.mapper.SysPermissionMapper;
import com.wangboot.system.service.SysPermissionService;
import com.wangboot.system.service.SysPolicyPermissionRelService;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author wwtg99
 */
@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission>
    implements SysPermissionService {

  private final SysPolicyPermissionRelService policyPermissionRelService;

  @Override
  @NonNull
  public SysPermission checkBeforeDeleteObject(@NonNull SysPermission entity) {
    entity = SysPermissionService.super.checkBeforeDeleteObject(entity);
    // 存在关联策略，则不允许删除
    QueryWrapper wrapper =
        policyPermissionRelService
            .query()
            .where(
                SysPolicyPermissionRelTableDef.SYS_POLICY_PERMISSION_REL.PERMISSION_ID.eq(
                    entity.getId()));
    if (policyPermissionRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(entity.getId());
    }
    return entity;
  }

  @Override
  @NonNull
  public Collection<SysPermission> checkBeforeBatchDeleteObjects(
      @NonNull Collection<SysPermission> entities) {
    entities = SysPermissionService.super.checkBeforeBatchDeleteObjects(entities);
    // 存在关联策略，则不允许删除
    Collection<String> ids = entities.stream().map(IdEntity::getId).collect(Collectors.toList());
    QueryWrapper wrapper =
        policyPermissionRelService
            .query()
            .where(SysPolicyPermissionRelTableDef.SYS_POLICY_PERMISSION_REL.PERMISSION_ID.in(ids));
    if (policyPermissionRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(String.join(",", ids));
    }
    return entities;
  }
}
