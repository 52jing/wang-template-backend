package com.wangboot.system.controller;

import com.wangboot.core.auth.annotation.RequireStaff;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiController;
import com.wangboot.model.entity.request.ParamFilterDefinition;
import com.wangboot.model.entity.request.SearchStrategy;
import com.wangboot.model.entity.request.SortFilter;
import com.wangboot.system.entity.SysPermission;
import com.wangboot.system.entity.table.SysPermissionTableDef;
import com.wangboot.system.service.SysPermissionService;
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
@RestPermissionPrefix(group = "system", name = "permission")
@RequestMapping("/system/permission")
@EnableApi(ControllerApiGroup.FULL)
public class SysPermissionController
    extends RestfulApiController<String, SysPermission, SysPermissionService> {
  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {new SortFilter(FieldConstants.UPDATED_TIME, false)};
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {
      SysPermissionTableDef.SYS_PERMISSION.NAME.getName(),
      SysPermissionTableDef.SYS_PERMISSION.LABEL.getName()
    };
  }

  @NonNull
  @Override
  public SearchStrategy configSearchStrategy() {
    return SearchStrategy.BOTH_LIKE;
  }

  @Override
  public ParamFilterDefinition configParamFilterDefinition() {
    return ParamFilterDefinition.newInstance().addFilter("name");
  }
}
