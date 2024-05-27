package com.wangboot.task.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.dataauthority.DataAuthority;
import com.wangboot.model.dataauthority.factory.UserIdAuthorizerFactory;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.impl.CommonEntity;
import com.wangboot.system.listener.EntityChangeListener;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DataAuthority(field = "created_by", factory = UserIdAuthorizerFactory.class)
@Table(
    value = "wb_task_quick_link",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class TaskQuickLink extends CommonEntity implements IdEntity<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.name_not_blank")
  @Size(max = 100)
  private String name;

  @Size(max = 200)
  private String icon = "";

  @Size(max = 200)
  private String path = "";

  private Integer sort = 0;
}
