package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.SysUserDict;
import com.wangboot.system.mapper.SysUserDictMapper;
import com.wangboot.system.service.SysUserDictService;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author wwtg99
 */
@Service
public class SysUserDictServiceImpl extends ServiceImpl<SysUserDictMapper, SysUserDict>
    implements SysUserDictService {}
