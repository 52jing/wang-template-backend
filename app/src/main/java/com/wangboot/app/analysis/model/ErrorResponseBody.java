package com.wangboot.app.analysis.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorResponseBody {

  private Error error;

  static class Error {
    private String message;
    private String type;
  }
}
