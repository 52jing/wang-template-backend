package com.wangboot.app.execution.datasource.dbsql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库SQL数据源配置类
 *
 * @author wwtg99
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatabaseSqlConfig {

  private String url;
  private String username;
  private String password;
  private String sql;
}
