package com.wangboot.app.execution;

import com.wangboot.app.execution.datasource.DatasourceParamHolder;
import com.wangboot.app.template.entity.TplRenderExecution;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 渲染执行事件
 *
 * @author wwtg99
 */
public class RenderExecutionEvent extends ApplicationEvent {

  @Getter private final DatasourceParamHolder params;

  public RenderExecutionEvent(TplRenderExecution renderExecution, DatasourceParamHolder params) {
    super(renderExecution);
    this.params = params;
  }

  public TplRenderExecution getRenderExecution() {
    return (TplRenderExecution) this.getSource();
  }
}
