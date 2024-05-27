package com.wangboot.app.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.event.EnableOperationLog;
import com.wangboot.model.entity.impl.CommonEntity;
import com.wangboot.system.listener.EntityChangeListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@EnableOperationLog
@Table(
  value = "wb_template_datasource",
  onInsert = EntityChangeListener.class,
  onUpdate = EntityChangeListener.class)
public class Datasource extends CommonEntity implements IdEntity<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.name_not_blank")
  @Size(max = 100)
  private String name;

  private String type;

  private String config = "";

}
