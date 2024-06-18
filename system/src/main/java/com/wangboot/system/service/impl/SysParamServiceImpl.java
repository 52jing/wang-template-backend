package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.system.entity.SysParam;
import com.wangboot.system.entity.table.SysParamTableDef;
import com.wangboot.system.mapper.SysParamMapper;
import com.wangboot.system.service.SysParamService;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 服务层实现。
 *
 * @author wwtg99
 */
@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParam>
    implements SysParamService {

  @Override
  public void setParamConfig(String key, String value) {
    this.updateChain()
        .where(SysParamTableDef.SYS_PARAM.PARAM_KEY.eq(key))
        .set(SysParamTableDef.SYS_PARAM.PARAM_VAL, value)
        .update();
  }

  @Override
  public String getParamConfig(String key) {
    if (!StringUtils.hasText(key)) {
      return "";
    }
    return Optional.ofNullable(
            this.getOne(query().where(SysParamTableDef.SYS_PARAM.PARAM_KEY.eq(key))))
        .map(SysParam::getParamVal)
        .orElse("");
  }
}
