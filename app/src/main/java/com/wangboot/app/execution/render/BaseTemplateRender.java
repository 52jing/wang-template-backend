package com.wangboot.app.execution.render;

import com.wangboot.app.execution.RenderErrorCode;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.framework.exception.ErrorCode;
import java.util.Objects;
import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * 模板渲染器抽象基类
 *
 * @author wwtg99
 */
public abstract class BaseTemplateRender implements ITemplateRender {

  @Getter private String templateName;

  @Getter private byte[] templateBytes;

  @Override
  public ITemplateRender setTemplate(String name, byte[] bytes) {
    if (!StringUtils.hasText(name) || Objects.isNull(bytes) || bytes.length == 0) {
      throw new ErrorCodeException(RenderErrorCode.INVALID_TEMPLATE);
    }
    this.templateName = name;
    this.templateBytes = bytes;
    return this;
  }
}
