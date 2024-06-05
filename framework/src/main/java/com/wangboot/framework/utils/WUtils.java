package com.wangboot.framework.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.wangboot.core.reliability.counter.CacheCounter;
import com.wangboot.core.reliability.counter.ICounter;
import com.wangboot.core.utils.StrUtils;
import com.wangboot.core.utils.password.PasswordStrategyManager;
import com.wangboot.core.utils.password.strategy.MinimumLength;
import com.wangboot.core.utils.password.strategy.MultiPattern;
import com.wangboot.core.web.param.IParamConfig;
import com.wangboot.framework.ParamConstants;
import java.util.Objects;
import org.springframework.lang.NonNull;

public class WUtils {

  private WUtils() {}

  /** 获取密码策略管理器 */
  @NonNull
  public static PasswordStrategyManager getPasswordStrategy() {
    IParamConfig paramConfig = SpringUtil.getBean(IParamConfig.class);
    PasswordStrategyManager passwordStrategyManager =
        SpringUtil.getBean(PasswordStrategyManager.class);
    passwordStrategyManager.clearStrategies();
    Integer minLength =
        StrUtils.getInteger(paramConfig.getParamConfig(ParamConstants.PASSWORD_MIN_LENGTH_KEY));
    if (Objects.nonNull(minLength)) {
      passwordStrategyManager.addPasswordStrategy(new MinimumLength(minLength));
    }
    int pattern = 0;
    boolean requireNumber =
        StrUtils.getBooleanPrimitive(
            paramConfig.getParamConfig(ParamConstants.PASSWORD_REQUIRE_NUMBER_KEY), false);
    boolean requireUpper =
        StrUtils.getBooleanPrimitive(
            paramConfig.getParamConfig(ParamConstants.PASSWORD_REQUIRE_UPPER_KEY), false);
    boolean requireLower =
        StrUtils.getBooleanPrimitive(
            paramConfig.getParamConfig(ParamConstants.PASSWORD_REQUIRE_LOWER_KEY), false);
    boolean requireSymbol =
        StrUtils.getBooleanPrimitive(
            paramConfig.getParamConfig(ParamConstants.PASSWORD_REQUIRE_SYMBOL_KEY), false);
    if (requireNumber) {
      pattern |= MultiPattern.NUMBER_PATTERN;
    }
    if (requireUpper) {
      pattern |= MultiPattern.UPPER_CASE_PATTERN;
    }
    if (requireLower) {
      pattern |= MultiPattern.LOWER_CASE_PATTERN;
    }
    if (requireSymbol) {
      pattern |= MultiPattern.SYMBOL_PATTERN;
    }
    if (pattern > 0) {
      passwordStrategyManager.addPasswordStrategy(new MultiPattern(pattern));
    }
    return passwordStrategyManager;
  }

  /** 获取登录失败计数器 */
  @NonNull
  public static ICounter getLoginFailedCounter() {
    IParamConfig paramConfig = SpringUtil.getBean(IParamConfig.class);
    return new CacheCounter(
        StrUtils.getLong(
            paramConfig.getParamConfig(ParamConstants.LOGIN_FAILED_COUNT_SECONDS_KEY), 60L));
  }
}
