package com.wangboot.app.execution.render.word;

import com.deepoove.poi.XWPFTemplate;
import com.wangboot.app.execution.render.BaseTemplateRender;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class PoiWordTemplateRender extends BaseTemplateRender {

  private final static String CONTENT_TYPE = "";

  @Override
  public String getContentType() {
    return CONTENT_TYPE;
  }

  @Override
  public void renderAndOutput(Object dataModel, OutputStream outputStream) throws IOException {
    try (ByteArrayInputStream bai = new ByteArrayInputStream(this.getTemplateBytes())) {
      XWPFTemplate xwpfTemplate = XWPFTemplate.compile(bai);
      xwpfTemplate.render(dataModel).writeAndClose(outputStream);
    }
  }
}
