package com.wangboot.app.template.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.impl.AppendOnlyEntity;
import com.wangboot.system.listener.EntityChangeListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(
  value = "wb_template_execution_result",
  onInsert = EntityChangeListener.class,
  onUpdate = EntityChangeListener.class)
public class TplExecutionResult extends AppendOnlyEntity implements IdEntity<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  private String executionId;

  private String message;

  private String attachmentId;

}
