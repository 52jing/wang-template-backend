package com.wangboot.app.analysis;

import cn.hutool.extra.spring.SpringUtil;
import com.wangboot.app.analysis.exception.MoonShotRequestException;
import com.wangboot.app.analysis.model.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class AnalysisManager {

  public MoonShotClient getMoonShotClient() {
    return SpringUtil.getBean(MoonShotClient.class);
  }

  public List<ChatCompletionsResponseBody.Choice> analysisIndicators(byte[] bytes, String filename, String contentType) throws IOException {
    MoonShotClient client = getMoonShotClient();
    FileUploadResponseBody response = client.uploadFile(bytes, filename, contentType);
    if (Objects.isNull(response)) {
      throw new MoonShotRequestException("Upload file failed");
    }
    String fileId = response.getId();
    FileContentResponseBody response1 = client.getFileContent(fileId);
    if (Objects.isNull(response1)) {
      throw new MoonShotRequestException("Get file content failed");
    }
    ChatMessage chatMessage1 = new ChatMessage("system", "你是一个专业且高效的文档分析助理，能够精准地为用户阅读报告并准确提取其中的关键主要指标。");
    ChatMessage chatMessage2 = new ChatMessage("system", response1.getContent());
    ChatMessage chatMessage3 = new ChatMessage("user", "请提取文件中的主要指标");
    ChatCompletionsRequestBody body = new ChatCompletionsRequestBody("moonshot-v1-8k", Arrays.asList(chatMessage1, chatMessage2, chatMessage3), 0.3);
    ChatCompletionsResponseBody response2 = client.chatCompletions(body);
    if (Objects.isNull(response2)) {
      throw new MoonShotRequestException("Get indicator failed");
    }
    return response2.getChoices();
  }

}
