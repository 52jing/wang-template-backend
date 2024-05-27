package com.wangboot.system.entity.relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IUniqueEntity;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.system.entity.relation.table.SysPolicyMenuRelTableDef;
import javax.validation.constraints.NotBlank;
import lombok.*;

/**
 * 实体类。
 *
 * @author wwtg99
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = "wb_sys_policy_menu_rel")
public class SysPolicyMenuRel implements IdEntity<String>, IUniqueEntity {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.relation_not_empty")
  private String policyId;

  @NotBlank(message = "message.relation_not_empty")
  private String menuId;

  @Override
  @JsonIgnore
  public String[][] getUniqueTogetherFields() {
    return new String[][] {
      {
        SysPolicyMenuRelTableDef.SYS_POLICY_MENU_REL.POLICY_ID.getName(),
        SysPolicyMenuRelTableDef.SYS_POLICY_MENU_REL.MENU_ID.getName()
      }
    };
  }
}
