package com.wangboot.app.template.entity.dto;

import com.wangboot.app.template.entity.TplDatasourceParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TplDatasourceParamListDto {

  @NotNull
  private List<TplDatasourceParam> params;
}
