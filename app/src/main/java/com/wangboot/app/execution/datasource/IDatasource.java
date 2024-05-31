package com.wangboot.app.execution.datasource;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.lang.Nullable;

import java.util.Map;

/**
 * 数据源接口
 * @author wwtg99
 */
public interface IDatasource {

  /**
   * 数据源名称
   *
   * @return 名称
   */
  String getName();

  /**
   * 获取数据
   *
   * @param params 参数
   * @return 数据
   */
  @Nullable
  Object retrieveData(Map<String, String> params);

  /**
   * 获取数据
   *
   * @param params 参数
   * @param dataClass 数据类
   * @param <T> 数据类型
   * @return 数据
   */
  @Nullable
  default <T> T retrieveData(Map<String, String> params, Class<T> dataClass) {
    return BeanUtil.toBean(retrieveData(params), dataClass);
  }

}