package com.wangboot.app.analysis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.wangboot.app.analysis.exception.MoonShotRequestException;
import com.wangboot.app.analysis.model.*;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

@Slf4j
public class MoonShotClient {

  public static final String MOONSHOT_BASE = "https://api.moonshot.cn/v1";
  private static final String JSON_MEDIA_TYPE = "application/json;charset=utf-8";
  private static final String FORM_MEDIA_TYPE = "multipart/form-data";

  private final OkHttpClient httpClient;
  private final String baseUrl;
  private final String accessToken;
  private final ObjectMapper objectMapper;

  public MoonShotClient(String baseUrl, String accessToken, OkHttpClient httpClient) {
    this.baseUrl = baseUrl;
    this.accessToken = accessToken;
    if (Objects.isNull(httpClient)) {
      this.httpClient =
          new OkHttpClient.Builder()
              .connectTimeout(30, TimeUnit.SECONDS)
              .readTimeout(30, TimeUnit.SECONDS)
              .callTimeout(30, TimeUnit.SECONDS)
              .build();
    } else {
      this.httpClient = httpClient;
    }
    this.objectMapper = new ObjectMapper();
    this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
  }

  public MoonShotClient(String accessToken, OkHttpClient httpClient) {
    this(MOONSHOT_BASE, accessToken, httpClient);
  }

  public MoonShotClient(String accessToken) {
    this(accessToken, null);
  }

  @Nullable
  public FileUploadResponseBody uploadFile(byte[] bytes, String filename, String contentType)
      throws IOException {
    if (Objects.isNull(bytes) || !StringUtils.hasText(filename)) {
      return null;
    }
    RequestBody fileBody = RequestBody.create(bytes, MediaType.parse(contentType));
    MultipartBody requestBody =
        new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", filename, fileBody)
            .addFormDataPart("purpose", "file-extract")
            .build();
    Request request =
        new Request.Builder()
            .url(this.baseUrl + "/files")
            .addHeader("Authorization", "Bearer " + this.accessToken)
            .header("Content-Type", FORM_MEDIA_TYPE)
            .post(requestBody)
            .build();
    final Call call = this.httpClient.newCall(request);
    Response response = call.execute();
    if (response.isSuccessful()) {
      ResponseBody body = response.body();
      if (Objects.nonNull(body)) {
        String content = body.string();
        log.info("===> Response content: {}", content);
        return this.objectMapper.readValue(content, FileUploadResponseBody.class);
      }
    } else {
      ResponseBody body = response.body();
      if (Objects.nonNull(body)) {
        String content = body.string();
        log.warn("===> Response content: {}", content);
        throw new MoonShotRequestException(content);
      }
    }
    return null;
  }

  @Nullable
  public FileContentResponseBody getFileContent(String fileId) throws IOException {
    if (!StringUtils.hasText(fileId)) {
      return null;
    }
    Request request =
        new Request.Builder()
            .url(this.baseUrl + "/files/" + fileId + "/content")
            .addHeader("Authorization", "Bearer " + this.accessToken)
            .get()
            .build();
    final Call call = this.httpClient.newCall(request);
    Response response = call.execute();
    if (response.isSuccessful()) {
      ResponseBody body = response.body();
      if (Objects.nonNull(body)) {
        String content = body.string();
        log.info("===> Response content: {}", content);
        return this.objectMapper.readValue(content, FileContentResponseBody.class);
      }
    } else {
      ResponseBody body = response.body();
      if (Objects.nonNull(body)) {
        String content = body.string();
        log.warn("===> Response content: {}", content);
        throw new MoonShotRequestException(content);
      }
    }
    return null;
  }

  @Nullable
  public ChatCompletionsResponseBody chatCompletions(@Nullable ChatCompletionsRequestBody body)
      throws IOException {
    if (Objects.isNull(body)) {
      return null;
    }
    RequestBody requestBody =
        RequestBody.create(
            this.objectMapper.writeValueAsString(body), MediaType.parse(JSON_MEDIA_TYPE));
    Request request =
        new Request.Builder()
            .url(this.baseUrl + "/chat/completions")
            .addHeader("Authorization", "Bearer " + this.accessToken)
            .post(requestBody)
            .build();
    final Call call = this.httpClient.newCall(request);
    Response response = call.execute();
    if (response.isSuccessful()) {
      ResponseBody responseBody = response.body();
      if (Objects.nonNull(responseBody)) {
        String content = responseBody.string();
        log.info("===> Response content: {}", content);
        return this.objectMapper.readValue(content, ChatCompletionsResponseBody.class);
      }
    } else {
      ResponseBody responseBody = response.body();
      if (Objects.nonNull(responseBody)) {
        String content = responseBody.string();
        log.warn("===> Response content: {}", content);
        throw new MoonShotRequestException(content);
      }
    }
    return null;
  }
}
