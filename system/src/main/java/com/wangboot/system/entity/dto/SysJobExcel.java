package com.wangboot.system.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SysJobExcel {

  @ExcelProperty("ID")
  private String id;

  @ExcelProperty("名称")
  private String name;

  @ExcelProperty("类型")
  private String type;

  @ExcelProperty("备注")
  private String remark;
}
