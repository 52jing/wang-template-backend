package com.wangboot.system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mybatisflex.annotation.EnumValue;
import com.wangboot.core.utils.ISystemDict;
import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * 后台任务状态
 *
 * @author wwtg99
 */
public enum BgTaskStatus implements ISystemDict {
  PENDING("PENDING", "运行中"),
  COMPLETED("COMPLETED", "已完成"),
  FAILED("FAILED", "已失败"),
  CANCELED("CANCELED", "已取消");

  @Getter @EnumValue private final String code;

  @Getter private final String name;

  BgTaskStatus(String code, String name) {
    this.code = code;
    this.name = name;
  }

  @Nullable
  @JsonCreator
  public static BgTaskStatus resolve(String code) {
    for (BgTaskStatus status : BgTaskStatus.values()) {
      if (status.getCode().equals(code) || status.getName().equals(code)) {
        return status;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
