package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.relation.SysRolePolicyRel;
import com.wangboot.system.mapper.SysRolePolicyRelMapper;
import com.wangboot.system.service.SysRolePolicyRelService;
import org.springframework.stereotype.Service;

@Service
public class SysRolePolicyRelServiceImpl
    extends ServiceImpl<SysRolePolicyRelMapper, SysRolePolicyRel>
    implements SysRolePolicyRelService {}
