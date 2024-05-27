package com.wangboot.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.core.auth.frontend.IFrontendModel;
import com.wangboot.model.entity.IUniqueEntity;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.event.EnableOperationLog;
import com.wangboot.model.entity.impl.CommonEntity;
import com.wangboot.system.entity.table.SysFrontendTableDef;
import com.wangboot.system.listener.EntityChangeListener;
import com.wangboot.system.model.ClientType;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    value = "wb_sys_frontend",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class SysFrontend extends CommonEntity
    implements IdEntity<String>, IFrontendModel, IUniqueEntity {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.name_not_blank")
  @Size(max = 100)
  private String name;

  private String description = "";

  @Size(max = 100)
  private String author = "";

  @Size(max = 200)
  private String domain = "";

  @NotNull(message = "message.type_not_blank")
  private ClientType clientType;

  private Boolean allowRegister = false;

  private Boolean staffOnly = false;

  @Override
  public String getType() {
    return Optional.ofNullable(this.clientType).map(ClientType::getName).orElse("");
  }

  @Override
  public boolean staffOnly() {
    return this.staffOnly;
  }

  @Override
  public boolean allowRegister() {
    return allowRegister;
  }

  @JsonIgnore
  @Override
  public String[][] getUniqueTogetherFields() {
    return new String[][] {{SysFrontendTableDef.SYS_FRONTEND.NAME.getName()}};
  }
}
