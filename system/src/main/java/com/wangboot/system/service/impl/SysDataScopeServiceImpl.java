package com.wangboot.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.model.dataauthority.datascope.IDataScopeModel;
import com.wangboot.model.dataauthority.datascope.IDataScopeService;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.exception.DeleteCascadeFailedException;
import com.wangboot.system.entity.SysDataScope;
import com.wangboot.system.entity.relation.table.SysPolicyDataScopeRelTableDef;
import com.wangboot.system.mapper.SysDataScopeMapper;
import com.wangboot.system.service.SysDataScopeService;
import com.wangboot.system.service.SysPolicyDataScopeRelService;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author wwtg99
 */
@Service
@RequiredArgsConstructor
public class SysDataScopeServiceImpl extends ServiceImpl<SysDataScopeMapper, SysDataScope>
    implements SysDataScopeService, IDataScopeService {

  private final SysPolicyDataScopeRelService policyDataScopeRelService;

  @Override
  @NonNull
  public SysDataScope checkBeforeDeleteObject(@NonNull SysDataScope entity) {
    entity = SysDataScopeService.super.checkBeforeDeleteObject(entity);
    // 存在关联策略，则不允许删除
    QueryWrapper wrapper =
        policyDataScopeRelService
            .query()
            .where(
                SysPolicyDataScopeRelTableDef.SYS_POLICY_DATA_SCOPE_REL.DATA_SCOPE_ID.eq(
                    entity.getId()));
    if (policyDataScopeRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(entity.getId());
    }
    return entity;
  }

  @Override
  @Nullable
  public Collection<SysDataScope> checkBeforeBatchDeleteObjects(
      @Nullable Collection<SysDataScope> entities) {
    entities = SysDataScopeService.super.checkBeforeBatchDeleteObjects(entities);
    if (Objects.nonNull(entities)) {
      // 存在关联策略，则不允许删除
      Collection<String> ids = entities.stream().map(IdEntity::getId).collect(Collectors.toList());
      QueryWrapper wrapper =
          policyDataScopeRelService
              .query()
              .where(SysPolicyDataScopeRelTableDef.SYS_POLICY_DATA_SCOPE_REL.DATA_SCOPE_ID.in(ids));
      if (policyDataScopeRelService.count(wrapper) > 0) {
        throw new DeleteCascadeFailedException(String.join(",", ids));
      }
    }
    return entities;
  }

  @Override
  public Collection<? extends IDataScopeModel> getDataScopes(String userId) {
    return null;
  }
}
