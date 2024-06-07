package com.wangboot.app.template.entity;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.attachment.IAttachmentListRelatedModel;
import com.wangboot.model.attachment.IAttachmentModel;
import com.wangboot.model.entity.impl.AppendOnlyEntity;
import com.wangboot.system.entity.vo.AttachmentVo;
import com.wangboot.system.listener.EntityChangeListener;
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
@Table(
    value = "wb_template_execution_result",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class TplExecutionResult extends AppendOnlyEntity
    implements IAttachmentListRelatedModel<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  private String executionId;

  private String message = "";

  private ExecutionStatus status;

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
