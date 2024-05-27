package com.wangboot.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.event.EnableOperationLog;
import com.wangboot.model.entity.impl.CommonTreeEntity;
import com.wangboot.system.listener.EntityChangeListener;
import javax.validation.constraints.NotBlank;
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
    value = "wb_sys_department",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class SysDepartment extends CommonTreeEntity<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.name_not_blank")
  @Size(max = 100)
  private String name;

  @Size(max = 200)
  private String fullname = "";

  @Size(max = 200)
  private String leader = "";

  @Override
  public boolean hasChildren() {
    return true;
  }
}
