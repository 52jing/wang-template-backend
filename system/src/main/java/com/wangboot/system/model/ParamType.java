package com.wangboot.system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mybatisflex.annotation.EnumValue;
import com.wangboot.core.utils.ISystemDict;
import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * 参数类型枚举
 *
 * @author wwtg99
 */
public enum ParamType implements ISystemDict {
  STR("str", "字符串"),
  INT("int", "整数"),
  FLOAT("float", "浮点数"),
  BOOL("bool", "布尔值"),
  STR_LIST("str_list", "字符串数组"),
  INT_LIST("int_list", "整数数组"),
  FLOAT_LIST("float_list", "浮点数数组"),
  BOOL_LIST("bool_list", "布尔值数组");

  @Getter @EnumValue private final String code;
  @Getter private final String name;

  ParamType(String code, String name) {
    this.code = code;
    this.name = name;
  }

  @Nullable
  @JsonCreator
  public static ParamType resolve(String code) {
    for (ParamType paramType : ParamType.values()) {
      if (paramType.getCode().equalsIgnoreCase(code)
          || paramType.getName().equalsIgnoreCase(code)) {
        return paramType;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
