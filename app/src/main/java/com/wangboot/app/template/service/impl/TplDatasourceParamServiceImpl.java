package com.wangboot.app.template.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.app.template.entity.TplDatasourceParam;
import com.wangboot.app.template.mapper.TplDatasourceParamMapper;
import com.wangboot.app.template.service.TplDatasourceParamService;
import org.springframework.stereotype.Service;

@Service
public class TplDatasourceParamServiceImpl
    extends ServiceImpl<TplDatasourceParamMapper, TplDatasourceParam>
    implements TplDatasourceParamService {}
