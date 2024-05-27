package com.wangboot.system.entity.vo;

import com.wangboot.model.entity.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.x.file.storage.core.hash.HashInfo;

/**
 * 附件对外视图对象
 *
 * @author wwtg99
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentVo implements IdEntity<String> {
  private String id;
  private String url;
  private long size;
  private String originalFilename;
  private String contentType;
  private String thUrl;
  private long thSize;
  private String thContentType;
  private HashInfo hashInfo;
}
