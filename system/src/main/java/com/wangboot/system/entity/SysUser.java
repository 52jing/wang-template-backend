package com.wangboot.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.core.auth.user.IUserModel;
import com.wangboot.framework.validator.Telephone;
import com.wangboot.model.entity.IUniqueEntity;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.event.EnableOperationLog;
import com.wangboot.model.entity.impl.CommonEntity;
import com.wangboot.system.entity.table.SysUserTableDef;
import com.wangboot.system.listener.EntityChangeListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.*;

/**
 * 实体类。
 *
 * @author wwtg99
 */
@Data
@EqualsAndHashCode(
    callSuper = false,
    exclude = {"password", "needChangePwd"})
@NoArgsConstructor
@AllArgsConstructor
@EnableOperationLog
@Table(
    value = "wb_sys_user",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class SysUser extends CommonEntity implements IdEntity<String>, IUniqueEntity, IUserModel {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.username_not_blank")
  @Size(min = 3, max = 100)
  private String username;

  @Size(min = 3, max = 100)
  private String nickname = "";

  @JsonIgnore private String password = "";

  @Email(message = "message.invalid_email")
  @Size(max = 100)
  private String email = "";

  @Telephone
  @Size(max = 20)
  private String tel = "";

  private Boolean active = true;

  private Boolean superuser = false;

  private Boolean staff = false;

  private OffsetDateTime expiredTime;

  @Size(max = 64)
  private String sex;

  @Size(max = 64)
  private String departmentId;

  @Size(max = 64)
  private String jobId;

  @Size(max = 200)
  private String province = "";

  @Size(max = 200)
  private String city = "";

  @Size(max = 200)
  private String area = "";

  @Size(max = 200)
  private String town = "";

  private String address = "";

  private Boolean needChangePwd = false;

  @Override
  public String getUserId() {
    return this.getId();
  }

  @Override
  public boolean checkSuperuser() {
    return this.superuser;
  }

  @Override
  public boolean checkStaff() {
    return this.staff;
  }

  @Override
  public boolean checkEnabled() {
    return this.active;
  }

  @Override
  public boolean checkLocked() {
    return false;
  }

  @JsonIgnore
  @Override
  public String[][] getUniqueTogetherFields() {
    return new String[][] {{SysUserTableDef.SYS_USER.USERNAME.getName()}};
  }
}
