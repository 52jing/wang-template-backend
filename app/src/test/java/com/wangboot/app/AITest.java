package com.wangboot.app;

import cn.hutool.core.io.resource.ResourceUtil;
import com.wangboot.app.analysis.MoonShotClient;
import com.wangboot.app.analysis.model.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@DisplayName("AI测试")
public class AITest {

  public static final String MOONSHOT_ACCESS_TOKEN = "";

  @Disabled
  @Test
  @SneakyThrows
  public void testMoonShotClient() {
    MoonShotClient client = new MoonShotClient(MOONSHOT_ACCESS_TOKEN);
//    byte[] bytes = ResourceUtil.readBytes("aaa.pdf");
//    FileUploadResponseBody response = client.uploadFile(bytes, "aaa.pdf", "application/pdf");
    byte[] bytes = ResourceUtil.readBytes("aaa.docx");
    FileUploadResponseBody response = client.uploadFile(bytes, "aaa.docx", "application/msword");
    System.out.println(response);
    Assertions.assertNotNull(response);
    String fileId = response.getId();
    FileContentResponseBody response1 = client.getFileContent(fileId);
    System.out.println(response1);
    Assertions.assertNotNull(response1);
    ChatMessage chatMessage1 = new ChatMessage("system", "你是一个专业且高效的文档分析助理，能够精准地为用户阅读报告并准确提取其中的关键主要指标。");
    ChatMessage chatMessage2 = new ChatMessage("system", response1.getContent());
    ChatMessage chatMessage3 = new ChatMessage("user", "请提取文件中的主要指标");
    ChatCompletionsRequestBody body = new ChatCompletionsRequestBody("moonshot-v1-8k", Arrays.asList(chatMessage1, chatMessage2, chatMessage3), 0.3);
    ChatCompletionsResponseBody response2 = client.chatCompletions(body);
    System.out.println(response2);
    Assertions.assertNotNull(response2);
  }
}
