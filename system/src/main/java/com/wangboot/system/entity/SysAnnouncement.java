package com.wangboot.system.entity;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.event.EnableOperationLog;
import com.wangboot.model.entity.impl.CommonEntity;
import com.wangboot.system.attachment.IAttachmentRelatedEntity;
import com.wangboot.system.entity.vo.AttachmentVo;
import com.wangboot.system.listener.EntityChangeListener;
import com.wangboot.system.model.AnnouncementType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 实体类。
 *
 * @author wwtg99
 */
@Data
@EqualsAndHashCode(
    callSuper = false,
    exclude = {"attachments"})
@NoArgsConstructor
@AllArgsConstructor
@EnableOperationLog
@Table(
    value = "wb_sys_announcement",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class SysAnnouncement extends CommonEntity implements IAttachmentRelatedEntity<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @Size(min = 3, max = 200)
  private String title = "";

  private String content = "";

  private AnnouncementType type = AnnouncementType.NORMAL;

  private Boolean display = true;

  @Max(10000)
  @Min(-10000)
  private Integer sort = 0;

  private List<AttachmentVo> attachments;

  @JsonIgnore
  @Override
  public List<? extends IdEntity<String>> getAttachmentList() {
    return this.attachments;
  }

  @JsonIgnore
  @Override
  public void setAttachmentList(List<? extends IdEntity<String>> attachments) {
    if (Objects.nonNull(attachments)) {
      this.attachments =
          attachments.stream()
              .map(d -> BeanUtil.copyProperties(d, AttachmentVo.class, "hashInfo"))
              .collect(Collectors.toList());
    }
  }
}
