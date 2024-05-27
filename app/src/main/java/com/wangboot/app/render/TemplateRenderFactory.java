package com.wangboot.app.render;

import com.wangboot.app.render.text.FreeMarkerTemplateRender;
import com.wangboot.app.render.word.PoiWordTemplateRender;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class TemplateRenderFactory implements InitializingBean {

  private final Map<String, ITemplateRender> map = new HashMap<>();

  public TemplateRenderFactory registerTemplateRender(String type, ITemplateRender templateRender) {
    if (StringUtils.hasText(type) && Objects.nonNull(templateRender)) {
      this.map.put(type, templateRender);
    }
    return this;
  }

  @Nullable
  public ITemplateRender getTemplateRender(String type) {
    if (StringUtils.hasText(type) && this.map.containsKey(type)) {
      return this.map.get(type);
    }
    return null;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.registerTemplateRender("word", new PoiWordTemplateRender());
    this.registerTemplateRender("text", new FreeMarkerTemplateRender());
  }
}
