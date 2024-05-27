package com.wangboot.task.entity.vo;

import com.mybatisflex.annotation.RelationManyToOne;
import com.wangboot.task.entity.TaskInsideMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskInsideMessageWithUser extends TaskInsideMessage {

  @RelationManyToOne(
      selfField = "fromUserId",
      targetTable = "wb_sys_user",
      targetField = "id",
      valueField = "username")
  private String fromUser;

  @RelationManyToOne(
      selfField = "toUserId",
      targetTable = "wb_sys_user",
      targetField = "id",
      valueField = "username")
  private String toUser;
}
