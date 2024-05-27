package com.wangboot.system.entity.relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IUniqueEntity;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.system.entity.relation.table.SysRolePolicyRelTableDef;
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
@Table(value = "wb_sys_role_policy_rel")
public class SysRolePolicyRel implements IdEntity<String>, IUniqueEntity {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.relation_not_empty")
  private String roleId;

  @NotBlank(message = "message.relation_not_empty")
  private String policyId;

  @Override
  @JsonIgnore
  public String[][] getUniqueTogetherFields() {
    return new String[][] {
      {
        SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL.ROLE_ID.getName(),
        SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL.POLICY_ID.getName()
      }
    };
  }
}
