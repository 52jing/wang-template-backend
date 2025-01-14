package com.wangboot.app;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.RandomUtil;
import com.wangboot.app.execution.render.RenderContext;
import com.wangboot.app.execution.render.text.FreeMarkerTemplateRender;
import com.wangboot.app.execution.render.word.PoiWordTemplateRender;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("模板测试")
public class TemplateTest {

  @BeforeAll
  public static void beforeAll() {
    File tmpDir = new File("tmp");
    if (!tmpDir.exists()) {
      FileUtil.mkdir(tmpDir);
    }
  }

  @Test
  @SneakyThrows
  public void testPoiWordTemplate() {
    String outName = "tmp/out.docx";
    File outFile = new File(outName);
    if (FileUtil.exist(outFile)) {
      FileUtil.del(outFile);
    }
    String templateName = RandomUtil.randomString(6);
    PoiWordTemplateRender templateRender = new PoiWordTemplateRender();
    byte[] bytes = ResourceUtil.readBytes("word.docx");
    templateRender.setTemplate(templateName, bytes);
    RenderContext context = new RenderContext();
    Map<String, Object> map = new HashMap<>();
    map.put("title", "Wang");
    context.setData(map);
    FileOutputStream outputStream = new FileOutputStream(outFile);
    templateRender.renderAndOutput(context, outputStream);
    Assertions.assertTrue(FileUtil.exist(outFile));
  }

  @Test
  @SneakyThrows
  public void testFreeMarkerTemplateRender() {
    String outName = "tmp/out.md";
    File outFile = new File(outName);
    if (FileUtil.exist(outFile)) {
      FileUtil.del(outFile);
    }
    String templateName = RandomUtil.randomString(6);
    FreeMarkerTemplateRender templateRender = new FreeMarkerTemplateRender();
    byte[] bytes = ResourceUtil.readBytes("text.tpl");
    templateRender.setTemplate(templateName, bytes);
    RenderContext context = new RenderContext();
    Map<String, Object> map = new HashMap<>();
    map.put("title", "Wang");
    context.setData(map);
    FileOutputStream outputStream = new FileOutputStream(outFile);
    templateRender.renderAndOutput(context, outputStream);
    Assertions.assertTrue(FileUtil.exist(outFile));
  }
}
