package com.wangboot.app.controller;

import com.wangboot.app.analysis.AnalysisManager;
import com.wangboot.app.analysis.config.AnalysisProperties;
import com.wangboot.app.analysis.exception.MoonShotRequestException;
import com.wangboot.app.execution.RenderErrorCode;
import com.wangboot.core.auth.annotation.RequireAuthority;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.errorcode.HttpErrorCode;
import com.wangboot.core.web.crypto.ExcludeEncryption;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.starter.autoconfiguration.WbProperties;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analysis")
public class ReportAnalysisController {

  private final WbProperties wbProperties;
  private final AnalysisProperties analysisProperties;
  private final AnalysisManager analysisManager;

  @PostMapping("/indicator")
  @RequireAuthority("template:analysis:upload")
  @ExcludeEncryption
  public ResponseEntity<?> indicatorAnalysis(MultipartFile file) {
    if (!analysisProperties.isIndicatorAnalysis()) {
      throw new ErrorCodeException(RenderErrorCode.ANALYSIS_NOT_ENABLED);
    }
    // 允许的上传格式
    String[] restrictContentTypes =
        new String[] {
          "application/pdf",
          "image/",
          "application/msword",
          "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        };
    if (this.wbProperties.getUploadLimits() > 0
        && file.getSize() > this.wbProperties.getUploadLimits()) {
      throw new ErrorCodeException(ErrorCode.FILE_EXCEED_MAX_SIZE);
    }
    if (Arrays.stream(restrictContentTypes)
        .anyMatch(
            t ->
                StringUtils.hasText(file.getContentType())
                    && file.getContentType().startsWith(t))) {
      try {
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        if (file.getContentType()
            .equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
          contentType = "application/msword";
        }
        return ResponseUtils.success(
            DetailBody.ok(analysisManager.analysisIndicators(bytes, filename, contentType)));
      } catch (IOException e) {
        throw new ErrorCodeException(HttpErrorCode.BAD_REQUEST);
      } catch (MoonShotRequestException e) {
        throw new ErrorCodeException(RenderErrorCode.ANALYSIS_FAILED);
      }
    }
    throw new ErrorCodeException(ErrorCode.NOT_ALLOWED_FORMAT);
  }
}
