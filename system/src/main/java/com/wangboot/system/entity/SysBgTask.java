package com.wangboot.system.entity;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.attachment.IAttachmentListRelatedModel;
import com.wangboot.model.attachment.IAttachmentModel;
import com.wangboot.model.dataauthority.DataAuthority;
import com.wangboot.model.dataauthority.factory.UserIdAuthorizerFactory;
import com.wangboot.model.entity.impl.CommonEntity;
import com.wangboot.system.entity.vo.AttachmentVo;
import com.wangboot.system.listener.EntityChangeListener;
import com.wangboot.system.model.BgTaskStatus;
import java.util.List;
import java.util.stream.Collectors;
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
public class SysBgTask extends CommonEntity implements IAttachmentListRelatedModel<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
  private String id;

  private String name;

  private String type;

  private BgTaskStatus status = BgTaskStatus.PENDING;

  private String result = "";

  @Column(ignore = true)
  private List<AttachmentVo> attachments;

  @JsonIgnore
  @Override
  public List<? extends IAttachmentModel> getAttachmentList() {
    return this.attachments;
  }

  @Override
  public void setAttachmentList(List<? extends IAttachmentModel> attachments) {
    this.attachments =
        attachments.stream()
            .map(d -> BeanUtil.copyProperties(d, AttachmentVo.class))
            .collect(Collectors.toList());
  }
}
