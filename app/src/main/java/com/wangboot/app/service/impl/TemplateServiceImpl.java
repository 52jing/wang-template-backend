package com.wangboot.app.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.app.entity.Template;
import com.wangboot.app.mapper.TemplateMapper;
import com.wangboot.app.service.TemplateService;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {
}
