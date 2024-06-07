package com.wangboot.app.template.controller;

import com.wangboot.app.execution.ExecutionManager;
import com.wangboot.app.execution.datasource.DatasourceParamHolder;
import com.wangboot.app.template.entity.TplRenderExecution;
import com.wangboot.app.template.entity.dto.TplRenderExecutionDto;
import com.wangboot.app.template.entity.table.TplRenderExecutionTableDef;
import com.wangboot.app.template.service.TplRenderExecutionService;
import com.wangboot.core.auth.annotation.RestPermissionAction;
import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.core.auth.authorization.resource.ApiResource;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiController;
import com.wangboot.model.entity.request.ParamFilterDefinition;
import com.wangboot.model.entity.request.SearchStrategy;
import com.wangboot.model.entity.request.SortFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RestPermissionPrefix(group = "template", name = "render_execution")
@RequestMapping("/template/render_execution")
@EnableApi(ControllerApiGroup.READ_ONLY)
public class TplRenderExecutionController
    extends RestfulApiController<String, TplRenderExecution, TplRenderExecutionService> {

  private final ExecutionManager executionManager;

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {new SortFilter(FieldConstants.UPDATED_TIME, false)};
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {
      TplRenderExecutionTableDef.TPL_RENDER_EXECUTION.DATASOURCE_NAME.getName(),
      TplRenderExecutionTableDef.TPL_RENDER_EXECUTION.TEMPLATE_NAME.getName(),
      TplRenderExecutionTableDef.TPL_RENDER_EXECUTION.FILENAME.getName(),
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
        .addFilter("templateId")
        .addFilter("datasourceId")
        .addFilter("status")
        .addFilter("templateType")
        .addFilter("datasourceType");
  }

  @PostMapping("/start")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_CREATE)
  @NonNull
  public ResponseEntity<?> startApi(@Validated @RequestBody TplRenderExecutionDto obj) {
    TplRenderExecution execution =
        this.executionManager.startRenderExecution(
            obj.getDatasourceId(),
            obj.getTemplateId(),
            obj.getFilename(),
            new DatasourceParamHolder(obj.getParams()));
    return ResponseUtils.created(DetailBody.created(execution));
  }

  @GetMapping("/{id}/results")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> listResults(@PathVariable String id) {
    return ResponseUtils.success(DetailBody.ok(this.getEntityService().getResults(id)));
  }
}
