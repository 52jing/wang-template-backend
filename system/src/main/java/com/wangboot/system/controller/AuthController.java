package com.wangboot.system.controller;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.wangboot.core.auth.model.LoginBody;
import com.wangboot.core.auth.model.LogoutBody;
import com.wangboot.core.auth.model.RefreshTokenBody;
import com.wangboot.core.auth.token.TokenPair;
import com.wangboot.core.auth.user.IUserModel;
import com.wangboot.core.captcha.CaptchaProcessorHolder;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.errorcode.HttpErrorCode;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.response.ListBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.system.entity.table.SysUserTableDef;
import com.wangboot.system.entity.vo.SysUserSearch;
import com.wangboot.system.model.RegisterBody;
import com.wangboot.system.model.TokenResponseBody;
import com.wangboot.system.service.AuthService;
import com.wangboot.system.service.SysUserService;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证相关接口
 *
 * @author wwtg99
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  private final SysUserService userService;

  /** 登录 */
  @PostMapping("/login")
  public ResponseEntity<?> login(@Validated @RequestBody LoginBody body) {
    TokenPair token = authService.login(body);
    TokenResponseBody body1 =
        new TokenResponseBody(
            Objects.nonNull(token.getAccessToken()) ? token.getAccessToken().getString() : "",
            Objects.nonNull(token.getRefreshToken()) ? token.getRefreshToken().getString() : "",
            200);
    return ResponseUtils.success(body1);
  }

  /** 登出 */
  @PostMapping("/logout")
  public ResponseEntity<?> logout(@Validated @RequestBody LogoutBody body) {
    return ResponseUtils.success(DetailBody.ok(authService.logout(body)));
  }

  /** 刷新令牌 */
  @PostMapping("/refresh_token")
  public ResponseEntity<?> refreshToken(@Validated @RequestBody RefreshTokenBody refreshTokenBody) {
    TokenPair token = authService.refreshToken(refreshTokenBody);
    TokenResponseBody body1 =
        new TokenResponseBody(
            Objects.nonNull(token.getAccessToken()) ? token.getAccessToken().getString() : "",
            Objects.nonNull(token.getRefreshToken()) ? token.getRefreshToken().getString() : "",
            200);
    return ResponseUtils.success(body1);
  }

  /** 注册 */
  @PostMapping("/register")
  public ResponseEntity<?> register(@Validated @RequestBody RegisterBody registerBody) {
    if (StringUtils.hasText(registerBody.getFrontendId())) {
      IUserModel userModel = this.authService.register(registerBody);
      return ResponseUtils.success(DetailBody.ok(userModel.getUserId()));
    } else {
      throw new ErrorCodeException(ErrorCode.REGISTER_FAILED);
    }
  }

  /**
   * 图片验证码<br>
   * 参考配置如下<br>
   *
   * @param uid 随机字符串
   *     <pre>
   * app:
   *   captcha:
   *     enabled: true
   *     image:
   *       enabled: true
   *       # circle, shear, gif, line 中任一个
   *       type: line
   *       # 字符长度
   *       length: 4
   *       # 混乱度
   *       chaos: 300
   *       # 图片宽度
   *       width: 200
   *       # 图片高度
   *       height: 100
   *       # 验证码有效时间（秒），0 则不限制
   *       ttl: 300
   * </pre>
   */
  @GetMapping("/captcha/image")
  public ResponseEntity<?> getCaptchaImage(@RequestParam String uid) {
    String type = "image";
    Map<String, String> data = CaptchaProcessorHolder.generateAndSend(type, uid);
    return ResponseUtils.success(DetailBody.ok(data));
  }

  /** 校验验证码 */
  @PostMapping("/captcha/verify")
  public ResponseEntity<?> verifyCaptcha(@RequestBody LoginBody loginBody) {
    boolean ret =
        CaptchaProcessorHolder.verifyCaptcha(
            loginBody.getCaptchaType(), loginBody.getCaptcha(), loginBody.getUuid(), false);
    return ResponseUtils.success(DetailBody.ok(ret));
  }

  /** 搜索用户 */
  @GetMapping("/user/search")
  public ResponseEntity<?> queryUsername(
      @RequestParam String query,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    if (StringUtils.hasText(query)) {
      QueryWrapper wrapper =
          this.userService
              .query()
              .where(SysUserTableDef.SYS_USER.USERNAME.likeLeft(query))
              .or(SysUserTableDef.SYS_USER.NICKNAME.likeLeft(query))
              .select(
                  SysUserTableDef.SYS_USER.ID,
                  SysUserTableDef.SYS_USER.USERNAME,
                  SysUserTableDef.SYS_USER.NICKNAME);
      Page<SysUserSearch> data =
          this.userService.pageAs(new Page<>(page, pageSize), wrapper, SysUserSearch.class);
      ListBody<SysUserSearch> body = new ListBody<>();
      body.setData(data.getRecords());
      body.setPage(data.getPageNumber());
      body.setPageSize(data.getPageSize());
      body.setTotal(data.getTotalRow());
      return ResponseUtils.success(body);
    } else {
      throw new ErrorCodeException(HttpErrorCode.BAD_REQUEST);
    }
  }
}
