package com.wangboot.task.controller;

import com.mybatisflex.core.paginate.Page;
import com.wangboot.core.auth.utils.AuthUtils;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.response.ListBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.model.entity.exception.CreateFailedException;
import com.wangboot.task.entity.TaskInsideMessage;
import com.wangboot.task.entity.vo.TaskInsideMessageWithUser;
import com.wangboot.task.service.TaskInsideMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 站内信接口
 *
 * @author wwtg99
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/task/message")
public class TaskInsideMessageController {

  private final TaskInsideMessageService taskInsideMessageService;

  /** 获取站内信详情 */
  @GetMapping("/{id}")
  public ResponseEntity<?> getMessage(@PathVariable String id) {
    return ResponseUtils.success(
        DetailBody.ok(
            this.taskInsideMessageService
                .getMapper()
                .selectOneWithRelationsByIdAs(id, TaskInsideMessageWithUser.class)));
  }

  /** 获取已发送的站内信 */
  @GetMapping("/sent")
  public ResponseEntity<?> getUserSentMessages(
      @RequestParam(defaultValue = "1") long page,
      @RequestParam(defaultValue = "10") long pageSize) {
    Page<TaskInsideMessageWithUser> data =
        this.taskInsideMessageService.getUserSentMessages(AuthUtils.getUserId(), page, pageSize);
    ListBody<TaskInsideMessageWithUser> body =
        new ListBody<>(
            data.getRecords(),
            data.getTotalRow(),
            data.getPageNumber(),
            data.getPageSize(),
            HttpStatus.OK.value());
    return ResponseUtils.success(body);
  }

  /** 获取接收的站内信 */
  @GetMapping("/received")
  public ResponseEntity<?> getUserReceivedMessages(
      @RequestParam boolean unread,
      @RequestParam(defaultValue = "1") long page,
      @RequestParam(defaultValue = "10") long pageSize) {
    Page<TaskInsideMessageWithUser> data =
        this.taskInsideMessageService.getUserReceivedMessages(
            unread, AuthUtils.getUserId(), page, pageSize);
    ListBody<TaskInsideMessageWithUser> body =
        new ListBody<>(
            data.getRecords(),
            data.getTotalRow(),
            data.getPageNumber(),
            data.getPageSize(),
            HttpStatus.OK.value());
    return ResponseUtils.success(body);
  }

  /** 发送站内信 */
  @PostMapping
  public ResponseEntity<?> sendMessage(@RequestBody TaskInsideMessage message) {
    boolean ret = this.taskInsideMessageService.sendMessage(message, AuthUtils.getUserId());
    if (ret) {
      return ResponseUtils.success(DetailBody.ok(message));
    } else {
      throw new CreateFailedException();
    }
  }

  /** 标记站内信已读 */
  @PostMapping("/{id}/read")
  public ResponseEntity<?> readMessage(@PathVariable String id) {
    boolean ret = this.taskInsideMessageService.readMessage(id, AuthUtils.getUserId());
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  /** 标记所有站内信已读 */
  @PostMapping("/all_read")
  public ResponseEntity<?> markAllRead() {
    boolean ret = this.taskInsideMessageService.markAllRead(AuthUtils.getUserId());
    return ResponseUtils.success(DetailBody.ok(ret));
  }
}
