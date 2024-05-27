package com.wangboot.framework.validator;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

/**
 * 手机号码验证类
 *
 * @author wuwentao
 */
public class TelephoneValidator implements ConstraintValidator<Telephone, String> {

  public static final String TEL_PATTERN = "^1[3-9]\\d{9}$";

  private Pattern pattern;

  @Override
  public void initialize(Telephone constraintAnnotation) {
    this.pattern = Pattern.compile(TEL_PATTERN);
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return !StringUtils.hasText(s) || this.pattern.matcher(s).matches();
  }
}
