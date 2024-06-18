package com.wangboot.app.template.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.app.execution.RenderErrorCode;
import com.wangboot.app.execution.datasource.DatasourceParamHolder;
import com.wangboot.app.execution.datasource.DatasourceProcessor;
import com.wangboot.app.execution.datasource.IDatasource;
import com.wangboot.app.template.entity.TplDatasource;
import com.wangboot.app.template.entity.TplDatasourceParam;
import com.wangboot.app.template.entity.table.TplDatasourceParamTableDef;
import com.wangboot.app.template.entity.table.TplDatasourceTableDef;
import com.wangboot.app.template.mapper.TplDatasourceMapper;
import com.wangboot.app.template.service.TplDatasourceParamService;
import com.wangboot.app.template.service.TplDatasourceService;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.exception.UpdateFailedException;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TplDatasourceServiceImpl extends ServiceImpl<TplDatasourceMapper, TplDatasource>
    implements TplDatasourceService {

  private final TplDatasourceParamService datasourceParamService;

  private final DatasourceProcessor datasourceProcessor;

  @Override
  public boolean deleteResource(@NonNull TplDatasource entity) {
    boolean ret = TplDatasourceService.super.deleteResource(entity);
    if (ret) {
      // 移除数据源
      this.datasourceProcessor.removeDatasource(entity.getId());
    }
    return ret;
  }

  @Override
  @NonNull
  public List<TplDatasourceParam> getDatasourceParams(String id) {
    if (!StringUtils.hasText(id)) {
      return Collections.emptyList();
    }
    QueryWrapper wrapper =
        QueryWrapper.create()
            .where(TplDatasourceParamTableDef.TPL_DATASOURCE_PARAM.DATASOURCE_ID.eq(id));
    return this.datasourceParamService.list(wrapper);
  }

  @Override
  public boolean createDatasourceParams(String datasourceId, List<TplDatasourceParam> params) {
    if (StringUtils.hasText(datasourceId) && Objects.nonNull(params) && !params.isEmpty()) {
      params.forEach(pm -> pm.setDatasourceId(datasourceId));
      return this.datasourceParamService.saveBatch(params);
    }
    return true;
  }

  @Override
  public boolean updateDatasourceParams(String datasourceId, List<TplDatasourceParam> params) {
    if (!StringUtils.hasText(datasourceId)) {
      return false;
    }
    if (Objects.nonNull(params) && !params.isEmpty()) {
      final List<TplDatasourceParam> createParamList = new ArrayList<>();
      final List<TplDatasourceParam> updateParamList = new ArrayList<>();
      params.forEach(
          pm -> {
            pm.setDatasourceId(datasourceId);
            if (StringUtils.hasText(pm.getId())) {
              // update
              updateParamList.add(pm);
            } else {
              // insert
              createParamList.add(pm);
            }
          });
      if (!createParamList.isEmpty()) {
        // 添加
        boolean ret = this.datasourceParamService.saveBatch(createParamList);
        if (!ret) {
          throw new UpdateFailedException(datasourceId);
        }
      }
      if (!updateParamList.isEmpty()) {
        // 更新
        boolean ret = this.datasourceParamService.updateBatch(updateParamList);
        if (!ret) {
          throw new UpdateFailedException(datasourceId);
        }
      }
    }
    return true;
  }

  @Override
  public boolean deleteDatasourceParams(String id) {
    return this.datasourceParamService.removeById(id);
  }

  @Override
  @Nullable
  public IDatasource connectDatasource(TplDatasource datasource) {
    if (Objects.isNull(datasource)) {
      return null;
    }
    // 准备数据源
    this.datasourceProcessor.prepareDatasource(
        datasource.getId(), datasource.getName(), datasource.getType(), datasource.getConfig());
    // 连接数据源
    IDatasource ds = this.datasourceProcessor.connectDatasource(datasource.getId());
    if (Objects.isNull(ds)) {
      return null;
    }
    // 添加数据源
    this.datasourceProcessor.addDatasource(datasource.getId(), ds);
    if (!datasource.getConnected()) {
      // 更新已连接
      this.updateChain()
          .eq(FieldConstants.PRIMARY_KEY, datasource.getId())
          .set(TplDatasourceTableDef.TPL_DATASOURCE.CONNECTED, true)
          .update();
    }
    return ds;
  }

  @Override
  @Nullable
  public Object retrieveData(String id, DatasourceParamHolder params) {
    IDatasource ds = this.datasourceProcessor.getDatasource(id);
    if (Objects.isNull(ds)) {
      throw new ErrorCodeException(RenderErrorCode.CONNECT_DATASOURCE_FAILED);
    }
    return ds.retrieveData(params);
  }

  @Override
  public Set<String> getDatasourceTypes() {
    return this.datasourceProcessor.getTypes();
  }
}
