package com.wangboot.app.analysis.model;

import java.util.List;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionsRequestBody {
  private String model;
  private List<ChatMessage> messages;
  private double temperature;
}
