package com.wangboot.app.template.controller;

import com.wangboot.app.execution.render.TemplateRenderFactory;
import com.wangboot.app.template.entity.TplTemplate;
import com.wangboot.app.template.entity.table.TplTemplateTableDef;
import com.wangboot.app.template.service.TplTemplateService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RestPermissionPrefix(group = "template", name = "template")
@RequestMapping("/template/template")
@EnableApi(ControllerApiGroup.FULL)
public class TplTemplateController
    extends RestfulApiController<String, TplTemplate, TplTemplateService> {

  private final TemplateRenderFactory templateRenderFactory;

  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {new SortFilter(FieldConstants.UPDATED_TIME, false)};
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {TplTemplateTableDef.TPL_TEMPLATE.NAME.getName()};
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

  @GetMapping("/types")
  @RestPermissionAction(ApiResource.REST_PERMISSION_ACTION_VIEW)
  @NonNull
  public ResponseEntity<?> listTypes() {
    return ResponseUtils.success(DetailBody.ok(this.templateRenderFactory.getAllTypes()));
  }
}
