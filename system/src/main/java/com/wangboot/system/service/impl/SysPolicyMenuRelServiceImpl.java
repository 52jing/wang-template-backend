package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.relation.SysPolicyMenuRel;
import com.wangboot.system.mapper.SysPolicyMenuRelMapper;
import com.wangboot.system.service.SysPolicyMenuRelService;
import org.springframework.stereotype.Service;

@Service
public class SysPolicyMenuRelServiceImpl
    extends ServiceImpl<SysPolicyMenuRelMapper, SysPolicyMenuRel>
    implements SysPolicyMenuRelService {}
