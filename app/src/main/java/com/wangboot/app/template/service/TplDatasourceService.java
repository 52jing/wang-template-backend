package com.wangboot.app.template.service;

import com.wangboot.app.template.entity.TplDatasource;
import com.wangboot.app.template.entity.TplDatasourceParam;
import com.wangboot.app.template.entity.dto.TplDatasourceDto;
import com.wangboot.model.flex.IFlexRestfulService;
import org.springframework.lang.NonNull;

import java.util.List;

public interface TplDatasourceService extends IFlexRestfulService<String, TplDatasource> {

  /**
   * 获取数据源参数
   *
   * @param id 数据源ID
   * @return 参数列表
   */
  List<TplDatasourceParam> getDatasourceParams(String id);

  /**
   * 创建数据源参数
   *
   * @param datasource 数据源
   * @param dto 数据集
   * @return 是否成功
   */
  boolean createDatasourceParams(TplDatasource datasource, TplDatasourceDto dto);

  /**
   * 更新数据源参数
   *
   * @param datasource 数据源
   * @param dto 数据集
   * @return 是否成功
   */
  boolean updateDatasourceParams(TplDatasource datasource, TplDatasourceDto dto);

  /**
   * 删除数据源参数
   *
   * @param id 数据源ID
   * @return 是否成功
   */
  boolean deleteDatasourceParams(String id);
}
