package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.relation.SysPolicyPermissionRel;
import com.wangboot.system.mapper.SysPolicyPermissionRelMapper;
import com.wangboot.system.service.SysPolicyPermissionRelService;
import org.springframework.stereotype.Service;

@Service
public class SysPolicyPermissionRelServiceImpl
    extends ServiceImpl<SysPolicyPermissionRelMapper, SysPolicyPermissionRel>
    implements SysPolicyPermissionRelService {}
