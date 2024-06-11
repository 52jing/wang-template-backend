package com.wangboot.app.analysis.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FileUploadResponseBody {
  private String id;
  private String object;
  private long bytes;
  private long createdAt;
  private String filename;
  private String purpose;
  private String status;
  private String status_details;

}
