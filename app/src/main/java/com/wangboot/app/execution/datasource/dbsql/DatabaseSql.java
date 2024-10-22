package com.wangboot.app.execution.datasource.dbsql;

import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.datasource.DataSourceKey;
import com.mybatisflex.core.datasource.FlexDataSource;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.wangboot.app.execution.datasource.DatasourceParamHolder;
import com.wangboot.app.execution.datasource.IDatasource;
import java.util.List;
import java.util.Properties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * 数据库SQL数据源<br>
 * 通过执行SQL从数据库中获取数据
 *
 * @author wwtg99
 */
@AllArgsConstructor
public class DatabaseSql implements IDatasource {

  private static final String DS_PREFIX = "DS_";

  @Getter private final String name;

  @Getter private final DatabaseSqlConfig config;

  private static final PropertyPlaceholderHelper propertyPlaceholderHelper =
      new PropertyPlaceholderHelper("${", "}", ":", false);

  @Override
  @Nullable
  public Object retrieveData(DatasourceParamHolder params) {
    try {
      DataSourceKey.use(this.getDatasourceKey());
      String sql = propertyPlaceholderHelper.replacePlaceholders(this.config.getSql(), params::get);
      List<Row> obj = Db.selectListBySql(sql);
      if (this.config.getExpandOnOne() && obj.size() == 1) {
        return obj.get(0);
      }
      return obj;
    } finally {
      DataSourceKey.clear();
    }
  }

  @Override
  public void close() {
    FlexDataSource flexDataSource = FlexGlobalConfig.getDefaultConfig().getDataSource();
    flexDataSource.removeDatasource(this.getDatasourceKey());
  }

  /** 配置数据库连接 */
  public void configDatasource() {
    FlexDataSource flexDataSource = FlexGlobalConfig.getDefaultConfig().getDataSource();
    DruidDataSource newDataSource = new DruidDataSource();
    Properties properties = new Properties();
    properties.put("druid.name", this.getName());
    properties.put("druid.url", this.config.getUrl());
    properties.put("druid.username", this.config.getUsername());
    properties.put("druid.password", this.config.getPassword());
    newDataSource.configFromPropeties(properties);
    flexDataSource.addDataSource(this.getDatasourceKey(), newDataSource);
  }

  private String getDatasourceKey() {
    return DS_PREFIX + this.getName();
  }
}
