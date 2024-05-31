package com.wangboot.app.execution;

import com.wangboot.app.template.entity.TplRenderExecution;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * 渲染执行事件
 *
 * @author wwtg99
 */
public class RenderExecutionEvent extends ApplicationEvent {

  @Getter
  private final Map<String, String> params;

  public RenderExecutionEvent(TplRenderExecution renderExecution, Map<String, String> params) {
    super(renderExecution);
    this.params = params;
  }

  public TplRenderExecution getRenderExecution() {
    return (TplRenderExecution) this.getSource();
  }
}
