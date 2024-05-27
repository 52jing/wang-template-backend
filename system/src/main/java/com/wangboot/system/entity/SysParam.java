package com.wangboot.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IUniqueEntity;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.event.EnableOperationLog;
import com.wangboot.model.entity.impl.CommonEntity;
import com.wangboot.system.entity.table.SysParamTableDef;
import com.wangboot.system.listener.EntityChangeListener;
import com.wangboot.system.model.ParamType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.*;

/**
 * 实体类。
 *
 * @author wwtg99
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@EnableOperationLog
@Table(
    value = "wb_sys_param",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class SysParam extends CommonEntity implements IdEntity<String>, IUniqueEntity {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.name_not_blank")
  @Size(max = 100)
  private String name;

  @Size(max = 200)
  private String paramGroup = "";

  @NotBlank(message = "message.key_not_blank")
  @Size(max = 100)
  private String paramKey;

  private String paramVal;

  private ParamType paramType = ParamType.STR;

  public SysParam(
      String id,
      String name,
      String paramGroup,
      String paramKey,
      String paramVal,
      ParamType paramType,
      String remark) {
    this(id, name, paramGroup, paramKey, paramVal, paramType);
    this.setRemark(remark);
  }

  @Override
  @JsonIgnore
  public String[][] getUniqueTogetherFields() {
    return new String[][] {{SysParamTableDef.SYS_PARAM.PARAM_KEY.getName()}};
  }
}
