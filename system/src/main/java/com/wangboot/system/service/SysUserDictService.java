package com.wangboot.system.service;

import com.wangboot.model.flex.IFlexRestfulService;
import com.wangboot.system.entity.SysUserDict;
import java.util.List;

/**
 * 服务层。
 *
 * @author wwtg99
 */
public interface SysUserDictService extends IFlexRestfulService<String, SysUserDict> {

  List<SysUserDict> getByGroup(String group, String code);
}
