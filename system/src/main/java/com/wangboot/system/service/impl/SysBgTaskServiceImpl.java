package com.wangboot.system.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.core.web.utils.ServletUtils;
import com.wangboot.system.entity.SysBgTask;
import com.wangboot.system.event.BgTaskEvent;
import com.wangboot.system.event.BgTaskObject;
import com.wangboot.system.event.BgTaskResult;
import com.wangboot.system.mapper.SysBgTaskMapper;
import com.wangboot.system.service.SysBgTaskService;
import java.util.function.Supplier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class SysBgTaskServiceImpl extends ServiceImpl<SysBgTaskMapper, SysBgTask>
    implements SysBgTaskService {

  @Override
  @Nullable
  public SysBgTask addBackgroundTask(
      String name, String type, @NonNull Supplier<BgTaskResult> supplier) {
    // 保存后台任务记录
    SysBgTask bgTask = new SysBgTask();
    bgTask.setName(name);
    bgTask.setType(type);
    boolean ret = this.createResource(bgTask);
    if (ret) {
      // 发送后台任务事件
      BgTaskObject bgTaskObject = new BgTaskObject(bgTask.getId(), name, type, supplier);
      SpringUtil.publishEvent(new BgTaskEvent(bgTaskObject, ServletUtils.getRequest()));
      return bgTask;
    }
    return null;
  }
}
