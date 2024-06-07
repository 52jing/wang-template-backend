package com.wangboot.app.template.entity;

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

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@EnableOperationLog
@Table(
    value = "wb_template_render_execution",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class TplRenderExecution extends CommonEntity implements IdEntity<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  private String templateId;

  private String templateName;

  private String templateType;

  private String datasourceId;

  private String datasourceName;

  private String datasourceType;

  private String params;

  private ExecutionStatus status = ExecutionStatus.WAITING;

  private String filename;
}
