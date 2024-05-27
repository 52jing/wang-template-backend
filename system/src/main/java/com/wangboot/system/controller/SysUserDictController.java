package com.wangboot.system.controller;

import com.wangboot.core.auth.annotation.RequireStaff;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiController;
import com.wangboot.model.entity.request.ParamFilterDefinition;
import com.wangboot.model.entity.request.SearchStrategy;
import com.wangboot.model.entity.request.SortFilter;
import com.wangboot.system.entity.SysUserDict;
import com.wangboot.system.entity.table.SysUserDictTableDef;
import com.wangboot.system.service.SysUserDictService;
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
@RestPermissionPrefix(group = "system", name = "user_dict")
@RequestMapping("/system/user_dict")
@EnableApi(ControllerApiGroup.FULL)
public class SysUserDictController
    extends RestfulApiController<String, SysUserDict, SysUserDictService> {

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {
      new SortFilter(SysUserDictTableDef.SYS_USER_DICT.DICT_GROUP.getName()),
      new SortFilter(SysUserDictTableDef.SYS_USER_DICT.SORT.getName())
    };
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {
      SysUserDictTableDef.SYS_USER_DICT.NAME.getName(),
      SysUserDictTableDef.SYS_USER_DICT.DICT_GROUP.getName(),
      SysUserDictTableDef.SYS_USER_DICT.DICT_CODE.getName()
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
        .addFilter("dictGroup")
        .addFilter("dictCode");
  }
}
