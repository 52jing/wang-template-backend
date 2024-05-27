package com.wangboot.system.controller;

import com.wangboot.core.auth.annotation.RequireStaff;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiController;
import com.wangboot.model.entity.request.*;
import com.wangboot.system.entity.SysUserLog;
import com.wangboot.system.entity.table.SysUserLogTableDef;
import com.wangboot.system.service.SysUserLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequireStaff
@RestPermissionPrefix(group = "log", name = "user_log")
@RequestMapping("/log/user_log")
@EnableApi(ControllerApiGroup.READ_ONLY)
public class SysUserLogController
    extends RestfulApiController<String, SysUserLog, SysUserLogService> {
  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {
      new SortFilter(FieldConstants.CREATED_TIME, false),
    };
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {SysUserLogTableDef.SYS_USER_LOG.EVENT.getName()};
  }

  @Override
  public ParamFilterDefinition configParamFilterDefinition() {
    return ParamFilterDefinition.newInstance()
        .addFilter("event")
        .addFilter("status")
        .addFilter("userId")
        .addFilter("username")
        .addFilter("frontendId")
        .addFilter(
            "frontendName",
            new FieldFilter(
                SysUserLogTableDef.SYS_USER_LOG.FRONTEND_NAME.getName(),
                FilterOperator.CONTAINS,
                ParamValType.STR))
        .addFilter("ip")
        .addFilter(
            "start",
            new FieldFilter(FieldConstants.CREATED_TIME, FilterOperator.GE, ParamValType.DATE))
        .addFilter(
            "end",
            new FieldFilter(FieldConstants.CREATED_TIME, FilterOperator.LE, ParamValType.DATE));
  }
}
