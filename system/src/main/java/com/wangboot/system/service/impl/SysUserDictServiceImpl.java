package com.wangboot.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.SysUserDict;
import com.wangboot.system.entity.table.SysUserDictTableDef;
import com.wangboot.system.mapper.SysUserDictMapper;
import com.wangboot.system.service.SysUserDictService;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 服务层实现。
 *
 * @author wwtg99
 */
@Service
public class SysUserDictServiceImpl extends ServiceImpl<SysUserDictMapper, SysUserDict>
    implements SysUserDictService {
  @Override
  public List<SysUserDict> getByGroup(String group, String code) {
    if (StringUtils.hasText(group)) {
      QueryWrapper wrapper =
          this.getListQueryWrapper().where(SysUserDictTableDef.SYS_USER_DICT.DICT_GROUP.eq(group));
      if (StringUtils.hasText(code)) {
        wrapper.and(SysUserDictTableDef.SYS_USER_DICT.DICT_CODE.eq(code));
      }
      return this.list(wrapper);
    } else {
      return Collections.emptyList();
    }
  }
}
