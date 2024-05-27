package com.wangboot.system.controller;

import com.wangboot.core.auth.annotation.RequireStaff;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiController;
import com.wangboot.model.entity.request.*;
import com.wangboot.system.entity.SysOperationLog;
import com.wangboot.system.entity.table.SysOperationLogTableDef;
import com.wangboot.system.service.SysOperationLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequireStaff
@RestPermissionPrefix(group = "log", name = "operation_log")
@RequestMapping("/log/operation_log")
@EnableApi(ControllerApiGroup.READ_ONLY)
public class SysOperationLogController
    extends RestfulApiController<String, SysOperationLog, SysOperationLogService> {

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {
      new SortFilter(FieldConstants.CREATED_TIME, false),
    };
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {
      SysOperationLogTableDef.SYS_OPERATION_LOG.EVENT.getName(),
      SysOperationLogTableDef.SYS_OPERATION_LOG.RESOURCE.getName()
    };
  }

  @Override
  public ParamFilterDefinition configParamFilterDefinition() {
    return ParamFilterDefinition.newInstance()
        .addFilter("event")
        .addFilter("resource")
        .addFilter("resourceId")
        .addFilter("username")
        .addFilter(FieldConstants.CREATED_BY_CAMEL)
        .addFilter(
            "start",
            new FieldFilter(FieldConstants.CREATED_TIME, FilterOperator.GE, ParamValType.DATE))
        .addFilter(
            "end",
            new FieldFilter(FieldConstants.CREATED_TIME, FilterOperator.LE, ParamValType.DATE));
  }
}
