package com.wangboot.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationManyToMany;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IEditableEntity;
import com.wangboot.model.entity.IUniqueEntity;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.event.EnableOperationLog;
import com.wangboot.model.entity.impl.CommonEntity;
import com.wangboot.system.entity.table.SysPolicyTableDef;
import com.wangboot.system.listener.EntityChangeListener;
import java.util.List;
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
@EqualsAndHashCode(
    callSuper = false,
    exclude = {"permissions", "datascopes", "menus"})
@NoArgsConstructor
@AllArgsConstructor
@EnableOperationLog
@Table(
    value = "wb_sys_policy",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class SysPolicy extends CommonEntity
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

  @RelationManyToMany(
      joinTable = "wb_sys_policy_permission_rel",
      joinSelfColumn = "policy_id",
      joinTargetColumn = "permission_id")
  private List<SysPermission> permissions;

  @RelationManyToMany(
      joinTable = "wb_sys_policy_data_scope_rel",
      joinSelfColumn = "policy_id",
      joinTargetColumn = "data_scope_id")
  private List<SysDataScope> datascopes;

  @RelationManyToMany(
      joinTable = "wb_sys_policy_menu_rel",
      joinSelfColumn = "policy_id",
      joinTargetColumn = "menu_id")
  private List<SysMenu> menus;

  @Override
  @JsonIgnore
  public String[][] getUniqueTogetherFields() {
    return new String[][] {{SysPolicyTableDef.SYS_POLICY.NAME.getName()}};
  }
}
