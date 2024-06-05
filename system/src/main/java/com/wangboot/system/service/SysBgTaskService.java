package com.wangboot.system.service;

import com.wangboot.core.web.task.IBackgroundService;
import com.wangboot.system.attachment.IAttachmentRelatedService;
import com.wangboot.system.entity.SysBgTask;
import com.wangboot.system.event.BgTaskResult;

public interface SysBgTaskService
    extends IAttachmentRelatedService<String, SysBgTask>, IBackgroundService<BgTaskResult> {}
