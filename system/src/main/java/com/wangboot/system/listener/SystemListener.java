package com.wangboot.system.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangboot.core.auth.AuthFlow;
import com.wangboot.core.auth.context.ILoginUser;
import com.wangboot.core.auth.event.LogStatus;
import com.wangboot.core.auth.event.UserEvent;
import com.wangboot.core.auth.event.UserEventLog;
import com.wangboot.core.auth.user.IUserModel;
import com.wangboot.core.reliability.counter.ICounter;
import com.wangboot.core.utils.StrUtils;
import com.wangboot.core.web.param.IParamConfig;
import com.wangboot.core.web.utils.ServletUtils;
import com.wangboot.framework.ParamConstants;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.event.OperationEvent;
import com.wangboot.model.entity.event.OperationEventType;
import com.wangboot.model.entity.event.OperationLog;
import com.wangboot.system.entity.SysOperationLog;
import com.wangboot.system.entity.SysUserLog;
import com.wangboot.system.entity.table.SysBgTaskTableDef;
import com.wangboot.system.event.BgTaskEvent;
import com.wangboot.system.event.BgTaskObject;
import com.wangboot.system.event.BgTaskResult;
import com.wangboot.system.service.SysBgTaskService;
import com.wangboot.system.service.SysOperationLogService;
import com.wangboot.system.service.SysUserLogService;
import com.wangboot.system.service.SysUserService;
import java.time.OffsetDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SystemListener {

  private final ObjectMapper objectMapper;

  private final SysUserLogService userLogService;

  private final SysOperationLogService operationLogService;

  private final SysUserService userService;

  private final IParamConfig paramConfig;

  private final SysBgTaskService bgTaskService;

  /** 处理用户事件，插入用户日志 */
  @EventListener
  @Async
  public void listenUserEvent(UserEvent event) {
    UserEventLog userEventLog = event.getSourceObject();
    ILoginUser loginUser = userEventLog.getLoginUser();
    SysUserLog userLog =
        SysUserLog.builder()
            .event(userEventLog.getEvent())
            .status(userEventLog.getStatus())
            .message(userEventLog.getMessage())
            .userId(Objects.nonNull(loginUser) ? loginUser.getUser().getUserId() : "")
            .username(userEventLog.getUsername())
            .frontendId(Objects.nonNull(loginUser) ? loginUser.getFrontend().getId() : "")
            .frontendName(Objects.nonNull(loginUser) ? loginUser.getFrontend().getName() : "")
            .ip(ServletUtils.getRemoteIp(event.getRequest()))
            .ua(ServletUtils.getUserAgent(event.getRequest()))
            .build();
    userLog.setCreatedBy(userLog.getUserId());
    userLog.setCreatedTime(OffsetDateTime.now());
    boolean ret = this.userLogService.createResource(userLog);
    if (!ret) {
      log.error("插入用户日志失败！{}", userEventLog);
    }
    // 用户登录失败计数
    if (userEventLog.getEvent().equals(AuthFlow.EVENT_LOGIN)
        && userEventLog.getStatus().equals(LogStatus.FAILED)) {
      long limitCount =
          StrUtils.getLongPrimitive(
              this.paramConfig.getParamConfig(ParamConstants.LOGIN_FAILED_THRESHOLD_KEY), 0L);
      if (limitCount > 0) {
        ICounter loginFailedCount = SpringUtil.getBean("loginFailedCount");
        IUserModel userModel = this.userService.getUserModelByUsername(userEventLog.getUsername());
        if (Objects.nonNull(userModel)) {
          loginFailedCount.increment(userModel.getUserId());
          if (loginFailedCount.getCount(userModel.getUserId()) >= limitCount) {
            // 锁定用户
            log.info("用户 {} 登录失败过多被锁定！", userModel.getUsername());
            this.userService.lockUser(userModel.getUserId());
          }
        }
      }
    }
  }

  /** 处理数据操作事件，插入操作日志 */
  @EventListener
  @Async
  public void listenOperationEvent(OperationEvent event) {
    OperationLog operationLog = event.getSourceObject();
    String e = operationLog.getEvent();
    if (OperationEventType.CREATED_EVENT.equals(e)
        || OperationEventType.UPDATED_EVENT.equals(e)
        || OperationEventType.DELETED_EVENT.equals(e)) {
      // 仅 CREATED, UPDATED, DELETED 事件记录
      String body;
      try {
        body = this.objectMapper.writeValueAsString(operationLog.getObj());
      } catch (JsonProcessingException ignored) {
        body = "";
      }
      IUserModel userModel = this.userService.getUserModelById(operationLog.getUserId());
      String username = Objects.nonNull(userModel) ? userModel.getUsername() : "";
      SysOperationLog opeLog =
          SysOperationLog.builder()
              .event(e)
              .resource(operationLog.getResource())
              .resourceId(operationLog.getResourceId().toString())
              .obj(body)
              .username(username)
              .build();
      opeLog.setCreatedBy(operationLog.getUserId());
      opeLog.setCreatedTime(OffsetDateTime.now());
      boolean ret = this.operationLogService.createResource(opeLog);
      if (!ret) {
        log.error("插入操作日志失败！{}", operationLog);
      }
    }
  }

  /** 处理后台任务事件 */
  @EventListener
  @Async
  public void listenBgTaskEvent(BgTaskEvent event) {
    BgTaskObject taskObject = event.getSourceObject();
    BgTaskResult result = taskObject.getSupplier().get();
    bgTaskService
        .updateChain()
        .eq(FieldConstants.PRIMARY_KEY, taskObject.getId())
        .set(SysBgTaskTableDef.SYS_BG_TASK.STATUS, result.getStatus())
        .set(SysBgTaskTableDef.SYS_BG_TASK.RESULT, result.getResult())
        .set(SysBgTaskTableDef.SYS_BG_TASK.ATTACHMENT_ID, result.getAttachmentId())
        .update();
    log.info(
        "后台任务 {} {} 执行完成：{}",
        taskObject.getName(),
        taskObject.getId(),
        result.getStatus().getName());
  }
}
