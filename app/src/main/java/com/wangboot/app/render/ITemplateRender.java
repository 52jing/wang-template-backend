package com.wangboot.app.render;

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
   * 渲染并输出
   * @param dataModel 数据
   * @param outputStream 输出流
   * @throws IOException IO异常
   */
  void renderAndOutput(Object dataModel, OutputStream outputStream) throws IOException;

}
