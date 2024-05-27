package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.SysJob;
import com.wangboot.system.mapper.SysJobMapper;
import com.wangboot.system.service.SysJobService;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author wwtg99
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {}
