package com.wangboot.system.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.wangboot.core.auth.AuthFlow;
import com.wangboot.core.auth.context.ILoginUser;
import com.wangboot.core.auth.event.LogStatus;
import com.wangboot.core.auth.event.UserEvent;
import com.wangboot.core.auth.event.UserEventLog;
import com.wangboot.core.auth.model.ILoginBody;
import com.wangboot.core.auth.model.ILogoutBody;
import com.wangboot.core.auth.model.IRefreshTokenBody;
import com.wangboot.core.auth.token.TokenPair;
import com.wangboot.core.auth.user.IUserModel;
import com.wangboot.core.auth.utils.AuthUtils;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.web.utils.ServletUtils;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.framework.utils.WUtils;
import com.wangboot.system.entity.SysDataScope;
import com.wangboot.system.entity.SysMenu;
import com.wangboot.system.entity.SysPermission;
import com.wangboot.system.entity.SysUser;
import com.wangboot.system.entity.table.SysUserTableDef;
import com.wangboot.system.entity.vo.SysUserView;
import com.wangboot.system.model.ChangePasswordBody;
import com.wangboot.system.model.RegisterBody;
import com.wangboot.system.service.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthFlow authFlow;

  private final SysUserService userService;

  private final SysUserViewService userViewService;

  private final SysMenuService menuService;

  private final PasswordEncoder passwordEncoder;

  @Override
  @NonNull
  public IUserModel register(@NonNull RegisterBody registerBody) {
    // 检查重复
    QueryWrapper wrapper =
        this.userService
            .query()
            .where(SysUserTableDef.SYS_USER.USERNAME.eq(registerBody.getUsername()));
    if (StringUtils.hasText(registerBody.getEmail())) {
      wrapper.or(SysUserTableDef.SYS_USER.EMAIL.eq(registerBody.getEmail()));
    }
    if (StringUtils.hasText(registerBody.getTel())) {
      wrapper.or(SysUserTableDef.SYS_USER.TEL.eq(registerBody.getTel()));
    }
    if (this.userService.count(wrapper) > 0) {
      throw new ErrorCodeException(ErrorCode.DUPLICATED_USERNAME);
    }
    // 检查密码策略
    if (!WUtils.getPasswordStrategy().checkStrategies(registerBody.getPassword())) {
      throw new ErrorCodeException(ErrorCode.PASSWORD_CHECK_FAILED);
    }
    SysUser user = new SysUser();
    user.setUsername(registerBody.getUsername());
    user.setNickname(registerBody.getNickname());
    user.setPassword(passwordEncoder.encode(registerBody.getPassword()));
    user.setEmail(registerBody.getEmail());
    user.setTel(registerBody.getTel());
    boolean ret = this.userService.saveData(user);
    if (ret) {
      SpringUtil.publishEvent(
          new UserEvent(
              UserEventLog.builder()
                  .status(LogStatus.SUCCESS)
                  .event("register")
                  .username(registerBody.getUsername())
                  .message("user registered")
                  .build(),
              ServletUtils.getRequest()));
      return user;
    } else {
      throw new ErrorCodeException(ErrorCode.REGISTER_FAILED);
    }
  }

  @Override
  @NonNull
  public TokenPair login(@NonNull ILoginBody body) {
    return authFlow.loginAndGenerateToken(body);
  }

  @Override
  public boolean logout(@NonNull ILogoutBody body) {
    ILoginUser loginUser = authFlow.getLoginUser();
    if (loginUser.isLogin()) {
      return authFlow.logout(body, loginUser);
    }
    return false;
  }

  @Override
  public boolean changePassword(@NonNull ChangePasswordBody changePasswordBody) {
    if (StringUtils.hasText(changePasswordBody.getOldPwd())
        && userService.verifyPassword(AuthUtils.getUserModel(), changePasswordBody.getOldPwd())) {
      return userService.setPassword(
          AuthUtils.getUserModel(), changePasswordBody.getNewPwd(), false);
    }
    return false;
  }

  @Override
  public boolean setPassword(
      @NonNull IUserModel userModel, @NonNull ChangePasswordBody changePasswordBody) {
    return userService.setPassword(userModel, changePasswordBody.getNewPwd(), true);
  }

  @Override
  @Nullable
  public IUserModel getProfile() {
    ILoginUser loginUser = authFlow.getLoginUser();
    if (loginUser.isLogin()) {
      SysUserView userView = userViewService.getById(loginUser.getUser().getUserId());
      List<SysPermission> permissions =
          userService.getUserPermissions(loginUser.getUser().getUserId());
      userView.setAuthorities(
          permissions.stream().map(SysPermission::getName).collect(Collectors.toList()));
      return userView;
    }
    return null;
  }

  @Override
  @Nullable
  public IUserModel updateProfile(@Nullable SysUser user) {
    if (Objects.isNull(user)) {
      return null;
    }
    user.setId(AuthUtils.getUserId());
    if (userService.updateProfile(user)) {
      return userService.viewResource(user.getId());
    }
    return null;
  }

  @Override
  @NonNull
  public TokenPair refreshToken(IRefreshTokenBody body) {
    return authFlow.refreshAndGenerateToken(body);
  }

  @Override
  @NonNull
  public List<SysPermission> getMyPermissions() {
    if (!authFlow.getLoginUser().isLogin()) {
      return Collections.emptyList();
    }
    if (AuthUtils.isSuperuser()) {
      return Collections.emptyList();
    } else {
      return userService.getUserPermissions(authFlow.getUserId());
    }
  }

  @Override
  @NonNull
  public List<SysDataScope> getMyDataScopes() {
    if (!authFlow.getLoginUser().isLogin()) {
      return Collections.emptyList();
    }
    if (AuthUtils.isSuperuser()) {
      return Collections.emptyList();
    } else {
      return userService.getUserDataScopes(authFlow.getUserId());
    }
  }

  @Override
  @NonNull
  public List<SysMenu> getMyMenus() {
    if (!authFlow.getLoginUser().isLogin()) {
      return Collections.emptyList();
    }
    if (AuthUtils.isSuperuser()) {
      return menuService.list();
    } else {
      return userService.getUserMenus(authFlow.getUserId());
    }
  }
}
