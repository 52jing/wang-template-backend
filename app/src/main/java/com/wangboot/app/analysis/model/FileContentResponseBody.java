package com.wangboot.app.analysis.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FileContentResponseBody {

  private String content;
  private String fileType;
  private String filename;
  private String title;
  private String type;
}
