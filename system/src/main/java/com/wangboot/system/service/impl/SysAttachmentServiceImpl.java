package com.wangboot.system.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.system.entity.SysAttachment;
import com.wangboot.system.entity.table.SysAttachmentTableDef;
import com.wangboot.system.mapper.SysAttachmentMapper;
import com.wangboot.system.service.SysAttachmentService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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

  @Override
  public String getObjectTypeField() {
    return SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_TYPE.getName();
  }

  @Override
  public String getObjectIdField() {
    return SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_ID.getName();
  }

  @Override
  public <I extends Serializable> List<SysAttachment> getAttachmentsByObject(
      String objectType, I objectId) {
    QueryWrapper wrapper =
        this.getListQueryWrapper()
            .where(SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_TYPE.eq(objectType))
            .and(SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_ID.eq(objectId));
    return this.list(wrapper);
  }

  @Override
  public <I extends Serializable> boolean addAttachmentsRelations(
      String objectType, I objectId, Collection<String> attachmentIds) {
    if (Objects.nonNull(attachmentIds)
        && attachmentIds.size() > 0
        && StringUtils.hasText(objectType)) {
      return this.updateChain()
          .where(SysAttachmentTableDef.SYS_ATTACHMENT.ID.in(attachmentIds))
          .set(SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_TYPE, objectType)
          .set(SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_ID, objectId)
          .update();
    }
    return true;
  }

  @Override
  public <I extends Serializable> boolean setAttachmentsRelations(
      String objectType, I objectId, Collection<String> attachmentIds) {
    // 查找已有的附件
    QueryWrapper wrapper =
        this.getListQueryWrapper()
            .where(SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_TYPE.eq(objectType))
            .and(SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_ID.eq(objectId))
            .select(FieldConstants.PRIMARY_KEY);
    Set<String> existIds =
        this.list(wrapper).stream().map(IdEntity::getId).collect(Collectors.toSet());
    Set<String> needAdd =
        attachmentIds.stream().filter(aid -> !existIds.contains(aid)).collect(Collectors.toSet());
    Set<String> needDel =
        existIds.stream().filter(aid -> !attachmentIds.contains(aid)).collect(Collectors.toSet());
    if (needDel.size() > 0) {
      boolean ret = this.removeByIds(needDel);
      if (!ret) {
        return false;
      }
    }
    if (needAdd.size() > 0) {
      return this.addAttachmentsRelations(objectType, objectId, needAdd);
    }
    return true;
  }

  @Override
  public <I extends Serializable> boolean removeAttachmentsRelations(
      String objectType, I objectId) {
    if (StringUtils.hasText(objectType)) {
      QueryWrapper wrapper =
          this.query()
              .where(SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_TYPE.eq(objectType))
              .and(SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_ID.eq(objectId));
      return this.remove(wrapper);
    }
    return false;
  }

  @Override
  public <I extends Serializable> boolean batchRemoveAttachmentsRelations(
      String objectType, Collection<I> objectIds) {
    if (StringUtils.hasText(objectType) && Objects.nonNull(objectIds) && objectIds.size() > 0) {
      QueryWrapper wrapper =
          this.query()
              .where(SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_TYPE.eq(objectType))
              .and(SysAttachmentTableDef.SYS_ATTACHMENT.OBJECT_ID.in(objectIds));
      return this.remove(wrapper);
    }
    return false;
  }
}
