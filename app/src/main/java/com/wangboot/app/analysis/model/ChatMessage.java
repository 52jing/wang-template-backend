package com.wangboot.app.analysis.model;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
  private String role;
  private String content;
}
