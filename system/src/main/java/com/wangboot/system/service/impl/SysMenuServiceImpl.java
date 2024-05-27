package com.wangboot.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.model.entity.exception.DeleteCascadeFailedException;
import com.wangboot.system.entity.SysMenu;
import com.wangboot.system.entity.relation.table.SysPolicyMenuRelTableDef;
import com.wangboot.system.mapper.SysMenuMapper;
import com.wangboot.system.service.SysMenuService;
import com.wangboot.system.service.SysPolicyMenuRelService;
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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService {

  private final SysPolicyMenuRelService policyMenuRelService;

  @Override
  @NonNull
  public SysMenu checkBeforeDeleteObject(@NonNull SysMenu entity) {
    entity = SysMenuService.super.checkBeforeDeleteObject(entity);
    // 存在子节点或关联策略则不允许删除
    if (this.getDirectChildrenCount(entity.getId()) > 0) {
      throw new DeleteCascadeFailedException(entity.getId());
    }
    QueryWrapper wrapper =
        policyMenuRelService
            .query()
            .where(SysPolicyMenuRelTableDef.SYS_POLICY_MENU_REL.MENU_ID.eq(entity.getId()));
    if (policyMenuRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(entity.getId());
    }
    return entity;
  }
}
