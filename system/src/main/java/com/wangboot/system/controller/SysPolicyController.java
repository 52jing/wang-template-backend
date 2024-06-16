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
import com.wangboot.system.entity.SysDataScope;
import com.wangboot.system.entity.SysMenu;
import com.wangboot.system.entity.SysPermission;
import com.wangboot.system.entity.SysPolicy;
import com.wangboot.system.entity.table.SysPolicyTableDef;
import com.wangboot.system.service.SysDataScopeService;
import com.wangboot.system.service.SysMenuService;
import com.wangboot.system.service.SysPermissionService;
import com.wangboot.system.service.SysPolicyService;
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
@RestPermissionPrefix(group = "system", name = "policy")
@RequestMapping("/system/policy")
@EnableApi(ControllerApiGroup.FULL)
public class SysPolicyController extends RestfulApiController<String, SysPolicy, SysPolicyService> {

  private final SysPermissionService permissionService;

  private final SysDataScopeService dataScopeService;

  private final SysMenuService menuService;

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {new SortFilter(FieldConstants.UPDATED_TIME, false)};
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {SysPolicyTableDef.SYS_POLICY.NAME.getName()};
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

  @GetMapping({"/{id}/permissions"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> getPolicyPermissionsApi(@PathVariable String id) {
    SysPolicy policy = this.getEntityService().viewResource(id);
    if (Objects.isNull(policy)) {
      throw new NotFoundException();
    }
    List<SysPermission> data =
        this.getEntityService().getPolicyPermissions(Collections.singletonList(id));
    return ResponseUtils.success(DetailBody.ok(data));
  }

  @PostMapping({"/{id}/permissions"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> addPolicyPermissionsApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    List<SysPermission> entities = this.permissionService.viewResources(entitiesIds.getIds());
    boolean ret = this.getEntityService().addPolicyPermissions(id, entities);
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @PutMapping({"/{id}/permissions"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> setPolicyPermissionsApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    List<SysPermission> entities = this.permissionService.viewResources(entitiesIds.getIds());
    boolean ret = this.getEntityService().setPolicyPermissions(id, entities);
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @DeleteMapping({"/{id}/permissions"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> removePolicyPermissionsApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    boolean ret =
        this.getEntityService().removePolicyPermissions(id, new ArrayList<>(entitiesIds.getIds()));
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @GetMapping({"/{id}/data_scopes"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> getPolicyDataScopesApi(@PathVariable String id) {
    SysPolicy policy = this.getEntityService().viewResource(id);
    if (Objects.isNull(policy)) {
      throw new NotFoundException();
    }
    List<SysDataScope> data =
        this.getEntityService().getPolicyDataScopes(Collections.singletonList(id));
    return ResponseUtils.success(DetailBody.ok(data));
  }

  @PostMapping({"/{id}/data_scopes"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> addPolicyDataScopesApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    List<SysDataScope> entities = this.dataScopeService.viewResources(entitiesIds.getIds());
    boolean ret = this.getEntityService().addPolicyDataScopes(id, entities);
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @PutMapping({"/{id}/data_scopes"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> setPolicyDataScopesApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    List<SysDataScope> entities = this.dataScopeService.viewResources(entitiesIds.getIds());
    boolean ret = this.getEntityService().setPolicyDataScopes(id, entities);
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @DeleteMapping({"/{id}/data_scopes"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> removePolicyDataScopesApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    boolean ret =
        this.getEntityService().removePolicyDataScopes(id, new ArrayList<>(entitiesIds.getIds()));
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @GetMapping({"/{id}/menus"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> getPolicyMenusApi(@PathVariable String id) {
    SysPolicy policy = this.getEntityService().viewResource(id);
    if (Objects.isNull(policy)) {
      throw new NotFoundException();
    }
    List<SysMenu> data = this.getEntityService().getPolicyMenus(Collections.singletonList(id));
    return ResponseUtils.success(DetailBody.ok(data));
  }

  @PostMapping({"/{id}/menus"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> addPolicyMenusApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    List<SysMenu> entities = this.menuService.viewResources(entitiesIds.getIds());
    boolean ret = this.getEntityService().addPolicyMenus(id, entities);
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @PutMapping({"/{id}/menus"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> setPolicyMenusApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    List<SysMenu> entities = this.menuService.viewResources(entitiesIds.getIds());
    boolean ret = this.getEntityService().setPolicyMenus(id, entities);
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  @DeleteMapping({"/{id}/menus"})
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> removePolicyMenusApi(
      @PathVariable String id, @RequestBody IdListBody<String> entitiesIds) {
    boolean ret =
        this.getEntityService().removePolicyMenus(id, new ArrayList<>(entitiesIds.getIds()));
    return ResponseUtils.success(DetailBody.ok(ret));
  }
}
