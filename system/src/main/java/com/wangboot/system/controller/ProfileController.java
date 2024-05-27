package com.wangboot.system.controller;

import com.wangboot.core.auth.user.IUserModel;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.model.entity.exception.UpdateFailedException;
import com.wangboot.system.entity.SysUser;
import com.wangboot.system.model.ChangePasswordBody;
import com.wangboot.system.service.AuthService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户账户相关接口
 *
 * @author wwtg99
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

  private final AuthService authService;

  /** 获取当前用户信息 */
  @GetMapping("/profile")
  public ResponseEntity<?> getProfile() {
    return ResponseUtils.success(DetailBody.ok(authService.getProfile()));
  }

  /** 修改当前用户信息 */
  @PostMapping("/profile")
  public ResponseEntity<?> updateProfile(@Validated @RequestBody SysUser user) {
    IUserModel userModel = authService.updateProfile(user);
    if (Objects.nonNull(userModel)) {
      return ResponseUtils.success(DetailBody.ok(userModel));
    } else {
      throw new UpdateFailedException(user.getId());
    }
  }

  /** 获取当前用户权限 */
  @GetMapping("/my_permissions")
  public ResponseEntity<?> getMyPermissions() {
    return ResponseUtils.success(DetailBody.ok(authService.getMyPermissions()));
  }

  /** 获取当前用户菜单 */
  @GetMapping("/my_menus")
  public ResponseEntity<?> getMyMenus() {
    return ResponseUtils.success(DetailBody.ok(authService.getMyMenus()));
  }

  /** 修改当前用户密码 */
  @PostMapping("/change_password")
  public ResponseEntity<?> changePassword(
      @Validated @RequestBody ChangePasswordBody changePasswordBody) {
    boolean ret = authService.changePassword(changePasswordBody);
    if (ret) {
      return ResponseUtils.success(DetailBody.ok(true));
    } else {
      throw new ErrorCodeException(ErrorCode.PASSWORD_CHANGE_FAILED);
    }
  }
}
