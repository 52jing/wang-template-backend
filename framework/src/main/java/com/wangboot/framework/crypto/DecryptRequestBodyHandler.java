package com.wangboot.framework.crypto;

import cn.hutool.extra.spring.SpringUtil;
import com.wangboot.core.crypto.CryptoProcessor;
import com.wangboot.core.web.crypto.ExcludeEncryption;
import com.wangboot.starter.autoconfiguration.WbProperties;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

/**
 * 请求体解密
 *
 * @author wwtg99
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class DecryptRequestBodyHandler extends RequestBodyAdviceAdapter {

  private final WbProperties wbProperties;

  @Override
  public boolean supports(
      @NonNull MethodParameter methodParameter,
      @NonNull Type targetType,
      @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
    // 排除解密注解
    if (methodParameter.hasMethodAnnotation(ExcludeEncryption.class)) {
      return false;
    }
    return wbProperties.getCrypto().isEnabled();
  }

  @NonNull
  @Override
  public HttpInputMessage beforeBodyRead(
      @NonNull HttpInputMessage inputMessage,
      @NonNull MethodParameter parameter,
      @NonNull Type targetType,
      @NonNull Class<? extends HttpMessageConverter<?>> converterType)
      throws IOException {
    if (inputMessage.getBody().available() <= 0) {
      return inputMessage;
    }
    // 获取原请求体
    byte[] bytes = new byte[inputMessage.getBody().available()];
    int count = inputMessage.getBody().read(bytes);
    if (count <= 0) {
      return inputMessage;
    }
    // 解密数据
    byte[] nbytes = this.getProcessor().decryptDataFromBytes(bytes);
    // 使用解密后的数据，构造新的读取流
    InputStream inputStream = new ByteArrayInputStream(nbytes);
    return new HttpInputMessage() {
      @NonNull
      @Override
      @Generated
      public InputStream getBody() throws IOException {
        return inputStream;
      }

      @NonNull
      @Override
      @Generated
      public HttpHeaders getHeaders() {
        return inputMessage.getHeaders();
      }
    };
  }

  private CryptoProcessor getProcessor() {
    return SpringUtil.getBean(CryptoProcessor.class);
  }
}
