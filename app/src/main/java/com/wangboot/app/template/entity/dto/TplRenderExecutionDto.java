package com.wangboot.app.template.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class TplRenderExecutionDto {

  @NotBlank
  private String datasourceId;

  @NotBlank
  private String templateId;

  private String filename = "";

  @NotNull
  private Map<String, String> params;
}
