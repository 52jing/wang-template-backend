package com.wangboot.system.service;

import com.wangboot.core.auth.model.ILoginBody;
import com.wangboot.core.auth.model.ILogoutBody;
import com.wangboot.core.auth.model.IRefreshTokenBody;
import com.wangboot.core.auth.token.TokenPair;
import com.wangboot.core.auth.user.IUserModel;
import com.wangboot.system.entity.SysDataScope;
import com.wangboot.system.entity.SysMenu;
import com.wangboot.system.entity.SysPermission;
import com.wangboot.system.entity.SysUser;
import com.wangboot.system.model.ChangePasswordBody;
import com.wangboot.system.model.RegisterBody;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface AuthService {
  /** 注册 */
  @NonNull
  IUserModel register(@NonNull RegisterBody registerBody);

  /** 登录 */
  @NonNull
  TokenPair login(@NonNull ILoginBody loginBody);

  /** 登出 */
  boolean logout(@NonNull ILogoutBody logoutBody);

  /** 用户修改密码 */
  boolean changePassword(@NonNull ChangePasswordBody changePasswordBody);

  /** 管理员直接设置密码 */
  boolean setPassword(
      @NonNull IUserModel userModel, @NonNull ChangePasswordBody changePasswordBody);

  /** 获取用户信息 */
  @Nullable
  IUserModel getProfile();

  /** 更新用户信息 */
  @Nullable
  IUserModel updateProfile(@Nullable SysUser user);

  /** 刷新令牌 */
  @NonNull
  TokenPair refreshToken(IRefreshTokenBody refreshTokenBody);

  @NonNull
  List<SysPermission> getMyPermissions();

  @NonNull
  List<SysDataScope> getMyDataScopes();

  /** 获取当前用户菜单 */
  @NonNull
  List<SysMenu> getMyMenus();
}
