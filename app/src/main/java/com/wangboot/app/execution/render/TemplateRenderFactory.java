package com.wangboot.app.execution.render;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 模版渲染器工厂
 *
 * @author wwtg99
 */
@Component
public class TemplateRenderFactory {

  private final Map<String, ITemplateRender> map = new HashMap<>();

  /**
   * 注册模板渲染器类型
   *
   * @param type 类型
   * @param templateRender 模版渲染器
   * @return 工厂
   */
  public TemplateRenderFactory registerTemplateRender(String type, ITemplateRender templateRender) {
    if (StringUtils.hasText(type) && Objects.nonNull(templateRender)) {
      this.map.put(type, templateRender);
    }
    return this;
  }

  /**
   * 根据类型获取模版渲染器
   *
   * @param type 类型
   * @return 模版渲染器
   */
  @Nullable
  public ITemplateRender getTemplateRender(String type) {
    if (StringUtils.hasText(type) && this.map.containsKey(type)) {
      return this.map.get(type);
    }
    return null;
  }

  /**
   * 获取所有类型
   *
   * @return 类型集合
   */
  @NonNull
  public Set<String> getAllTypes() {
    return this.map.keySet();
  }

}
