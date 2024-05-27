package com.wangboot.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.impl.AppendOnlyEntity;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = "wb_sys_operation_log")
public class SysOperationLog extends AppendOnlyEntity implements IdEntity<String> {
  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  private String event;

  private String resource;

  private String resourceId;

  private String obj;

  private String username;
}
