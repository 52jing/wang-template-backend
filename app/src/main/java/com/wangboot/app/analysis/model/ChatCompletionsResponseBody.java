package com.wangboot.app.analysis.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ChatCompletionsResponseBody {
  private String id;
  private String object;
  private long created;
  private String model;
  private List<Choice> choices;
  private Usage usage;

  @Data
  @ToString
  public static class Choice {
    private long index;
    private ChatMessage message;
    private String finishReason;

  }

  @Data
  @ToString
  public static class Usage {
    private long promptTokens;
    private long completionTokens;
    private long totalTokens;
  }
}
