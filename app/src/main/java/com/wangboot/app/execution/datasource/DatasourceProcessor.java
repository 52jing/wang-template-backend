package com.wangboot.app.execution.datasource;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源处理器
 * @author wwtg99
 */
@Component
public class DatasourceProcessor {

  private final Map<String, IDatasourceFactory> factories = new HashMap<>();
  private final Map<String, IDatasource> datasources = new ConcurrentHashMap<>();

  /**
   * 注册数据源工厂
   *
   * @param type 类型
   * @param datasourceFactory 数据源工厂
   * @return 数据源处理器
   */
  @NonNull
  public DatasourceProcessor registerDatasourceFactory(String type, IDatasourceFactory datasourceFactory) {
    this.factories.put(type, datasourceFactory);
    return this;
  }

  /**
   * 获取数据源工厂
   *
   * @param type 类型
   * @return 数据源工厂
   */
  @Nullable
  public IDatasourceFactory getDatasourceFactory(String type) {
    if (StringUtils.hasText(type) && this.factories.containsKey(type)) {
      return this.factories.get(type);
    }
    return null;
  }

  /**
   * 添加数据源
   * @param id 数据源ID
   * @param datasource 数据源
   */
  @NonNull
  public void addDatasource(String id, IDatasource datasource) {
    if (StringUtils.hasText(id) && Objects.nonNull(datasource)) {
      this.datasources.put(id, datasource);
    }
  }

  /**
   * 连接并添加数据源
   *
   * @param id 数据源ID
   * @param name 数据源名称
   * @param type 数据源类型
   * @param config 数据源配置
   * @return 数据源
   */
  @Nullable
  public IDatasource connectDatasource(String id, String name, String type, String config) {
    IDatasourceFactory factory = this.getDatasourceFactory(type);
    if (Objects.isNull(factory)) {
      return null;
    }
    IDatasource datasource = factory.connectDatasource(id, name, config);
    if (Objects.isNull(datasource)) {
      return null;
    }
    this.addDatasource(id, datasource);
    return datasource;
  }

  /**
   * 获取数据源
   * @param id 数据源ID
   * @return 数据源
   */
  @Nullable
  public IDatasource getDatasource(String id) {
    if (StringUtils.hasText(id) && this.datasources.containsKey(id)) {
      return this.datasources.get(id);
    }
    return null;
  }

  /**
   * 获取数据源类型
   * @return 类型集合
   */
  @NonNull
  public Set<String> getTypes() {
    return this.factories.keySet();
  }

}
