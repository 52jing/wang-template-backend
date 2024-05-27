package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.SysUserLog;
import com.wangboot.system.mapper.SysUserLogMapper;
import com.wangboot.system.service.SysUserLogService;
import org.springframework.stereotype.Service;

@Service
public class SysUserLogServiceImpl extends ServiceImpl<SysUserLogMapper, SysUserLog>
    implements SysUserLogService {}
