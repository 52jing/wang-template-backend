package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.relation.SysPolicyDataScopeRel;
import com.wangboot.system.mapper.SysPolicyDataScopeRelMapper;
import com.wangboot.system.service.SysPolicyDataScopeRelService;
import org.springframework.stereotype.Service;

@Service
public class SysPolicyDataScopeRelServiceImpl
    extends ServiceImpl<SysPolicyDataScopeRelMapper, SysPolicyDataScopeRel>
    implements SysPolicyDataScopeRelService {}
