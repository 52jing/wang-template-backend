package com.wangboot.app.template.entity.dto;

import com.wangboot.app.template.entity.TplDatasourceParam;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TplDatasourceParamListDto {

  @NotNull private List<TplDatasourceParam> params;
}
