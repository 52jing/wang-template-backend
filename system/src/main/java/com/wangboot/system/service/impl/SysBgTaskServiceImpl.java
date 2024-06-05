package com.wangboot.system.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.core.web.response.ListBody;
import com.wangboot.core.web.task.IBackgroundTask;
import com.wangboot.core.web.utils.ServletUtils;
import com.wangboot.model.attachment.IAttachmentService;
import com.wangboot.model.entity.request.FieldFilter;
import com.wangboot.model.entity.request.SortFilter;
import com.wangboot.system.entity.SysBgTask;
import com.wangboot.system.event.BgTaskEvent;
import com.wangboot.system.event.BgTaskObject;
import com.wangboot.system.event.BgTaskResult;
import com.wangboot.system.mapper.SysBgTaskMapper;
import com.wangboot.system.service.SysAttachmentService;
import com.wangboot.system.service.SysBgTaskService;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysBgTaskServiceImpl extends ServiceImpl<SysBgTaskMapper, SysBgTask>
    implements SysBgTaskService {

  private final SysAttachmentService attachmentService;

  @Override
  @NonNull
  public ListBody<SysBgTask> listResourcesPage(
      @Nullable SortFilter[] sortFilters,
      @Nullable FieldFilter[] fieldFilters,
      @Nullable FieldFilter[] searchFilters,
      long page,
      long pageSize) {
    ListBody<SysBgTask> data =
        SysBgTaskService.super.listResourcesPage(
            sortFilters, fieldFilters, searchFilters, page, pageSize);
    data.setData(this.getEntitiesWithAttachments(data.getData()));
    return data;
  }

  @Nullable
  @Override
  public IBackgroundTask<BgTaskResult> addTask(
      String name, String group, Supplier<BgTaskResult> resultSupplier) {
    // 保存后台任务记录
    SysBgTask bgTask = new SysBgTask();
    bgTask.setName(name);
    bgTask.setType(group);
    boolean ret = this.createResource(bgTask);
    if (ret) {
      // 发送后台任务事件
      BgTaskObject bgTaskObject = new BgTaskObject(bgTask.getId(), name, group, resultSupplier);
      SpringUtil.publishEvent(new BgTaskEvent(bgTaskObject, ServletUtils.getRequest()));
      return bgTaskObject;
    }
    return null;
  }

  @Override
  @NonNull
  public IAttachmentService getAttachmentService() {
    return this.attachmentService;
  }

  @Override
  public String getObjectType() {
    return this.getEntityClass().getName();
  }
}
