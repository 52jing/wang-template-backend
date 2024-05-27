package com.wangboot.app.render;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.system.entity.SysAttachment;
import com.wangboot.system.service.SysAttachmentService;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.util.PropertyPlaceholderHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class RenderUtils {

  private static final PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("${", "}", ":", false);

  private RenderUtils() {}

  /**
   * 获取占位符处理器
   * @return PropertyPlaceholderHelper
   */
  public static PropertyPlaceholderHelper getPropertyPlaceholder() {
    return propertyPlaceholderHelper;
  }

  /**
   * 载入模板附件
   * @param attachmentId 附件ID
   * @param attachmentService 附件服务
   * @param fileStorageService 存储服务
   * @return 附件字节数组
   * @throws IOException IO异常
   */
  public static byte[] loadTemplateAttachment(String attachmentId, SysAttachmentService attachmentService, FileStorageService fileStorageService) throws IOException {
    SysAttachment attachment = attachmentService.getDataById(attachmentId);
    if (Objects.isNull(attachment)) {
      throw new ErrorCodeException(ErrorCode.INVALID_TEMPLATE);
    }
    try (ByteArrayOutputStream bao = new ByteArrayOutputStream()) {
      fileStorageService
        .download(attachmentService.toFileInfo(attachment))
        .outputStream(bao);
      return bao.toByteArray();
    }
  }
}
