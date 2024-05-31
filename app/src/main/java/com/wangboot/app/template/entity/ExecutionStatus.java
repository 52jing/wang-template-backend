package com.wangboot.app.template.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mybatisflex.annotation.EnumValue;
import com.wangboot.core.utils.ISystemDict;
import lombok.Getter;
import org.springframework.lang.Nullable;

public enum ExecutionStatus implements ISystemDict {

  WAITING("1", "等待中"),
  PENDING("2", "处理中"),
  COMPLETED("3", "已完成"),
  FAILED("4", "已失败"),
  CANCELED("5", "已取消");

  @Getter @EnumValue private final String code;

  @Getter private final String name;

  ExecutionStatus(String code, String name) {
    this.code = code;
    this.name = name;
  }

  @Nullable
  @JsonCreator
  public static ExecutionStatus resolve(String code) {
    for (ExecutionStatus executionStatus : ExecutionStatus.values()) {
      if (executionStatus.getCode().equals(code) || executionStatus.getName().equals(code)) {
        return executionStatus;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
