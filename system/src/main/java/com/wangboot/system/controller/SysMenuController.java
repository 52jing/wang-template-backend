package com.wangboot.system.controller;

import com.wangboot.core.auth.annotation.RequireStaff;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.controller.*;
import com.wangboot.model.entity.request.ParamFilterDefinition;
import com.wangboot.model.entity.request.SearchStrategy;
import com.wangboot.system.entity.SysMenu;
import com.wangboot.system.entity.table.SysMenuTableDef;
import com.wangboot.system.service.SysMenuService;
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
@RestPermissionPrefix(group = "system", name = "menu")
@RequestMapping("/system/menu")
@EnableApi(
    value = ControllerApiGroup.TREE_FULL,
    excludes = {ControllerApi.BATCH_REMOVE})
public class SysMenuController extends RestfulApiController<String, SysMenu, SysMenuService> {

  @Override
  public String[] configSearchableFields() {
    return new String[] {SysMenuTableDef.SYS_MENU.NAME.getName()};
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
