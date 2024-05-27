package com.wangboot.task.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.impl.AppendOnlyEntity;
import com.wangboot.system.listener.EntityChangeListener;
import java.time.OffsetDateTime;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(
    value = "wb_task_inside_message",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class TaskInsideMessage extends AppendOnlyEntity implements IdEntity<String> {
  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @Size(max = 200)
  private String title = "";

  private String content = "";

  @Size(max = 100)
  private String type = "";

  private String fromUserId;

  @Size(max = 64)
  private String toUserId;

  private OffsetDateTime readTime;
}
