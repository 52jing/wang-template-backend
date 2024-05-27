package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.relation.SysUserRoleRel;
import com.wangboot.system.mapper.SysUserRoleRelMapper;
import com.wangboot.system.service.SysUserRoleRelService;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleRelServiceImpl extends ServiceImpl<SysUserRoleRelMapper, SysUserRoleRel>
    implements SysUserRoleRelService {}
