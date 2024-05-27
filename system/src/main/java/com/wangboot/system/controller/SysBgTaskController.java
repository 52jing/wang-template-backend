package com.wangboot.system.controller;

import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiController;
import com.wangboot.model.entity.request.*;
import com.wangboot.system.entity.SysBgTask;
import com.wangboot.system.service.SysBgTaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/bg_task")
@EnableApi(ControllerApiGroup.READ_ONLY)
public class SysBgTaskController extends RestfulApiController<String, SysBgTask, SysBgTaskService> {

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {new SortFilter(FieldConstants.CREATED_TIME, false)};
  }

  @Override
  public ParamFilterDefinition configParamFilterDefinition() {
    return ParamFilterDefinition.newInstance()
        .addFilter("name")
        .addFilter("type")
        .addFilter("status")
        .addFilter(
            "start",
            new FieldFilter(FieldConstants.CREATED_TIME, FilterOperator.GE, ParamValType.DATE))
        .addFilter(
            "end",
            new FieldFilter(FieldConstants.CREATED_TIME, FilterOperator.LE, ParamValType.DATE));
  }
}
