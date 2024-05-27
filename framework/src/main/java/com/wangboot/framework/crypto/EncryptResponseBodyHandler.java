package com.wangboot.framework.crypto;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wangboot.core.crypto.CryptoBody;
import com.wangboot.core.crypto.CryptoProcessor;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.web.crypto.ExcludeEncryption;
import com.wangboot.core.web.response.CryptoStatusBody;
import com.wangboot.core.web.response.IStatusBody;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.starter.autoconfiguration.WbProperties;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 响应体加密
 *
 * @author wwtg99
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class EncryptResponseBodyHandler implements ResponseBodyAdvice<IStatusBody> {

  private final WbProperties wbProperties;

  @Override
  public boolean supports(@NonNull MethodParameter returnType, @NonNull Class converterType) {
    // 排除解密注解
    if (returnType.hasMethodAnnotation(ExcludeEncryption.class)) {
      return false;
    }
    return this.wbProperties.getCrypto().isEnabled();
  }

  @Override
  @Nullable
  public IStatusBody beforeBodyWrite(
      @Nullable IStatusBody body,
      @NonNull MethodParameter returnType,
      @NonNull MediaType selectedContentType,
      @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
      @NonNull ServerHttpRequest request,
      @NonNull ServerHttpResponse response) {
    if (Objects.isNull(body)) {
      return null;
    }
    try {
      CryptoBody cryptoBody =
          this.getProcessor().encryptDataToBody(this.wbProperties.getCrypto().getMode(), body);
      if (Objects.isNull(cryptoBody)) {
        throw new ErrorCodeException(ErrorCode.CRYPTO_ERROR);
      }
      return new CryptoStatusBody(cryptoBody);
    } catch (JsonProcessingException e) {
      log.error("Process json failed for {}", e.getMessage());
      throw new ErrorCodeException(ErrorCode.CRYPTO_ERROR);
    }
  }

  private CryptoProcessor getProcessor() {
    return SpringUtil.getBean(CryptoProcessor.class);
  }
}
