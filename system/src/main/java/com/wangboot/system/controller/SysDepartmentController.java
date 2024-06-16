package com.wangboot.system.controller;

import com.wangboot.core.auth.annotation.RequireStaff;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.controller.ControllerApi;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiController;
import com.wangboot.model.entity.request.ParamFilterDefinition;
import com.wangboot.model.entity.request.SearchStrategy;
import com.wangboot.model.entity.request.SortFilter;
import com.wangboot.system.entity.SysDepartment;
import com.wangboot.system.entity.table.SysDepartmentTableDef;
import com.wangboot.system.service.SysDepartmentService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制层。
 *
 * @author wwtg99
 */
@RestController
@RequireStaff
@RestPermissionPrefix(group = "system", name = "department")
@RequestMapping("/system/department")
@EnableApi(
    value = ControllerApiGroup.TREE_FULL,
    excludes = {ControllerApi.BATCH_REMOVE})
public class SysDepartmentController
    extends RestfulApiController<String, SysDepartment, SysDepartmentService> {

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {new SortFilter(FieldConstants.UPDATED_TIME, false)};
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {
      SysDepartmentTableDef.SYS_DEPARTMENT.NAME.getName(),
      SysDepartmentTableDef.SYS_DEPARTMENT.FULLNAME.getName()
    };
  }

  @NonNull
  @Override
  public SearchStrategy configSearchStrategy() {
    return SearchStrategy.BOTH_LIKE;
  }

  @Override
  public ParamFilterDefinition configParamFilterDefinition() {
    return ParamFilterDefinition.newInstance()
        .addFilter("name")
        .addFilter(FieldConstants.PARENT_ID_CAMEL);
  }
}
