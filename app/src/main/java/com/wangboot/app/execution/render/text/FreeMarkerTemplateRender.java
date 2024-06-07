package com.wangboot.app.execution.render.text;

import com.wangboot.app.execution.render.RenderContext;
import com.wangboot.app.template.entity.TplTemplate;
import com.wangboot.app.execution.render.BaseTemplateRender;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.framework.exception.ErrorCode;
import freemarker.cache.ByteArrayTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

@Slf4j
public class FreeMarkerTemplateRender extends BaseTemplateRender {

  public static final String DEFAULT_ENCODING = "UTF-8";
  private static final String TEMPLATE_NAME = "template";
  private static final String CONTENT_TYPE = "text/plain";

  private final Configuration configuration;

  private final ByteArrayTemplateLoader templateLoader;

  @Getter
  private TplTemplate template;

  public FreeMarkerTemplateRender() {
    this.configuration = new Configuration(Configuration.VERSION_2_3_32);
    this.configuration.setDefaultEncoding(DEFAULT_ENCODING);
    this.templateLoader = new ByteArrayTemplateLoader();
    this.configuration.setTemplateLoader(this.templateLoader);
  }

  @Override
  public String getContentType() {
    return CONTENT_TYPE;
  }

  @Override
  public void renderAndOutput(RenderContext context, OutputStream outputStream) throws IOException {
    this.templateLoader.putTemplate(TEMPLATE_NAME, this.getTemplateBytes());
    try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, Charset.forName(DEFAULT_ENCODING))) {
      this.configuration.getTemplate(TEMPLATE_NAME).process(context, writer);
      writer.flush();
    } catch (TemplateException e) {
      log.error(e.getMessage());
      throw new ErrorCodeException(ErrorCode.INVALID_TEMPLATE);
    }
  }
}
