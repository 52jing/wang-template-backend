package com.wangboot.system.service;

import com.wangboot.model.flex.IFlexRestfulService;
import com.wangboot.system.entity.SysBgTask;
import com.wangboot.system.event.BgTaskResult;
import java.util.function.Supplier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface SysBgTaskService extends IFlexRestfulService<String, SysBgTask> {

  /** 添加后台任务 */
  @Nullable
  SysBgTask addBackgroundTask(String name, String type, @NonNull Supplier<BgTaskResult> supplier);
}
