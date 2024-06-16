package com.wangboot.system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FrontendVo {
  private String id;
  private String title;
  private String description;
  private String author;
  private String domain;
}
