package com.wangboot.app.template.service;

import com.wangboot.app.template.entity.TplExecutionResult;
import com.wangboot.app.template.entity.TplRenderExecution;
import com.wangboot.model.flex.IFlexRestfulService;
import java.util.List;

public interface TplRenderExecutionService extends IFlexRestfulService<String, TplRenderExecution> {

  /**
   * 获取执行结果
   *
   * @param id 执行ID
   * @return 结果列表
   */
  List<TplExecutionResult> getResults(String id);
}
