package com.wangboot.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.dataauthority.DataAuthority;
import com.wangboot.model.dataauthority.factory.UserIdAuthorizerFactory;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.impl.CommonEntity;
import com.wangboot.system.listener.EntityChangeListener;
import com.wangboot.system.model.BgTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@DataAuthority(field = "created_by", factory = UserIdAuthorizerFactory.class)
@Table(
    value = "wb_sys_bg_task",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class SysBgTask extends CommonEntity implements IdEntity<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
  private String id;

  private String name;

  private String type;

  private BgTaskStatus status = BgTaskStatus.PENDING;

  private String attachmentId;

  private String result = "";
}
