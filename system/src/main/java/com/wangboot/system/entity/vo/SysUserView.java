package com.wangboot.system.entity.vo;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.RelationManyToMany;
import com.mybatisflex.annotation.Table;
import com.wangboot.system.entity.SysRole;
import com.wangboot.system.entity.SysUser;
import java.util.List;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "wb_sys_user_view")
public class SysUserView extends SysUser {

  private String sexVal;

  private String department;

  private String job;

  @Column(ignore = true)
  private String password;

  @RelationManyToMany(
      joinTable = "wb_sys_user_role_rel",
      joinSelfColumn = "user_id",
      joinTargetColumn = "role_id")
  private List<SysRole> roles;

  @Column(ignore = true)
  private List<String> authorities;
}
