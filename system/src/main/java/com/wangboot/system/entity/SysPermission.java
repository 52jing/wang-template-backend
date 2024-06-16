package com.wangboot.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IEditableEntity;
import com.wangboot.model.entity.IUniqueEntity;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.event.EnableOperationLog;
import com.wangboot.model.entity.impl.CommonEntity;
import com.wangboot.system.entity.table.SysPermissionTableDef;
import com.wangboot.system.listener.EntityChangeListener;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    value = "wb_sys_permission",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class SysPermission extends CommonEntity
    implements IdEntity<String>, IEditableEntity, IUniqueEntity {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.name_not_blank")
  @Size(max = 90)
  private String name;

  @NotBlank(message = "message.label_not_blank")
  @Size(max = 90)
  private String label;

  private Boolean readonly = false;

  @Override
  public boolean readonly() {
    return this.readonly;
  }

  @Override
  @JsonIgnore
  public String[][] getUniqueTogetherFields() {
    return new String[][] {{SysPermissionTableDef.SYS_PERMISSION.NAME.getName()}};
  }
}
