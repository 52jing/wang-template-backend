package com.wangboot.app.execution.render.word;

import com.deepoove.poi.XWPFTemplate;
import com.wangboot.app.execution.render.BaseTemplateRender;
import com.wangboot.app.execution.render.RenderContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PoiWordTemplateRender extends BaseTemplateRender {

  private static final String CONTENT_TYPE = "";

  @Override
  public String getContentType() {
    return CONTENT_TYPE;
  }

  @Override
  public void renderAndOutput(RenderContext context, OutputStream outputStream) throws IOException {
    try (ByteArrayInputStream bai = new ByteArrayInputStream(this.getTemplateBytes())) {
      XWPFTemplate xwpfTemplate = XWPFTemplate.compile(bai);
      xwpfTemplate.render(context).writeAndClose(outputStream);
    }
  }
}
