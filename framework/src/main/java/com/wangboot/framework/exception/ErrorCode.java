package com.wangboot.framework.exception;

import com.wangboot.core.errorcode.IErrorCode;
import lombok.Generated;
import lombok.Getter;

/**
 * 自定义错误码<br>
 * 错误码一般规则<br>
 * statusCode 为 HTTP 状态码<br>
 * errCode 为自定义错误码，一般为 6 位数字，第一位对应 HTTP 状态码大类（例如 4XX/5XX），<br>
 * 第二位是子分类，后四位为序号，如 41XXXX，42XXXX 等子分类。<br>
 * 后四位为自定义序号。<br>
 * errMsg 为错误消息，一般为 I18N 消息键（可实现国际化），也可直接为字符串。
 *
 * @author wwtg99
 */
@Generated
public enum ErrorCode implements IErrorCode {
  // 认证错误
  CAPTCHA_MISMATCH(400, "410001", "error.captcha_mismatch"),
  EXPIRED_ACCOUNT(400, "410002", "error.expired_account"),
  INVALID_ACCOUNT(400, "410003", "error.invalid_account"),
  INVALID_TOKEN(400, "410004", "error.invalid_token"),
  LOGIN_KICKED_OUT(423, "410005", "error.login_kicked_out"),
  LOCKED_ACCOUNT(400, "410006", "error.locked_account"),
  LOGIN_FAILED(400, "410007", "error.login_failed"),
  USER_LOGIN_LOCKED(400, "410008", "error.user_login_locked"),
  LOGOUT_FAILED(400, "410009", "error.logout_failed"),
  NON_EXISTS_FRONTEND(400, "410010", "error.non_exist_frontend"),
  REFRESH_TOKEN_FAILED(400, "410011", "error.refresh_token_failed"),
  CAPTCHA_ERROR(400, "410012", "error.captcha_error"),
  REGISTER_FAILED(400, "410013", "error.register_failed"),
  PASSWORD_CHANGE_FAILED(400, "410014", "error.pwd_change_failed"),
  PASSWORD_CHECK_FAILED(400, "410015", "error.pwd_check_failed"),
  DUPLICATED_USERNAME(400, "410016", "error.duplicated_username"),
  // 文件错误
  FILE_EXCEED_MAX_SIZE(400, "420001", "error.file_exceed_max_size"),
  UPLOAD_FAILED(400, "420002", "error.upload_failed"),
  EXPORT_FAILED(400, "420003", "error.export_failed"),
  // 数据和校验错误
  CREATE_FAILED(400, "430001", "error.create_failed"),
  UPDATE_FAILED(400, "430002", "error.update_failed"),
  DELETE_FAILED(400, "430003", "error.delete_failed"),
  DELETE_CASCADE_FAILED(400, "430004", "error.delete_cascade_failed"),
  DUPLICATED_RECORD(400, "430005", "error.duplicated_record"),
  // 渲染错误
  INVALID_TEMPLATE(400, "440001", "error.invalid_template"),
  INVALID_DATASOURCE(400, "440001", "error.invalid_datasource"),
  INVALID_DATASOURCE_CONFIG(400, "440002", "error.invalid_datasource_config"),
  PARAM_IS_REQUIRED(400, "440003", "error.param_is_required"),
  RENDER_FAILED(400, "440004", "error.render_failed"),
  RETRIEVE_EMPTY_DATA(400, "440005", "error.retrieve_empty_data"),
  // 其他错误
  CRYPTO_ERROR(500, "510001", "error.crypto_error"),
  IN_MAINTENANCE(503, "510002", "error.in_maintenance");

  /** Http Status Code */
  @Getter private final int statusCode;

  /** Error Code */
  @Getter private final String errCode;

  /** Error Message */
  @Getter private final String errMsg;

  ErrorCode(int statusCode, String errCode, String errMsg) {
    this.statusCode = statusCode;
    this.errCode = errCode;
    this.errMsg = errMsg;
  }

  @Override
  public String toString() {
    return this.errCode + " " + this.errMsg;
  }
}
