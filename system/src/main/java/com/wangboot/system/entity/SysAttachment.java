package com.wangboot.system.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.attachment.IAttachmentModel;
import com.wangboot.model.entity.impl.AppendOnlyEntity;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = "wb_sys_attachment")
public class SysAttachment extends AppendOnlyEntity implements IAttachmentModel {
  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  private String url = "";

  private long size = 0;

  private String filename = "";

  private String originalFilename = "";

  private String basePath = "";

  private String path = "";

  private String ext = "";

  private String contentType = "";

  private String platform = "";

  private String thUrl = "";

  private String thFilename = "";

  private long thSize = 0;

  private String thContentType = "";

  private String hashInfo = "";

  private String attr = "";

  private String objectId;

  private String objectType = "";

  @Column(isLogicDelete = true)
  private Boolean deleted = false;
}
