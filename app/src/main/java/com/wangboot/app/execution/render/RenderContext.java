package com.wangboot.app.execution.render;

import com.wangboot.app.execution.datasource.DatasourceParamHolder;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
