package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.SysOperationLog;
import com.wangboot.system.mapper.SysOperationLogMapper;
import com.wangboot.system.service.SysOperationLogService;
import org.springframework.stereotype.Service;

@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog>
    implements SysOperationLogService {}
