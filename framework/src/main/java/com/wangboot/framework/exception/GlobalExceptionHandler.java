package com.wangboot.framework.exception;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.crypto.CryptoException;
import com.fasterxml.jackson.core.JacksonException;
import com.wangboot.core.auth.exception.*;
import com.wangboot.core.captcha.exception.InvalidCaptchaImageException;
import com.wangboot.core.captcha.exception.InvalidUidException;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.errorcode.HttpErrorCode;
import com.wangboot.core.web.exception.*;
import com.wangboot.core.web.handler.BaseErrorCodeHandler;
import com.wangboot.model.entity.exception.*;
import com.wangboot.starter.autoconfiguration.WbProperties;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局统一异常处理器
 *
 * @author wwtg99
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseErrorCodeHandler {

  public GlobalExceptionHandler(WbProperties wbProperties) {
    super(wbProperties.isDebug());
  }

  /** 验证码不匹配异常 */
  @ExceptionHandler(CaptchaMismatchException.class)
  public ResponseEntity<?> handleCaptchaMismatchException(
      CaptchaMismatchException e, HttpServletRequest request) {
    return toResponse(ErrorCode.CAPTCHA_MISMATCH);
  }

  /** 账户已超期异常 */
  @ExceptionHandler(ExpiredAccountException.class)
  public ResponseEntity<?> handleExpiredAccountException(
      ExpiredAccountException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.EXPIRED_ACCOUNT, e.getAccount());
  }

  /** 无效的账户异常 */
  @ExceptionHandler(InvalidAccountException.class)
  public ResponseEntity<?> handleInvalidAccountException(
      InvalidAccountException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.INVALID_ACCOUNT, e.getAccount());
  }

  /** 无效的令牌异常 */
  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<?> handleInvalidTokenException(
      InvalidTokenException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.INVALID_TOKEN);
  }

  /** 被踢出异常 */
  @ExceptionHandler(KickedOutException.class)
  public ResponseEntity<?> handleKickedOutException(
      KickedOutException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.LOGIN_KICKED_OUT);
  }

  /** 用户已锁定异常 */
  @ExceptionHandler(LockedAccountException.class)
  public ResponseEntity<?> handleLockedAccountException(
      LockedAccountException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.LOCKED_ACCOUNT, e.getAccount());
  }

  /** 用户已锁定异常 */
  @ExceptionHandler(LoginFailedException.class)
  public ResponseEntity<?> handleLoginFailedException(
      LoginFailedException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.LOGIN_FAILED);
  }

  /** 登录锁定异常 */
  @ExceptionHandler(LoginLockedException.class)
  public ResponseEntity<?> handleLoginLockedException(
      LoginLockedException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.USER_LOGIN_LOCKED, e.getAccount());
  }

  /** 登出失败异常 */
  @ExceptionHandler(LogoutFailedException.class)
  public ResponseEntity<?> handleLogoutFailedException(
      LogoutFailedException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.LOGOUT_FAILED);
  }

  /** 用户不存在异常 */
  @ExceptionHandler(NonExistsAccountException.class)
  public ResponseEntity<?> handleNonExistsAccountException(
      NonExistsAccountException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.LOGIN_FAILED);
  }

  /** 不存在的前端异常 */
  @ExceptionHandler(NonExistsFrontendException.class)
  public ResponseEntity<?> handleNonExistsFrontendException(
      NonExistsFrontendException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.NON_EXISTS_FRONTEND, e.getFrontend());
  }

  /** 未认证 */
  @ExceptionHandler(NotAuthenticatedException.class)
  public ResponseEntity<?> handleNotAuthenticatedException(
      NotAuthenticatedException e, HttpServletRequest request) {
    return toResponse(HttpErrorCode.UNAUTHORIZED);
  }

  /** 没有权限异常 */
  @ExceptionHandler(PermissionDeniedException.class)
  public ResponseEntity<?> handlePermissionDeniedException(
      PermissionDeniedException e, HttpServletRequest request) {
    return toResponse(HttpErrorCode.FORBIDDEN);
  }

  /** 刷新令牌失败异常 */
  @ExceptionHandler(RefreshTokenFailedException.class)
  public ResponseEntity<?> handleRefreshTokenFailedException(
      RefreshTokenFailedException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.REFRESH_TOKEN_FAILED);
  }

  /** 用户名密码不匹配异常 */
  @ExceptionHandler(UsernamePasswordMismatchException.class)
  public ResponseEntity<?> handleUsernamePasswordMismatchException(
      UsernamePasswordMismatchException e, HttpServletRequest request) {
    processWarning(e, e.getMessage());
    return toResponse(ErrorCode.LOGIN_FAILED);
  }

  /** 验证码图片错误 */
  @ExceptionHandler(InvalidCaptchaImageException.class)
  public ResponseEntity<?> handleInvalidCaptchaImageException(
      InvalidCaptchaImageException e, HttpServletRequest request) {
    processError(e, e.getMessage());
    return toResponse(ErrorCode.CAPTCHA_ERROR);
  }

  /** 无效的唯一码异常 */
  @ExceptionHandler(InvalidUidException.class)
  public ResponseEntity<?> handleInvalidUidException(
      InvalidUidException e, HttpServletRequest request) {
    processError(e, e.getMessage());
    return toResponse(ErrorCode.CAPTCHA_ERROR);
  }

  /** 创建失败异常 */
  @ExceptionHandler(CreateFailedException.class)
  public ResponseEntity<?> handleCreateFailedException(
      CreateFailedException e, HttpServletRequest request) {
    processError(e, e.getMessage());
    return toResponse(ErrorCode.CREATE_FAILED);
  }

  /** 级联删除失败异常 */
  @ExceptionHandler(DeleteCascadeFailedException.class)
  public ResponseEntity<?> handleDeleteCascadeFailedException(
      DeleteCascadeFailedException e, HttpServletRequest request) {
    processError(e, e.getMessage());
    return toResponse(ErrorCode.DELETE_CASCADE_FAILED, e.getDeleteId());
  }

  /** 删除失败异常 */
  @ExceptionHandler(DeleteFailedException.class)
  public ResponseEntity<?> handleDeleteFailedException(
      DeleteFailedException e, HttpServletRequest request) {
    processError(e, e.getMessage());
    return toResponse(ErrorCode.DELETE_FAILED, e.getDeleteId());
  }

  /** 更新失败异常 */
  @ExceptionHandler(UpdateFailedException.class)
  public ResponseEntity<?> handleUpdateFailedException(
      UpdateFailedException e, HttpServletRequest request) {
    processError(e, e.getMessage());
    return toResponse(ErrorCode.UPDATE_FAILED, e.getUpdateId());
  }

  /** 数据重复异常 */
  @ExceptionHandler(DuplicatedException.class)
  public ResponseEntity<?> handleDuplicatedException(
      DuplicatedException e, HttpServletRequest request) {
    processError(e, e.getMessage());
    return toResponse(ErrorCode.DUPLICATED_RECORD);
  }

  /** 找不到异常 */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handleNotFoundException(
      NotFoundException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s %s 找不到", request.getMethod(), request.getRequestURI());
    processError(e, msg);
    return toResponse(HttpErrorCode.NOT_FOUND);
  }

  /** 不支持的组件异常 */
  @ExceptionHandler(NotSupportedComponentException.class)
  public ResponseEntity<?> handleNotSupportedComponentException(
      NotSupportedComponentException e, HttpServletRequest request) {
    String msg =
        String.format(
            "请求地址 %s %s 组件不支持 %s", request.getMethod(), request.getRequestURI(), e.getMessage());
    processError(e, msg);
    return toResponse(HttpErrorCode.INTERNAL_SERVER_ERROR);
  }

  /** 维护中异常 */
  @ExceptionHandler(InMaintenanceException.class)
  public ResponseEntity<?> handleInMaintenanceException(
      InMaintenanceException e, HttpServletRequest request) {
    return toResponse(ErrorCode.IN_MAINTENANCE, e.getMessage());
  }

  /** 请求方式不支持 */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<?> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，不支持方法 %s", request.getRequestURI(), e.getMethod());
    processWarning(e, msg);
    return toResponse(HttpErrorCode.METHOD_NOT_ALLOWED);
  }

  /** HTTP 内容异常 */
  @ExceptionHandler(JacksonException.class)
  public ResponseEntity<?> handleJacksonException(JacksonException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，发生内容解析异常 %s", request.getRequestURI(), e.getMessage());
    processWarning(e, msg);
    return toResponse(HttpErrorCode.BAD_REQUEST);
  }

  /** HTTP 内容异常 */
  @ExceptionHandler(HttpMessageConversionException.class)
  public ResponseEntity<?> handleHttpMessageConversionException(
      HttpMessageConversionException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，发生HTTP内容异常 %s", request.getRequestURI(), e.getMessage());
    processWarning(e, msg);
    return toResponse(HttpErrorCode.BAD_REQUEST);
  }

  /** SQL 异常 */
  @ExceptionHandler(SQLException.class)
  public ResponseEntity<?> handleSQLException(SQLException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，发生SQL异常 %s", request.getRequestURI(), e.getMessage());
    processWarning(e, msg);
    return toResponse(HttpErrorCode.BAD_REQUEST);
  }

  /** 自定义验证异常 */
  @ExceptionHandler(BindException.class)
  public ResponseEntity<?> handleBindException(BindException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，发生验证异常 %s", request.getRequestURI(), e.getMessage());
    processWarning(e, msg);
    return toResponse(HttpErrorCode.BAD_REQUEST);
  }

  /** 自定义验证异常 */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，发生验证异常 %s", request.getRequestURI(), e.getMessage());
    processWarning(e, msg);
    String message = HttpErrorCode.BAD_REQUEST.getErrMsg();
    Optional<FieldError> messageOptional =
        Optional.ofNullable(e.getBindingResult().getFieldError());
    if (messageOptional.isPresent()) {
      message = messageOptional.get().getDefaultMessage();
    }
    return toResponse(
        HttpErrorCode.BAD_REQUEST.getStatusCode(), HttpErrorCode.BAD_REQUEST.getErrCode(), message);
  }

  /** 请求参数绑定错误 */
  @ExceptionHandler(ServletRequestBindingException.class)
  public ResponseEntity<?> handleServletRequestBindingException(
      ServletRequestBindingException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，发生请求参数异常 %s", request.getRequestURI(), e.getMessage());
    processWarning(e, msg);
    return toResponse(
        HttpErrorCode.BAD_REQUEST.getStatusCode(),
        HttpErrorCode.BAD_REQUEST.getErrCode(),
        e.getMessage());
  }

  /** Servlet错误 */
  @ExceptionHandler(ServletException.class)
  public ResponseEntity<?> handleServletException(ServletException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，发生服务异常 %s", request.getRequestURI(), e.getMessage());
    processWarning(e, msg);
    return toResponse(
        HttpErrorCode.BAD_REQUEST.getStatusCode(),
        HttpErrorCode.BAD_REQUEST.getErrCode(),
        e.getMessage());
  }

  /** 找不到语言文件 */
  @ExceptionHandler(NoSuchMessageException.class)
  public ResponseEntity<?> handleNoSuchMessageException(
      NoSuchMessageException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，找不到语言文件 %s", request.getRequestURI(), e.getMessage());
    processError(e, msg);
    return toResponse(HttpErrorCode.INTERNAL_SERVER_ERROR);
  }

  /** 找不到 Bean */
  @ExceptionHandler(NoSuchBeanDefinitionException.class)
  public ResponseEntity<?> handleNoSuchBeanDefinitionException(
      NoSuchBeanDefinitionException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，找不到 Bean %s", request.getRequestURI(), e.getMessage());
    processError(e, msg);
    return toResponse(HttpErrorCode.INTERNAL_SERVER_ERROR);
  }

  /** 工具类异常 */
  @ExceptionHandler(UtilException.class)
  public ResponseEntity<?> handleUtilException(UtilException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，工具类异常 %s", request.getRequestURI(), e.getMessage());
    processError(e, msg);
    return toResponse(HttpErrorCode.INTERNAL_SERVER_ERROR);
  }

  /** 加解密异常 */
  @ExceptionHandler(CryptoException.class)
  public ResponseEntity<?> handleCryptoException(CryptoException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，加解密异常 %s", request.getRequestURI(), e.getMessage());
    processError(e, msg);
    return toResponse(HttpErrorCode.INTERNAL_SERVER_ERROR);
  }

  /** 处理框架异常 */
  @ExceptionHandler(ErrorCodeException.class)
  public ResponseEntity<?> handleErrorCodeException(
      ErrorCodeException e, HttpServletRequest request) {
    String msg =
        String.format(
            "请求地址 %s，发生异常 %s: %s (%s)",
            request.getRequestURI(), e.getClass().getName(), e.getMessage(), e.getErrCode());
    processWarning(e, msg);
    return toResponse(
        e.getStatusCode(),
        e.getErrCode(),
        e.getErrMsg(),
        Objects.nonNull(e.getArgs()) ? Arrays.stream(e.getArgs()).toArray() : null);
  }

  /** 拦截未知的运行时异常 */
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，发生未知异常 %s", request.getRequestURI(), e.getMessage());
    processError(e, msg);
    return toResponse(HttpErrorCode.INTERNAL_SERVER_ERROR);
  }

  /** 所有其他异常 */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception e, HttpServletRequest request) {
    String msg = String.format("请求地址 %s，发生系统异常 %s", request.getRequestURI(), e.getMessage());
    processError(e, msg);
    return toResponse(HttpErrorCode.INTERNAL_SERVER_ERROR);
  }
}
