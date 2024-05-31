package com.wangboot.app.template.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.app.template.entity.TplDatasource;
import com.wangboot.app.template.entity.TplDatasourceParam;
import com.wangboot.app.template.entity.dto.TplDatasourceDto;
import com.wangboot.app.template.entity.table.TplDatasourceParamTableDef;
import com.wangboot.app.template.mapper.TplDatasourceMapper;
import com.wangboot.app.template.service.TplDatasourceParamService;
import com.wangboot.app.template.service.TplDatasourceService;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.exception.CreateFailedException;
import com.wangboot.model.entity.exception.UpdateFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TplDatasourceServiceImpl extends ServiceImpl<TplDatasourceMapper, TplDatasource> implements TplDatasourceService {

  private final TplDatasourceParamService datasourceParamService;

  @Override
  @NonNull
  public List<TplDatasourceParam> getDatasourceParams(String id) {
    if (!StringUtils.hasText(id)) {
      return Collections.emptyList();
    }
    QueryWrapper wrapper = QueryWrapper.create().where(TplDatasourceParamTableDef.TPL_DATASOURCE_PARAM.DATASOURCE_ID.eq(id));
    return this.datasourceParamService.list(wrapper);
  }

  @Override
  @Transactional
  public boolean createDatasourceParams(TplDatasource datasource, TplDatasourceDto dto) {
    if (Objects.isNull(datasource) || Objects.isNull(dto)) {
      throw new CreateFailedException();
    }
    if (Objects.nonNull(dto.getParams()) && !dto.getParams().isEmpty()) {
      List<TplDatasourceParam> paramList = new ArrayList<>();
      dto.getParams().forEach(pm -> {
        TplDatasourceParam datasourceParam = BeanUtil.copyProperties(pm, TplDatasourceParam.class);
        datasourceParam.setDatasourceId(datasource.getId());
        paramList.add(datasourceParam);
      });
      return this.datasourceParamService.saveBatch(paramList);
    }
    return true;
  }

  @Override
  @Transactional
  public boolean updateDatasourceParams(TplDatasource datasource, TplDatasourceDto dto) {
    if (Objects.isNull(datasource) || Objects.isNull(dto)) {
      throw new UpdateFailedException("");
    }
    if (Objects.nonNull(dto.getParams()) && !dto.getParams().isEmpty()) {
      final List<TplDatasourceParam> createParamList = new ArrayList<>();
      final List<TplDatasourceParam> updateParamList = new ArrayList<>();
      final List<String> retainParamIds = new ArrayList<>();
      dto.getParams().forEach(pm -> {
        if (StringUtils.hasText(pm.getId())) {
          // update
          TplDatasourceParam datasourceParam = BeanUtil.copyProperties(pm, TplDatasourceParam.class);
          datasourceParam.setDatasourceId(datasource.getId());
          updateParamList.add(datasourceParam);
          retainParamIds.add(pm.getId());
        } else {
          // insert
          TplDatasourceParam datasourceParam = BeanUtil.copyProperties(pm, TplDatasourceParam.class);
          datasourceParam.setDatasourceId(datasource.getId());
          createParamList.add(datasourceParam);
        }
      });
      if (!createParamList.isEmpty()) {
        // 添加
        boolean ret = this.datasourceParamService.saveBatch(createParamList);
        if (!ret) {
          throw new UpdateFailedException(datasource.getId());
        }
      }
      if (!updateParamList.isEmpty()) {
        // 更新
        boolean ret = this.datasourceParamService.updateBatch(updateParamList);
        if (!ret) {
          throw new UpdateFailedException(datasource.getId());
        }
      }
      if (!retainParamIds.isEmpty()) {
        // 删除
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.notIn(FieldConstants.PRIMARY_KEY, retainParamIds);
        boolean ret = this.datasourceParamService.remove(wrapper);
        if (!ret) {
          throw new UpdateFailedException(datasource.getId());
        }
      }
      return true;
    } else {
      // 清空参数
      return this.deleteDatasourceParams(datasource.getId());
    }
  }

  @Override
  public boolean deleteDatasourceParams(String id) {
    QueryWrapper wrapper = QueryWrapper.create().where(TplDatasourceParamTableDef.TPL_DATASOURCE_PARAM.DATASOURCE_ID.eq(id));
    return this.datasourceParamService.remove(wrapper);
  }
}
