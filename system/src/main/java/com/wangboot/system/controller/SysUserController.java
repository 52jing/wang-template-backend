package com.wangboot.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wangboot.core.auth.annotation.RequireStaff;
import com.wangboot.core.auth.annotation.RestPermissionAction;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.core.auth.authorization.resource.ApiResource;
import com.wangboot.core.auth.user.IUserModel;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.response.ListBody;
import com.wangboot.core.web.task.IBackgroundTask;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.model.attachment.IExcelExporter;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiReadWriteController;
import com.wangboot.model.entity.exception.NotFoundException;
import com.wangboot.model.entity.request.*;
import com.wangboot.system.attachment.IBgExportApi;
import com.wangboot.system.entity.SysRole;
import com.wangboot.system.entity.SysUser;
import com.wangboot.system.entity.dto.SysUserExcel;
import com.wangboot.system.entity.table.SysUserTableDef;
import com.wangboot.system.entity.vo.SysUserView;
import com.wangboot.system.event.BgTaskResult;
import com.wangboot.system.model.ChangePasswordBody;
import com.wangboot.system.service.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
@RestController
@RequiredArgsConstructor
@RequireStaff
@RestPermissionPrefix(group = "system", name = "user")
@RequestMapping("/system/user")
@EnableApi(ControllerApiGroup.FULL)
public class SysUserController
    extends RestfulApiReadWriteController<
        String, SysUserView, SysUserViewService, SysUser, SysUserService>
    implements IBgExportApi<SysUserExcel> {

  private final SysRoleService roleService;

  private final AuthService authService;

  @Getter private final IExcelExporter exporter;

  @Getter private final SysBgTaskService bgTaskService;

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {new SortFilter(FieldConstants.UPDATED_TIME, false)};
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {
      SysUserTableDef.SYS_USER.USERNAME.getName(),
      SysUserTableDef.SYS_USER.NICKNAME.getName(),
      SysUserTableDef.SYS_USER.EMAIL.getName(),
      SysUserTableDef.SYS_USER.TEL.getName()
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
        .addFilter("username")
        .addFilter("email")
        .addFilter("tel")
        .addFilter("departmentId")
        .addFilter("jobId")
        .addFilter(
            "superuser",
            new FieldFilter(
                SysUserTableDef.SYS_USER.SUPERUSER.getName(), FilterOperator.EQ, ParamValType.BOOL))
        .addFilter(
            "staff",
            new FieldFilter(
                SysUserTableDef.SYS_USER.STAFF.getName(), FilterOperator.EQ, ParamValType.BOOL))
        .addFilter(
            "active",
            new FieldFilter(
                SysUserTableDef.SYS_USER.ACTIVE.getName(), FilterOperator.EQ, ParamValType.BOOL));
  }

  @GetMapping({"/{id}/roles"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> getUserRolesApi(@PathVariable String id) {
    SysUser user = this.getWriteEntityService().viewResource(id);
    if (Objects.isNull(user)) {
      throw new NotFoundException();
    }
    List<SysRole> data = this.getWriteEntityService().getUserRoles(Collections.singletonList(id));
    return ResponseUtils.success(DetailBody.ok(data));
  }

  @PostMapping({"/{id}/roles"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> addUserRolesApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    List<SysRole> entities = this.roleService.viewResources(entitiesIds.getIds());
    boolean ret = this.getWriteEntityService().addUserRoles(id, entities);
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @PutMapping({"/{id}/roles"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> setUserRolesApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    List<SysRole> entities = this.roleService.viewResources(entitiesIds.getIds());
    boolean ret = this.getWriteEntityService().setUserRoles(id, entities);
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @DeleteMapping({"/{id}/roles"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> removeUserRolesApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    boolean ret =
        this.getWriteEntityService().removeUserRoles(id, new ArrayList<>(entitiesIds.getIds()));
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  /** 管理员设置密码 */
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @PostMapping("/{id}/set_password")
  public ResponseEntity<?> setPassword(
      @PathVariable String id, @RequestBody ChangePasswordBody changePasswordBody) {
    IUserModel user = this.getWriteService().viewResource(id);
    if (Objects.isNull(user)) {
      throw new NotFoundException();
    }
    boolean ret = authService.setPassword(user, changePasswordBody);
    if (ret) {
      return ResponseUtils.success(DetailBody.ok(true));
    } else {
      throw new ErrorCodeException(ErrorCode.PASSWORD_CHANGE_FAILED);
    }
  }

  @PostMapping({"/export"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  public ResponseEntity<?> export() {
    final SortFilter[] sortFilters = this.parseSortFilters();
    final FieldFilter[] fieldFilters = this.parseParamFilters();
    final FieldFilter[] searchFilters = this.parseSearchFilters();
    IBackgroundTask<BgTaskResult> bgTask =
        this.bgExport(
            "用户数据导出",
            SysUserExcel.class,
            () -> {
              ListBody<SysUserView> entities =
                  this.getReadService().listResourcesAll(sortFilters, fieldFilters, searchFilters);
              return entities.getData().stream()
                  .map(d -> BeanUtil.copyProperties(d, SysUserExcel.class))
                  .collect(Collectors.toList());
            });
    return ResponseUtils.success(DetailBody.ok(bgTask));
  }
}
