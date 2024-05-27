package com.wangboot.system.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class SysUserExcel {

  @ExcelProperty("ID")
  private String id;

  @ExcelProperty("用户名")
  private String username;

  @ExcelProperty("昵称")
  private String nickname;

  @ExcelProperty("邮箱")
  private String email;

  @ExcelProperty("手机")
  private String tel;

  @ExcelProperty("是否激活")
  private Boolean active;

  @ExcelProperty("是否超级管理员")
  private Boolean superuser;

  @ExcelProperty("是否内部用户")
  private Boolean staff;

  @ExcelProperty("失效时间")
  private OffsetDateTime expiredTime;

  @ExcelProperty("性别")
  private String sexVal;

  @ExcelProperty("部门")
  private String department;

  @ExcelProperty("岗位")
  private String job;

  @ExcelProperty("省")
  private String province;

  @ExcelProperty("市")
  private String city;

  @ExcelProperty("区")
  private String area;

  @ExcelProperty("县")
  private String town;

  @ExcelProperty("详细地址")
  private String address;

  @ExcelProperty("备注")
  private String remark;
}
