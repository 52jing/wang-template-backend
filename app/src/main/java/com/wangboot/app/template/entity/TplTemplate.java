package com.wangboot.app.template.entity;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.attachment.IAttachmentListRelatedModel;
import com.wangboot.model.attachment.IAttachmentModel;
import com.wangboot.model.entity.event.EnableOperationLog;
import com.wangboot.model.entity.impl.CommonEntity;
import com.wangboot.system.entity.vo.AttachmentVo;
import com.wangboot.system.listener.EntityChangeListener;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    value = "wb_template_template",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class TplTemplate extends CommonEntity implements IAttachmentListRelatedModel<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.name_not_blank")
  @Size(max = 100)
  private String name;

  @NotBlank(message = "message.type_not_blank")
  @Size(max = 100)
  private String type;

  @Size(max = 200)
  private String defFilename = "";

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
