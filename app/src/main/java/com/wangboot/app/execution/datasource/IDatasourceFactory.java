package com.wangboot.app.execution.datasource;

import org.springframework.lang.Nullable;

/**
 * 数据源工厂接口
 *
 * @author wwtg99
 */
public interface IDatasourceFactory {

  /**
   * 连接数据源
   *
   * @param id 数据源ID
   * @param name 数据源名称
   * @param config 数据源配置
   * @return 数据源
   */
  @Nullable
  IDatasource connectDatasource(String id, String name, String config);
}
