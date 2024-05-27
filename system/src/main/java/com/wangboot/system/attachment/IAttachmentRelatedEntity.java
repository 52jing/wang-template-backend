package com.wangboot.system.attachment;

import com.wangboot.model.entity.IdEntity;
import java.io.Serializable;
import java.util.List;
import org.springframework.lang.Nullable;

/**
 * 关联附件的实体接口
 *
 * @param <I> 主键类型
 * @author wwtg99
 */
public interface IAttachmentRelatedEntity<I extends Serializable> extends IdEntity<I> {

  /** 获取附件列表 */
  @Nullable
  List<? extends IdEntity<String>> getAttachmentList();

  /** 设置附件列表 */
  void setAttachmentList(@Nullable List<? extends IdEntity<String>> attachments);
}
