package com.wangboot.app.execution.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.wangboot.app.template.entity.TplDatasource;
import com.wangboot.core.cache.CacheUtil;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 数据源处理器
 *
 * @author wwtg99
 */
@Component
public class DatasourceProcessor {

  private static final String CACHE_PREFIX = "DS_";

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
  public DatasourceProcessor registerDatasourceFactory(
      String type, IDatasourceFactory datasourceFactory) {
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
   * 准备数据源
   *
   * @param id 数据源ID
   * @param name 数据源名称
   * @param type 数据源类型
   * @param config 数据源配置
   */
  public void prepareDatasource(String id, String name, String type, String config) {
    // save datasource to cache
    TplDatasource ds = new TplDatasource(id, name, type, config, true);
    CacheUtil.put(CACHE_PREFIX + id, ds);
  }

  /**
   * 添加数据源
   *
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
   * 连接数据源
   *
   * @param id 数据源ID
   * @return 数据源
   */
  @Nullable
  public IDatasource connectDatasource(String id) {
    if (!StringUtils.hasText(id)) {
      return null;
    }
    TplDatasource ds = CacheUtil.get(CACHE_PREFIX + id, TplDatasource.class);
    if (Objects.isNull(ds)) {
      return null;
    }
    IDatasourceFactory factory = this.getDatasourceFactory(ds.getType());
    if (Objects.isNull(factory)) {
      return null;
    }
    return factory.connectDatasource(id, ds.getName(), ds.getConfig());
  }

  /**
   * 获取数据源
   *
   * @param id 数据源ID
   * @return 数据源
   */
  @Nullable
  public IDatasource getDatasource(String id) {
    if (StringUtils.hasText(id)) {
      if (this.datasources.containsKey(id)) {
        return this.datasources.get(id);
      } else {
        // 未添加则先连接后添加
        IDatasource datasource = this.connectDatasource(id);
        if (Objects.nonNull(datasource)) {
          this.addDatasource(id, datasource);
        }
        return datasource;
      }
    }
    return null;
  }

  /**
   * 移除数据源
   *
   * @param id 数据源ID
   */
  public void removeDatasource(String id) {
    if (StringUtils.hasText(id)) {
      if (this.datasources.containsKey(id)) {
        IDatasource datasource = this.datasources.get(id);
        datasource.close();
        this.datasources.remove(id);
      }
      CacheUtil.remove(CACHE_PREFIX + id);
    }
  }

  /**
   * 获取数据源类型
   *
   * @return 类型集合
   */
  @NonNull
  public Set<String> getTypes() {
    return this.factories.keySet();
  }
}
