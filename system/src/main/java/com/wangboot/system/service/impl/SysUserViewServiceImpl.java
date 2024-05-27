package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.vo.SysUserView;
import com.wangboot.system.mapper.SysUserViewMapper;
import com.wangboot.system.service.SysUserViewService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class SysUserViewServiceImpl extends ServiceImpl<SysUserViewMapper, SysUserView>
    implements SysUserViewService {

  @Override
  @Nullable
  public SysUserView getDataById(@NonNull String id) {
    return this.getMapper().selectOneWithRelationsById(id);
  }
}
