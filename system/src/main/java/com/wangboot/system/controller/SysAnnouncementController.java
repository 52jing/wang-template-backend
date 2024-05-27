package com.wangboot.system.controller;

import com.wangboot.core.auth.annotation.RequireStaff;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiController;
import com.wangboot.model.entity.request.*;
import com.wangboot.system.entity.SysAnnouncement;
import com.wangboot.system.entity.table.SysAnnouncementTableDef;
import com.wangboot.system.service.SysAnnouncementService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequireStaff
@RestPermissionPrefix(group = "system", name = "announcement")
@RequestMapping("/system/announcement")
@EnableApi(ControllerApiGroup.FULL)
public class SysAnnouncementController
    extends RestfulApiController<String, SysAnnouncement, SysAnnouncementService> {

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {new SortFilter(FieldConstants.UPDATED_TIME, false)};
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {SysAnnouncementTableDef.SYS_ANNOUNCEMENT.TITLE.getName()};
  }

  @NonNull
  @Override
  public SearchStrategy configSearchStrategy() {
    return SearchStrategy.BOTH_LIKE;
  }

  @Override
  public ParamFilterDefinition configParamFilterDefinition() {
    return ParamFilterDefinition.newInstance()
        .addFilter("type")
        .addFilter(
            "display",
            new FieldFilter(
                SysAnnouncementTableDef.SYS_ANNOUNCEMENT.DISPLAY.getName(),
                FilterOperator.EQ,
                ParamValType.BOOL));
  }
}
