package com.wangboot.system.controller;

import com.wangboot.core.auth.annotation.RequireStaff;
import com.wangboot.core.auth.annotation.RestPermissionAction;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.core.auth.authorization.resource.ApiResource;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiController;
import com.wangboot.model.entity.exception.NotFoundException;
import com.wangboot.model.entity.request.IdListBody;
import com.wangboot.model.entity.request.ParamFilterDefinition;
import com.wangboot.model.entity.request.SearchStrategy;
import com.wangboot.model.entity.request.SortFilter;
import com.wangboot.system.entity.SysPolicy;
import com.wangboot.system.entity.SysRole;
import com.wangboot.system.entity.table.SysRoleTableDef;
import com.wangboot.system.service.SysPolicyService;
import com.wangboot.system.service.SysRoleService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
@RestPermissionPrefix(group = "system", name = "role")
@RequestMapping("/system/role")
@EnableApi(ControllerApiGroup.FULL)
public class SysRoleController extends RestfulApiController<String, SysRole, SysRoleService> {

  private final SysPolicyService policyService;

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {new SortFilter(FieldConstants.UPDATED_TIME, false)};
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {SysRoleTableDef.SYS_ROLE.NAME.getName()};
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

  @GetMapping({"/{id}/policies"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> getRolePoliciesApi(@PathVariable String id) {
    SysRole role = this.getEntityService().viewResource(id);
    if (Objects.isNull(role)) {
      throw new NotFoundException();
    }
    List<SysPolicy> data = this.getEntityService().getRolePolicies(Collections.singletonList(id));
    return ResponseUtils.success(DetailBody.ok(data));
  }

  @PostMapping({"/{id}/policies"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> addRolePoliciesApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    List<SysPolicy> entities = this.policyService.viewResources(entitiesIds.getIds());
    boolean ret = this.getEntityService().addRolePolicies(id, entities);
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @PutMapping({"/{id}/policies"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> setRolePoliciesApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    List<SysPolicy> entities = this.policyService.viewResources(entitiesIds.getIds());
    boolean ret = this.getEntityService().setRolePolicies(id, entities);
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @DeleteMapping({"/{id}/policies"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> removeRolePoliciesApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    boolean ret =
        this.getEntityService().removeRolePolicies(id, new ArrayList<>(entitiesIds.getIds()));
    return ResponseUtils.success(DetailBody.ok(ret));
  }
}
