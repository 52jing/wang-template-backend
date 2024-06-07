package com.wangboot.app.execution.render;

import com.wangboot.app.execution.datasource.DatasourceParamHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RenderContext {

  private String templateName;

  private String datasourceName;

  private DatasourceParamHolder params;

  private Map<String, Object> envs;

  private Object data;

}
