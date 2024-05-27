package com.wangboot.system.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.SysAttachment;
import com.wangboot.system.entity.table.SysAttachmentTableDef;
import com.wangboot.system.mapper.SysAttachmentMapper;
import com.wangboot.system.service.SysAttachmentService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class SysAttachmentServiceImpl extends ServiceImpl<SysAttachmentMapper, SysAttachment>
    implements SysAttachmentService {

  private final ObjectMapper objectMapper;

  @Override
  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  @Override
  public boolean save(@Nullable FileInfo info) {
    if (Objects.isNull(info)) {
      return false;
    }
    try {
      SysAttachment detail = toAttachment(info);
      boolean b = createResource(detail);
      if (b) {
        info.setId(detail.getId());
      }
      return b;
    } catch (JsonProcessingException ignored) {
      return false;
    }
  }

  @Override
  public void update(@Nullable FileInfo info) {
    if (Objects.isNull(info)) {
      return;
    }
    try {
      SysAttachment attachment = toAttachment(info);
      QueryWrapper wrapper =
          this.query().where(SysAttachmentTableDef.SYS_ATTACHMENT.URL.eq(attachment.getUrl()));
      update(attachment, wrapper);
    } catch (JsonProcessingException ignored) {
    }
  }

  @Override
  @Nullable
  public FileInfo getByUrl(String s) {
    if (!StringUtils.hasText(s)) {
      return null;
    }
    try {
      return toFileInfo(getOne(this.query().where(SysAttachmentTableDef.SYS_ATTACHMENT.URL.eq(s))));
    } catch (JsonProcessingException ignored) {
      return null;
    }
  }

  @Override
  public boolean delete(String s) {
    if (!StringUtils.hasText(s)) {
      return false;
    }
    remove(this.query().where(SysAttachmentTableDef.SYS_ATTACHMENT.URL.eq(s)));
    return true;
  }

  @Override
  public void saveFilePart(FilePartInfo filePartInfo) {}

  @Override
  public void deleteFilePartByUploadId(String s) {}
}
