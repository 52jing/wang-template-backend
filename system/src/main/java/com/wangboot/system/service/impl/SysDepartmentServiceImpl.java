package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.model.entity.exception.DeleteCascadeFailedException;
import com.wangboot.system.entity.SysDepartment;
import com.wangboot.system.mapper.SysDepartmentMapper;
import com.wangboot.system.service.SysDepartmentService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author wwtg99
 */
@Service
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment>
    implements SysDepartmentService {

  @Override
  @NonNull
  public SysDepartment checkBeforeDeleteObject(@NonNull SysDepartment entity) {
    entity = SysDepartmentService.super.checkBeforeDeleteObject(entity);
    // 存在子节点则不允许删除
    if (this.getDirectChildrenCount(entity.getId()) > 0) {
      throw new DeleteCascadeFailedException(entity.getId());
    }
    return entity;
  }
}
