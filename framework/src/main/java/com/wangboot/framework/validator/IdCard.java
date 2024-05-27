package com.wangboot.framework.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 身份证验证注解
 *
 * @author wuwentao
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdCardValidator.class)
public @interface IdCard {
  String message() default "message.invalid_idcard_format";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
