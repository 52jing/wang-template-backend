package com.wangboot.app.template.service;

import com.wangboot.app.execution.datasource.IDatasource;
import com.wangboot.app.template.entity.TplDatasource;
import com.wangboot.app.template.entity.TplDatasourceParam;
import com.wangboot.model.flex.IFlexRestfulService;
import java.util.List;
import java.util.Set;
import org.springframework.lang.Nullable;

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
   * @param datasourceId 数据源ID
   * @param params 数据集
   * @return 是否成功
   */
  boolean createDatasourceParams(String datasourceId, List<TplDatasourceParam> params);

  /**
   * 更新数据源参数
   *
   * @param datasourceId 数据源ID
   * @param params 数据集
   * @return 是否成功
   */
  boolean updateDatasourceParams(String datasourceId, List<TplDatasourceParam> params);

  /**
   * 删除数据源参数
   *
   * @param id 数据源ID
   * @return 是否成功
   */
  boolean deleteDatasourceParams(String id);

  /**
   * 连接数据源
   *
   * @param datasource 数据源
   * @return 数据源接口
   */
  @Nullable
  IDatasource connectDatasource(TplDatasource datasource);

  /**
   * 获取数据源类型
   *
   * @return 类型集合
   */
  Set<String> getDatasourceTypes();
}
