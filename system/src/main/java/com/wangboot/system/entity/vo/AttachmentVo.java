package com.wangboot.system.entity.vo;

import com.wangboot.model.attachment.IAttachmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附件对外视图对象
 *
 * @author wwtg99
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentVo implements IAttachmentModel {
  private String id;
  private String url;
  private long size;
  private String originalFilename;
  private String contentType;
  private String thUrl;
  private long thSize;
  private String thContentType;
  private String hashInfo;
}
