package com.wangboot.app.template.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.app.template.entity.TplExecutionResult;
import com.wangboot.app.template.mapper.TplExecutionResultMapper;
import com.wangboot.app.template.service.TplExecutionResultService;
import org.springframework.stereotype.Service;

@Service
public class TplExecutionResultServiceImpl extends ServiceImpl<TplExecutionResultMapper, TplExecutionResult> implements TplExecutionResultService {
}
