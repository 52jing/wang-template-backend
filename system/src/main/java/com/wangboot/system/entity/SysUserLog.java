package com.wangboot.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.core.auth.event.LogStatus;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.impl.AppendOnlyEntity;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = "wb_sys_user_log")
public class SysUserLog extends AppendOnlyEntity implements IdEntity<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  private String event;

  private LogStatus status;

  private String message;

  private String userId;

  private String username;

  private String frontendId;

  private String frontendName;

  private String ip;

  private String ua;
}
