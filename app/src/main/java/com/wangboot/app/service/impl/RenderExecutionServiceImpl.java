package com.wangboot.app.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.app.entity.RenderExecution;
import com.wangboot.app.mapper.RenderExecutionMapper;
import com.wangboot.app.service.RenderExecutionService;
import org.springframework.stereotype.Service;

@Service
public class RenderExecutionServiceImpl extends ServiceImpl<RenderExecutionMapper, RenderExecution> implements RenderExecutionService {
}
