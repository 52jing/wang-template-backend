package com.wangboot.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Dict;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangboot.core.auth.utils.AuthUtils;
import com.wangboot.model.flex.IFlexRestfulService;
import com.wangboot.system.entity.SysAttachment;
import com.wangboot.system.entity.vo.AttachmentVo;
import java.time.OffsetDateTime;
import java.util.Objects;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.hash.HashInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

public interface SysAttachmentService
    extends IFlexRestfulService<String, SysAttachment>, FileRecorder {

  ObjectMapper getObjectMapper();

  /** 将 FileInfo 转为 SysAttachment */
  @NonNull
  default SysAttachment toAttachment(@NonNull FileInfo info) throws JsonProcessingException {
    SysAttachment attachment = new SysAttachment();
    BeanUtil.copyProperties(
        info,
        attachment,
        CopyOptions.create(
            SysAttachment.class,
            true,
            "objectId",
            "objectType",
            "metadata",
            "userMetadata",
            "thMetadata",
            "thUserMetadata",
            "attr",
            "hashInfo",
            "uploadId",
            "uploadStatus",
            "createTime"));
    // 这里手动获 取附加属性字典并转成 json 字符串，方便存储在数据库中
    attachment.setAttr(valueToJson(getObjectMapper(), info.getAttr()));
    // 这里手动获 哈希信息并转成 json 字符串，方便存储在数据库中
    attachment.setHashInfo(valueToJson(getObjectMapper(), info.getHashInfo()));
    attachment.setCreatedTime(OffsetDateTime.now());
    attachment.setCreatedBy(AuthUtils.getUserId());
    return attachment;
  }

  /** 将 SysAttachment 转为 FileInfo */
  @NonNull
  default FileInfo toFileInfo(@NonNull SysAttachment attachment) throws JsonProcessingException {
    FileInfo info =
        BeanUtil.copyProperties(
            attachment,
            FileInfo.class,
            "metadata",
            "userMetadata",
            "thMetadata",
            "thUserMetadata",
            "attr",
            "hashInfo");
    // 这里手动获取数据库中的 json 字符串 并转成 附加属性字典，方便使用
    info.setAttr(jsonToDict(getObjectMapper(), attachment.getAttr()));
    // 这里手动获取数据库中的 json 字符串 并转成 哈希信息，方便使用
    info.setHashInfo(jsonToHashInfo(getObjectMapper(), attachment.getHashInfo()));
    return info;
  }

  /** 将 SysAttachment 转为 AttachmentVo */
  @NonNull
  default AttachmentVo toAttachmentVo(@NonNull SysAttachment attachment)
      throws JsonProcessingException {
    AttachmentVo attachmentVo = BeanUtil.copyProperties(attachment, AttachmentVo.class, "hashInfo");
    // 这里手动获取数据库中的 json 字符串 并转成 哈希信息，方便使用
    attachmentVo.setHashInfo(jsonToHashInfo(getObjectMapper(), attachment.getHashInfo()));
    return attachmentVo;
  }

  /** 将指定值转换成 json 字符串 */
  static String valueToJson(ObjectMapper objectMapper, Object value)
      throws JsonProcessingException {
    if (Objects.isNull(value)) return "";
    return objectMapper.writeValueAsString(value);
  }

  /** 将 json 字符串转换成字典对象 */
  static Dict jsonToDict(ObjectMapper objectMapper, String json) throws JsonProcessingException {
    if (!StringUtils.hasText(json)) return null;
    return objectMapper.readValue(json, Dict.class);
  }

  /** 将 json 字符串转换成哈希信息对象 */
  static HashInfo jsonToHashInfo(ObjectMapper objectMapper, String json)
      throws JsonProcessingException {
    if (!StringUtils.hasText(json)) return null;
    return objectMapper.readValue(json, HashInfo.class);
  }
}
