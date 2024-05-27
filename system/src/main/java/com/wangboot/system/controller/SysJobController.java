package com.wangboot.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wangboot.core.auth.annotation.RequireStaff;
import com.wangboot.core.auth.annotation.RestPermissionAction;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.core.auth.authorization.resource.ApiResource;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.response.ListBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiController;
import com.wangboot.model.entity.request.FieldFilter;
import com.wangboot.model.entity.request.ParamFilterDefinition;
import com.wangboot.model.entity.request.SearchStrategy;
import com.wangboot.model.entity.request.SortFilter;
import com.wangboot.system.attachment.IBgExportApi;
import com.wangboot.system.attachment.IExporter;
import com.wangboot.system.entity.SysBgTask;
import com.wangboot.system.entity.SysJob;
import com.wangboot.system.entity.dto.SysJobExcel;
import com.wangboot.system.entity.table.SysJobTableDef;
import com.wangboot.system.service.SysBgTaskService;
import com.wangboot.system.service.SysJobService;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

/**
 * 控制层。
 *
 * @author wwtg99
 */
@RequiredArgsConstructor
@RestController
@RequireStaff
@RestPermissionPrefix(group = "system", name = "job")
@RequestMapping("/system/job")
@EnableApi(ControllerApiGroup.FULL)
public class SysJobController extends RestfulApiController<String, SysJob, SysJobService>
    implements IBgExportApi<SysJobExcel> {

  @Getter private final IExporter exporter;

  @Getter private final SysBgTaskService bgTaskService;

  @Override
  public String[] configSearchableFields() {
    return new String[] {SysJobTableDef.SYS_JOB.NAME.getName()};
  }

  @NonNull
  @Override
  public SearchStrategy configSearchStrategy() {
    return SearchStrategy.BOTH_LIKE;
  }

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {new SortFilter(FieldConstants.UPDATED_TIME, false)};
  }

  @Override
  public ParamFilterDefinition configParamFilterDefinition() {
    return ParamFilterDefinition.newInstance().addFilter("name").addFilter("type");
  }

  @PostMapping({"/export"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  public ResponseEntity<?> export() {
    final SortFilter[] sortFilters = this.parseSortFilters();
    final FieldFilter[] fieldFilters = this.parseParamFilters();
    final FieldFilter[] searchFilters = this.parseSearchFilters();
    SysBgTask bgTask =
        this.bgExport(
            "岗位数据导出",
            SysJobExcel.class,
            () -> {
              ListBody<SysJob> entities =
                  this.getReadService().listResourcesAll(sortFilters, fieldFilters, searchFilters);
              return entities.getData().stream()
                  .map(d -> BeanUtil.copyProperties(d, SysJobExcel.class))
                  .collect(Collectors.toList());
            });
    return ResponseUtils.success(DetailBody.ok(bgTask));
  }
}
