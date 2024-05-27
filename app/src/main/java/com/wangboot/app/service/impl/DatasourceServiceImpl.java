package com.wangboot.app.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.app.entity.Datasource;
import com.wangboot.app.mapper.DatasourceMapper;
import com.wangboot.app.service.DatasourceService;
import org.springframework.stereotype.Service;

@Service
public class DatasourceServiceImpl extends ServiceImpl<DatasourceMapper, Datasource> implements DatasourceService {
}
