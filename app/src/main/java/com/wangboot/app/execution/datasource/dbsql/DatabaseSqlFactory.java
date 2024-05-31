package com.wangboot.app.execution.datasource.dbsql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangboot.app.execution.datasource.IDatasource;
import com.wangboot.app.execution.datasource.IDatasourceFactory;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.framework.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * 数据库SQL数据源工厂
 * @author wwtg99
 */
@RequiredArgsConstructor
@Slf4j
public class DatabaseSqlFactory implements IDatasourceFactory {

  private final ObjectMapper objectMapper;

  @Nullable
  @Override
  public IDatasource connectDatasource(String id, String name, String config) {
    if (!StringUtils.hasText(id) || !StringUtils.hasText(name) || !StringUtils.hasText(config)) {
      return null;
    }
    try {
      DatabaseSqlConfig databaseSqlConfig = this.objectMapper.readValue(config, DatabaseSqlConfig.class);
      DatabaseSql datasource = new DatabaseSql(name, databaseSqlConfig);
      datasource.configDatasource();
      return datasource;
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
      throw new ErrorCodeException(ErrorCode.INVALID_DATASOURCE_CONFIG);
    }
  }
}
