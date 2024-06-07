package com.wangboot.app.execution.datasource;

import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;

/**
 * 数据源参数持有者
 *
 * @author wwtg99
 */
@NoArgsConstructor
public class DatasourceParamHolder extends HashMap<String, String> {

  public DatasourceParamHolder(Map<String, String> params) {
    this.putAll(params);
  }
}
