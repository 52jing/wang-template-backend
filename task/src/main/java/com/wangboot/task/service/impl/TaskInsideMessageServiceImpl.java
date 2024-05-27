package com.wangboot.task.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.model.entity.exception.CreateFailedException;
import com.wangboot.model.entity.exception.NotFoundException;
import com.wangboot.task.entity.TaskInsideMessage;
import com.wangboot.task.entity.table.TaskInsideMessageTableDef;
import com.wangboot.task.entity.vo.TaskInsideMessageWithUser;
import com.wangboot.task.mapper.TaskInsideMessageMapper;
import com.wangboot.task.service.TaskInsideMessageService;
import java.time.OffsetDateTime;
import java.util.Objects;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TaskInsideMessageServiceImpl
    extends ServiceImpl<TaskInsideMessageMapper, TaskInsideMessage>
    implements TaskInsideMessageService {

  @Override
  @NonNull
  public Page<TaskInsideMessageWithUser> getUserSentMessages(
      String userId, long page, long pageSize) {
    if (!StringUtils.hasText(userId)) {
      return new Page<>();
    }
    QueryWrapper wrapper =
        this.query()
            .where(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.FROM_USER_ID.eq(userId))
            .orderBy(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.CREATED_TIME, false);
    return this.getMapper()
        .paginateWithRelationsAs(Page.of(page, pageSize), wrapper, TaskInsideMessageWithUser.class);
  }

  @Override
  @NonNull
  public Page<TaskInsideMessageWithUser> getUserReceivedMessages(
      boolean unread, String userId, long page, long pageSize) {
    if (!StringUtils.hasText(userId)) {
      return new Page<>();
    }
    QueryWrapper wrapper =
        this.query()
            .where(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.TO_USER_ID.eq(userId))
            .orderBy(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.CREATED_TIME, false);
    if (unread) {
      wrapper.where(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.READ_TIME.isNull());
    }
    return this.getMapper()
        .paginateWithRelationsAs(Page.of(page, pageSize), wrapper, TaskInsideMessageWithUser.class);
  }

  @Override
  public boolean sendMessage(@Nullable TaskInsideMessage message, String sendUserId) {
    if (Objects.isNull(message)
        || !StringUtils.hasText(message.getToUserId())
        || !StringUtils.hasText(message.getTitle())
        || !StringUtils.hasText(sendUserId)) {
      throw new CreateFailedException();
    }
    message.setFromUserId(sendUserId);
    return this.createResource(message);
  }

  @Override
  public boolean readMessage(String id, String userId) {
    if (!StringUtils.hasText(id) || !StringUtils.hasText(userId)) {
      throw new NotFoundException();
    }
    return this.updateChain()
        .where(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.ID.eq(id))
        .where(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.TO_USER_ID.eq(userId))
        .where(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.READ_TIME.isNull())
        .set(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.READ_TIME, OffsetDateTime.now())
        .update();
  }

  @Override
  public boolean markAllRead(String userId) {
    if (!StringUtils.hasText(userId)) {
      return false;
    }
    return this.updateChain()
        .where(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.TO_USER_ID.eq(userId))
        .where(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.READ_TIME.isNull())
        .set(TaskInsideMessageTableDef.TASK_INSIDE_MESSAGE.READ_TIME, OffsetDateTime.now())
        .update();
  }
}
