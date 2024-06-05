package com.wangboot.app.template.controller;

import com.wangboot.app.execution.datasource.IDatasource;
import com.wangboot.app.template.entity.TplDatasource;
import com.wangboot.app.template.entity.dto.TplDatasourceParamListDto;
import com.wangboot.app.template.entity.table.TplDatasourceTableDef;
import com.wangboot.app.template.service.TplDatasourceService;
import com.wangboot.core.auth.annotation.RestPermissionAction;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.core.auth.authorization.resource.ApiResource;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.IRestfulService;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.IRestfulReadController;
import com.wangboot.model.entity.controller.IRestfulWriteController;
import com.wangboot.model.entity.exception.NotFoundException;
import com.wangboot.model.entity.exception.UpdateFailedException;
import com.wangboot.model.entity.request.ParamFilterDefinition;
import com.wangboot.model.entity.request.SearchStrategy;
import com.wangboot.model.entity.request.SortFilter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RestPermissionPrefix(group = "template", name = "datasource")
@RequestMapping("/template/datasource")
@EnableApi(ControllerApiGroup.READ_ONLY)
public class TplDatasourceController implements IRestfulReadController<String, TplDatasource>, IRestfulWriteController<String, TplDatasource> {

  @Getter
  @Setter
  private ApplicationEventPublisher applicationEventPublisher;

  @Getter private final TplDatasourceService entityService;

  @NonNull
  @Override
  public IRestfulService<String, TplDatasource> getReadService() {
    return this.entityService;
  }

  @Override
  @NonNull
  public IRestfulService<String, TplDatasource> getWriteService() {
    return this.entityService;
  }

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {new SortFilter(FieldConstants.UPDATED_TIME, false)};
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {TplDatasourceTableDef.TPL_DATASOURCE.NAME.getName()};
  }

  @NonNull
  @Override
  public SearchStrategy configSearchStrategy() {
    return SearchStrategy.BOTH_LIKE;
  }

  @Override
  public ParamFilterDefinition configParamFilterDefinition() {
    return ParamFilterDefinition.newInstance().addFilter("name").addFilter("type");
  }

  @GetMapping
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> listApi() {
    return this.listPageResponse();
  }

  @GetMapping("/{id}")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> detailApi(@PathVariable String id) {
    return this.viewResponse(id);
  }

  @PostMapping
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_CREATE)
  @NonNull
  public ResponseEntity<?> createApi(@Validated @RequestBody TplDatasource obj) {
    return this.createResponse(obj);
  }

  @PutMapping("/{id}")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> updateApi(@PathVariable String id, @Validated @RequestBody TplDatasource obj) {
    obj.setId(id);
    return this.updateResponse(obj);
  }

  @DeleteMapping("/{id}")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_DELETE)
  @NonNull
  public ResponseEntity<?> removeApi(@PathVariable String id) {
    this.getEntityService().deleteDatasourceParams(id);
    return this.deleteByIdResponse(id);
  }

  @GetMapping("/{id}/params")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> listParams(@PathVariable String id) {
    return ResponseUtils.success(DetailBody.ok(this.getEntityService().getDatasourceParams(id)));
  }

  @PostMapping("/{id}/params")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> addParams(@PathVariable String id, @Validated @RequestBody TplDatasourceParamListDto dto) {
    boolean ret = this.getEntityService().createDatasourceParams(id, dto.getParams());
    if (!ret) {
      throw new UpdateFailedException(id);
    }
    return ResponseUtils.success(DetailBody.ok(true));
  }

  @PutMapping("/{id}/params")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> updateParams(@PathVariable String id, @Validated @RequestBody TplDatasourceParamListDto dto) {
    boolean ret = this.getEntityService().updateDatasourceParams(id, dto.getParams());
    if (!ret) {
      throw new UpdateFailedException(id);
    }
    return ResponseUtils.success(DetailBody.ok(true));
  }

  @DeleteMapping("/{id}/params/{pid}")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> deleteParams(@PathVariable String id, @PathVariable String pid) {
    boolean ret = this.getEntityService().deleteDatasourceParams(pid);
    if (!ret) {
      throw new UpdateFailedException(id);
    }
    return ResponseUtils.success(DetailBody.ok(true));
  }

  @PostMapping("/{id}/connect")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> connectDatasource(@PathVariable String id) {
    TplDatasource datasource = this.getEntityService().viewResource(id);
    if (Objects.isNull(datasource)) {
      throw new NotFoundException();
    }
    IDatasource ds = this.getEntityService().connectDatasource(datasource);
    if (Objects.isNull(ds)) {
      throw new ErrorCodeException(ErrorCode.CONNECT_DATASOURCE_FAILED);
    }
    return ResponseUtils.success(DetailBody.ok(true));
  }

  @PostMapping("/{id}/retrieve")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> retrieveData(@PathVariable String id, @RequestBody Map<String, String> params) {
    TplDatasource datasource = this.getEntityService().viewResource(id);
    if (Objects.isNull(datasource)) {
      throw new NotFoundException();
    }
    IDatasource ds = this.getEntityService().connectDatasource(datasource);
    if (Objects.isNull(ds)) {
      throw new ErrorCodeException(ErrorCode.CONNECT_DATASOURCE_FAILED);
    }
    return ResponseUtils.success(DetailBody.ok(ds.retrieveData(params)));
  }

  @GetMapping("/types")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> listTypes() {
    return ResponseUtils.success(DetailBody.ok(this.getEntityService().getDatasourceTypes()));
  }

}
