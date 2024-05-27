package com.wangboot.framework.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisFlex 配置
 *
 * @author wuwentao
 */
@Configuration
public class MyBatisFlexConfiguration implements MyBatisFlexCustomizer {
  @Override
  public void customize(FlexGlobalConfig flexGlobalConfig) {
    // TODO 添加自定义配置
  }
}
