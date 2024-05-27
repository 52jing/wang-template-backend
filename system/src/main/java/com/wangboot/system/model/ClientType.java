package com.wangboot.system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mybatisflex.annotation.EnumValue;
import com.wangboot.core.utils.ISystemDict;
import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * 客户端类型<br>
 * 生产环境可使用随机码作为 code，以防用户猜测。 TODO 开发自定义，添加自定义客户端类型
 *
 * @author wwtg99
 */
public enum ClientType implements ISystemDict {
  /** 网页 */
  WEB("101", "桌面端"),
  /** 网页管理后台 */
  WEB_ADMIN("211", "桌面端管理后台"),
  /** H5 */
  H5("505", "H5端"),
  /** H5 管理后台 */
  H5_ADMIN("565", "H5端管理后台"),
  /** App 应用 */
  APP("699", "App端"),
  /** App 安卓应用 */
  APP_ANDROID("707", "安卓端"),
  /** App IOS 应用 */
  APP_IOS("809", "IOS端"),
  /** 微信小程序 */
  WEIXIN_APP("996", "微信小程序端");

  @Getter @EnumValue private final String code;

  @Getter private final String name;

  ClientType(String code, String name) {
    this.code = code;
    this.name = name;
  }

  @Nullable
  @JsonCreator
  public static ClientType resolve(String code) {
    for (ClientType clientType : ClientType.values()) {
      if (clientType.getCode().equals(code) || clientType.getName().equals(code)) {
        return clientType;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
