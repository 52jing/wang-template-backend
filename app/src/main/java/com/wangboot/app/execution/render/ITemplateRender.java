package com.wangboot.app.execution.render;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 模板渲染器接口
 * @author wwtg99
 */
public interface ITemplateRender {

  /**
   * 设置模板
   * @param name 模板名称
   * @param bytes 模板字节数组
   * @return 模板渲染器
   */
  ITemplateRender setTemplate(String name, byte[] bytes);

  /**
   * 获取模板名称
   * @return 模板名称
   */
  String getTemplateName();

  /**
   * 获取模板内容
   * @return 字节数组
   */
  byte[] getTemplateBytes();

  /**
   * 获取文档类型
   * @return 文档类型
   */
  String getContentType();

  /**
   * 渲染并输出
   * @param context 上下文
   * @param outputStream 输出流
   * @throws IOException IO异常
   */
  void renderAndOutput(RenderContext context, OutputStream outputStream) throws IOException;

}
