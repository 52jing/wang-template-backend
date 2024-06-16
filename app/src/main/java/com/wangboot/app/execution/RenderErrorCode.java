package com.wangboot.app.execution;

import com.wangboot.core.errorcode.IErrorCode;
import lombok.Generated;
import lombok.Getter;

@Generated
public enum RenderErrorCode implements IErrorCode {
  // 渲染错误
  INVALID_TEMPLATE(400, "440001", "error.invalid_template"),
  INVALID_DATASOURCE(400, "440001", "error.invalid_datasource"),
  INVALID_DATASOURCE_CONFIG(400, "440002", "error.invalid_datasource_config"),
  PARAM_IS_REQUIRED(400, "440003", "error.param_is_required"),
  RENDER_FAILED(400, "440004", "error.render_failed"),
  RETRIEVE_EMPTY_DATA(400, "440005", "error.retrieve_empty_data"),
  CONNECT_DATASOURCE_FAILED(400, "440006", "error.connect_datasource_failed"),
  ANALYSIS_NOT_ENABLED(500, "510003", "error.analysis_not_enabled"),
  ANALYSIS_FAILED(500, "510004", "error.analysis_failed");

  /** Http Status Code */
  @Getter private final int statusCode;

  /** Error Code */
  @Getter private final String errCode;

  /** Error Message */
  @Getter private final String errMsg;

  RenderErrorCode(int statusCode, String errCode, String errMsg) {
    this.statusCode = statusCode;
    this.errCode = errCode;
    this.errMsg = errMsg;
  }

  @Override
  public String toString() {
    return this.errCode + " " + this.errMsg;
  }
}
