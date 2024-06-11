package com.wangboot.app.analysis.model;

import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionsRequestBody {
  private String model;
  private List<ChatMessage> messages;
  private double temperature;
}
