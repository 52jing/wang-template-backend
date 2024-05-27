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
import com.wangboot.system.entity.table.SysUserDictTableDef;
import com.wangboot.system.listener.EntityChangeListener;
import com.wangboot.system.model.ParamType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    value = "wb_sys_user_dict",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class SysUserDict extends CommonEntity implements IdEntity<String>, IUniqueEntity {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.name_not_blank")
  @Size(max = 100)
  private String name;

  @NotBlank(message = "message.group_not_blank")
  @Size(max = 100)
  private String dictGroup;

  @NotBlank(message = "message.code_not_blank")
  @Size(max = 100)
  private String dictCode;

  private String dictVal;

  private ParamType dictType = ParamType.STR;

  @Max(10000)
  @Min(-10000)
  private Integer sort = 0;

  @Override
  @JsonIgnore
  public String[][] getUniqueTogetherFields() {
    return new String[][] {
      {
        SysUserDictTableDef.SYS_USER_DICT.DICT_GROUP.getName(),
        SysUserDictTableDef.SYS_USER_DICT.DICT_CODE.getName()
      }
    };
  }
}
