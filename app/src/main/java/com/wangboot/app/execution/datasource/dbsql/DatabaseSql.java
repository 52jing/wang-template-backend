package com.wangboot.app.execution.datasource.dbsql;

import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.datasource.DataSourceKey;
import com.mybatisflex.core.datasource.FlexDataSource;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.wangboot.app.execution.datasource.IDatasource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库SQL数据源<br>
 * 通过执行SQL从数据库中获取数据
 *
 * @author wwtg99
 */
@AllArgsConstructor
public class DatabaseSql implements IDatasource {

  @Getter
  private final String name;

  @Getter
  private final DatabaseSqlConfig config;

  private static final PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("${", "}", ":", false);

  @Override
  @Nullable
  public Object retrieveData(Map<String, String> params) {
    try {
      DataSourceKey.use(this.getName());
      String sql = propertyPlaceholderHelper.replacePlaceholders(this.config.getSql(), params::get);
      List<Row> obj = Db.selectListBySql(sql);
      if (obj.size() == 1) {
        return obj.get(0);
      }
      return obj;
    } finally {
      DataSourceKey.clear();
    }
  }

  /**
   * 配置数据库连接
   */
  public void configDatasource() {
    FlexDataSource flexDataSource = FlexGlobalConfig.getDefaultConfig().getDataSource();
    DruidDataSource newDataSource = new DruidDataSource();
    Properties properties = new Properties();
    properties.put("druid.name", this.getName());
    properties.put("druid.url", this.config.getUrl());
    properties.put("druid.username", this.config.getUsername());
    properties.put("druid.password", this.config.getPassword());
    newDataSource.configFromPropeties(properties);
    flexDataSource.addDataSource(this.getName(), newDataSource);
  }
}
