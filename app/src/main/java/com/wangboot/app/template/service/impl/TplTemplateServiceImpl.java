package com.wangboot.app.template.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.app.template.entity.TplTemplate;
import com.wangboot.app.template.mapper.TplTemplateMapper;
import com.wangboot.app.template.service.TplTemplateService;
import org.springframework.stereotype.Service;

@Service
public class TplTemplateServiceImpl extends ServiceImpl<TplTemplateMapper, TplTemplate> implements TplTemplateService {
}
