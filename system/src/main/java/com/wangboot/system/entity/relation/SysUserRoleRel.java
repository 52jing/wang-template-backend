package com.wangboot.system.entity.relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IUniqueEntity;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.system.entity.relation.table.SysUserRoleRelTableDef;
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
@Table(value = "wb_sys_user_role_rel")
public class SysUserRoleRel implements IdEntity<String>, IUniqueEntity {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.relation_not_empty")
  private String userId;

  @NotBlank(message = "message.relation_not_empty")
  private String roleId;

  @Override
  @JsonIgnore
  public String[][] getUniqueTogetherFields() {
    return new String[][] {
      {
        SysUserRoleRelTableDef.SYS_USER_ROLE_REL.USER_ID.getName(),
        SysUserRoleRelTableDef.SYS_USER_ROLE_REL.ROLE_ID.getName()
      }
    };
  }
}
