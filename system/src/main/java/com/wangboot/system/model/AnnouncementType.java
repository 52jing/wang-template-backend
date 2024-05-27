package com.wangboot.system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mybatisflex.annotation.EnumValue;
import com.wangboot.core.utils.ISystemDict;
import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * 公告类型
 *
 * @author wwtg99
 */
public enum AnnouncementType implements ISystemDict {
  IMPORTANT("IMPORTANT", "重要公告"),
  NOTIFICATION("NOTIFICATION", "弹窗公告"),
  NORMAL("NORMAL", "普通公告");

  @Getter @EnumValue private final String code;

  @Getter private final String name;

  AnnouncementType(String code, String name) {
    this.code = code;
    this.name = name;
  }

  @Nullable
  @JsonCreator
  public static AnnouncementType resolve(String code) {
    for (AnnouncementType type : AnnouncementType.values()) {
      if (type.getCode().equals(code) || type.getName().equals(code)) {
        return type;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
