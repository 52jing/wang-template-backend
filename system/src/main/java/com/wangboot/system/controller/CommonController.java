package com.wangboot.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import com.wangboot.core.auth.annotation.RequireAuthority;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.errorcode.HttpErrorCode;
import com.wangboot.core.web.crypto.ExcludeEncryption;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.response.ListBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.model.entity.exception.NotFoundException;
import com.wangboot.starter.autoconfiguration.WbProperties;
import com.wangboot.system.attachment.ContentTypes;
import com.wangboot.system.entity.SysAnnouncement;
import com.wangboot.system.entity.SysAttachment;
import com.wangboot.system.entity.SysFrontend;
import com.wangboot.system.entity.SysUserDict;
import com.wangboot.system.entity.vo.AttachmentVo;
import com.wangboot.system.entity.vo.FrontendVo;
import com.wangboot.system.service.SysAnnouncementService;
import com.wangboot.system.service.SysAttachmentService;
import com.wangboot.system.service.SysFrontendService;
import com.wangboot.system.service.SysUserDictService;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class CommonController {

  private final SysFrontendService frontendService;

  private final SysUserDictService userDictService;

  private final SysAnnouncementService announcementService;

  private final SysAttachmentService attachmentService;

  private final FileStorageService fileStorageService;

  private final WbProperties wbProperties;

  /** 定义上传文件允许的类型 */
  private static final Map<String, String[]> uploadAccepts = buildUploadAccepts();

  private static Map<String, String[]> buildUploadAccepts() {
    Map<String, String[]> map = new HashMap<>();
    map.put(
        "template", ArrayUtil.addAll(ContentTypes.TEXT_FILE, ContentTypes.OFFICE_WORD_DOCUMENTS));
    map.put(
        "announcement",
        ArrayUtil.addAll(
            ContentTypes.TEXT_FILE,
            ContentTypes.ZIP_ARCHIVES,
            ContentTypes.OFFICE_DOCUMENTS,
            ContentTypes.IMAGES));
    map.put("image", new String[] {"image/"});
    map.put("default", ArrayUtil.addAll(ContentTypes.TEXT_FILE, ContentTypes.ZIP_ARCHIVES));
    return map;
  }

  /** 找不到控制器的错误处理 */
  @GetMapping("/error")
  public ResponseEntity<?> errorHandle() {
    throw new NotFoundException();
  }

  /** 获取用户字典 */
  @GetMapping("/user_dict")
  public ResponseEntity<?> userDictByGroup(
      @RequestParam String group, @RequestParam(required = false) String code) {
    List<SysUserDict> userDict = userDictService.getByGroup(group, code);
    return ResponseUtils.success(DetailBody.ok(userDict));
  }

  /** 获取用户字典 */
  @GetMapping("/user_dict/{id}")
  public ResponseEntity<?> userDictById(@PathVariable String id) {
    SysUserDict userDict = userDictService.viewResource(id);
    if (Objects.isNull(userDict)) {
      throw new NotFoundException();
    }
    return ResponseUtils.success(DetailBody.ok(userDict));
  }

  /** 获取应用参数配置 */
  @GetMapping("/frontend/{fid}")
  public ResponseEntity<?> getFrontend(@PathVariable String fid) {
    SysFrontend frontend = this.frontendService.viewResource(fid);
    if (Objects.isNull(frontend)) {
      throw new ErrorCodeException(HttpErrorCode.BAD_REQUEST);
    }
    FrontendVo frontendVo = BeanUtil.toBean(frontend, FrontendVo.class);
    frontendVo.setTitle(frontend.getName());
    return ResponseUtils.success(DetailBody.ok(frontendVo));
  }

  /** 获取公告详情 */
  @GetMapping("/announcement/{id}")
  public ResponseEntity<?> getAnnouncement(@PathVariable String id) {
    SysAnnouncement data = this.announcementService.viewResource(id);
    return ResponseUtils.success(DetailBody.ok(data));
  }

  /** 获取某个类型的公告 */
  @GetMapping("/announcements")
  public ResponseEntity<?> getAnnouncements(@RequestParam String type) {
    List<SysAnnouncement> data = this.announcementService.getDisplayedAnnouncements(type);
    return ResponseUtils.success(ListBody.ok(data));
  }

  /** 上传文件接口 */
  @PostMapping("/upload")
  @RequireAuthority("common:attachment:upload")
  @ExcludeEncryption
  public ResponseEntity<?> upload(
      MultipartFile file, @RequestParam(required = false, defaultValue = "default") String type) {
    // 允许的上传格式
    String[] restrictContentTypes = uploadAccepts.get(type);
    if (Objects.isNull(restrictContentTypes) || Objects.isNull(file)) {
      throw new ErrorCodeException(HttpErrorCode.BAD_REQUEST);
    }
    // 限制上传大小
    if (this.wbProperties.getUploadLimits() > 0
        && file.getSize() > this.wbProperties.getUploadLimits()) {
      throw new ErrorCodeException(ErrorCode.FILE_EXCEED_MAX_SIZE);
    }
    if (Arrays.stream(restrictContentTypes)
        .anyMatch(
            t ->
                StringUtils.hasText(file.getContentType())
                    && file.getContentType().startsWith(t))) {
      FileInfo fileInfo =
          fileStorageService
              .of(file)
              .setPath(DateUtil.today() + "/")
              .setHashCalculatorMd5()
              .upload();
      if (Objects.nonNull(fileInfo)) {
        AttachmentVo uploadedFile = BeanUtil.copyProperties(fileInfo, AttachmentVo.class);
        return ResponseUtils.success(DetailBody.ok(uploadedFile));
      }
    }
    throw new ErrorCodeException(ErrorCode.NOT_ALLOWED_FORMAT);
  }

  /** 上传图片并生成缩略图接口 */
  @PostMapping("/upload_image")
  @RequireAuthority("common:attachment:upload")
  @ExcludeEncryption
  public ResponseEntity<?> uploadImage(MultipartFile file) {
    // 允许的上传格式
    String[] restrictContentTypes = uploadAccepts.get("image");
    if (Objects.isNull(file)) {
      throw new ErrorCodeException(HttpErrorCode.BAD_REQUEST);
    }
    // 限制上传大小
    if (this.wbProperties.getUploadLimits() > 0
        && file.getSize() > this.wbProperties.getUploadLimits()) {
      throw new ErrorCodeException(ErrorCode.FILE_EXCEED_MAX_SIZE);
    }
    if (Arrays.stream(restrictContentTypes)
        .anyMatch(
            t ->
                StringUtils.hasText(file.getContentType())
                    && file.getContentType().startsWith(t))) {
      FileInfo fileInfo =
          fileStorageService
              .of(file)
              .setPath(DateUtil.today() + "/")
              .setHashCalculatorMd5()
              .thumbnail(
                  th ->
                      th.size(
                          wbProperties.getUploadImageThumbSize(),
                          wbProperties.getUploadImageThumbSize()))
              .upload();
      if (Objects.nonNull(fileInfo)) {
        AttachmentVo uploadedFile = BeanUtil.copyProperties(fileInfo, AttachmentVo.class);
        return ResponseUtils.success(DetailBody.ok(uploadedFile));
      }
    }
    throw new ErrorCodeException(ErrorCode.UPLOAD_FAILED);
  }

  /** 获取附件信息接口 */
  @GetMapping("/attachment/{id}")
  @RequireAuthority("common:attachment:view")
  public ResponseEntity<?> getAttachment(@PathVariable String id) {
    SysAttachment attachment = attachmentService.viewResource(id);
    if (Objects.isNull(attachment)) {
      throw new NotFoundException();
    }
    AttachmentVo attachmentVo = BeanUtil.copyProperties(attachment, AttachmentVo.class);
    return ResponseUtils.success(DetailBody.ok(attachmentVo));
  }

  /** 下载附件接口 */
  @GetMapping("/download/{id}")
  @RequireAuthority("common:attachment:download")
  @ExcludeEncryption
  public void download(@PathVariable String id, HttpServletResponse response) {
    SysAttachment attachment = attachmentService.viewResource(id);
    if (Objects.isNull(attachment)) {
      throw new NotFoundException();
    }
    try {
      response.setHeader(
          "Content-Disposition",
          String.format(
              "attachment; filename=%s",
              URLEncoder.encode(attachment.getOriginalFilename(), "UTF-8")));
      fileStorageService
          .download(attachmentService.toFileInfo(attachment))
          .outputStream(response.getOutputStream());
    } catch (IOException ignored) {
      throw new ErrorCodeException(ErrorCode.EXPORT_FAILED);
    }
  }
}
