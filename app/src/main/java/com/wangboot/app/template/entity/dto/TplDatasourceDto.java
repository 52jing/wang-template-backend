package com.wangboot.app.template.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class TplDatasourceDto {

  private String id;
  @NotBlank(message = "message.name_not_blank")
  @Size(max = 100)
  private String name;
  @NotBlank(message = "message.type_not_blank")
  @Size(max = 100)
  private String type;
  private String config = "";
  private List<TplDatasourceParamDto> params;

  @Data
  public static class TplDatasourceParamDto {
    private String id;
    @NotBlank(message = "message.name_not_blank")
    @Size(max = 100)
    private String name;
    @Size(max = 100)
    private String label;
    private Boolean required = false;
    @Size(max = 500)
    private String defVal = "";
    private String config = "";
  }
}
