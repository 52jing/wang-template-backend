package com.wangboot.task.service;

import com.mybatisflex.core.paginate.Page;
import com.wangboot.model.flex.IFlexRestfulService;
import com.wangboot.task.entity.TaskInsideMessage;
import com.wangboot.task.entity.vo.TaskInsideMessageWithUser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface TaskInsideMessageService extends IFlexRestfulService<String, TaskInsideMessage> {

  @NonNull
  Page<TaskInsideMessageWithUser> getUserSentMessages(String userId, long page, long pageSize);

  @NonNull
  Page<TaskInsideMessageWithUser> getUserReceivedMessages(
      boolean unread, String userId, long page, long pageSize);

  boolean sendMessage(@Nullable TaskInsideMessage message, String sendUserId);

  boolean readMessage(String id, String userId);

  boolean markAllRead(String userId);
}
