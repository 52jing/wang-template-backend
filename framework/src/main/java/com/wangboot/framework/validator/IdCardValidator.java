package com.wangboot.framework.validator;

import cn.hutool.core.util.IdcardUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

/**
 * 身份证验证类
 *
 * @author wuwentao
 */
public class IdCardValidator implements ConstraintValidator<IdCard, String> {
  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return !StringUtils.hasText(s) || IdcardUtil.isValidCard(s);
  }
}
