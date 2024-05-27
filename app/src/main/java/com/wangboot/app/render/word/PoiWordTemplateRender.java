package com.wangboot.app.render.word;

import com.deepoove.poi.XWPFTemplate;
import com.wangboot.app.render.BaseTemplateRender;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class PoiWordTemplateRender extends BaseTemplateRender {

  @Override
  public void renderAndOutput(Object dataModel, OutputStream outputStream) throws IOException {
    try (ByteArrayInputStream bai = new ByteArrayInputStream(this.getTemplateBytes())) {
      XWPFTemplate xwpfTemplate = XWPFTemplate.compile(bai);
      xwpfTemplate.render(dataModel).writeAndClose(outputStream);
    }
  }
}
