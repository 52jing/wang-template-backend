package com.wangboot.app.template.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wangboot.app.execution.datasource.DatasourceProcessor;
import com.wangboot.app.execution.datasource.IDatasource;
import com.wangboot.app.template.entity.TplDatasource;
import com.wangboot.app.template.entity.dto.TplDatasourceDto;
import com.wangboot.app.template.entity.table.TplDatasourceTableDef;
import com.wangboot.app.template.service.TplDatasourceService;
import com.wangboot.core.auth.annotation.RestPermissionAction;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.core.auth.authorization.resource.ApiResource;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.IRestfulService;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.IRestfulReadController;
import com.wangboot.model.entity.controller.IRestfulWriteController;
import com.wangboot.model.entity.exception.CreateFailedException;
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

  private final DatasourceProcessor datasourceProcessor;

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
  public ResponseEntity<?> createApi(@Validated @RequestBody TplDatasourceDto obj) {
    final TplDatasource entity = BeanUtil.copyProperties(obj, TplDatasource.class);
    this.create(entity);
    boolean ret = this.getEntityService().createDatasourceParams(entity, obj);
    if (!ret) {
      throw new CreateFailedException();
    }
    return ResponseUtils.created(DetailBody.created(obj));
  }

  @PutMapping("/{id}")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_UPDATE)
  @NonNull
  public ResponseEntity<?> updateApi(@PathVariable String id, @Validated @RequestBody TplDatasourceDto obj) {
    obj.setId(id);
    final TplDatasource entity = BeanUtil.copyProperties(obj, TplDatasource.class);
    this.update(entity);
    boolean ret = this.getEntityService().updateDatasourceParams(entity, obj);
    if (!ret) {
      throw new UpdateFailedException(id);
    }
    return ResponseUtils.success(DetailBody.updated(entity));
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

  @GetMapping("/types")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> listTypes() {
    return ResponseUtils.success(DetailBody.ok(this.datasourceProcessor.getTypes()));
  }

  @PostMapping("/{id}/retrieve")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> retrieveData(@PathVariable String id, @RequestBody Map<String, String> params) {
    IDatasource datasource = this.datasourceProcessor.getDatasource(id);
    if (Objects.isNull(datasource)) {
      throw new NotFoundException();
    }
    return ResponseUtils.success(DetailBody.ok(datasource.retrieveData(params)));
  }
}
