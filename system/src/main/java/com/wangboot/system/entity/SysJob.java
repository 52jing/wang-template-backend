package com.wangboot.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.event.EnableOperationLog;
import com.wangboot.model.entity.impl.CommonEntity;
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
    value = "wb_sys_job",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class SysJob extends CommonEntity implements IdEntity<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.name_not_blank")
  @Size(max = 100)
  private String name;

  @Size(max = 100)
  private String type = "";
}
