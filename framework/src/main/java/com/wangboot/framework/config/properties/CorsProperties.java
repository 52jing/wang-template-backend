package com.wangboot.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * CORS 配置属性
 *
 * @author wwtg99
 */
@Data
@ConfigurationProperties(prefix = CorsProperties.PREFIX, ignoreInvalidFields = true)
public class CorsProperties {
  public static final String PREFIX = "cors";

  /** 是否启用 */
  private boolean enabled = false;

  /** 允许的方法 */
  private String[] methods;

  /** 允许的来源 */
  private String[] origins;

  /** 允许的请求头 */
  private String[] headers;

  /** 暴露的请求头 */
  private String[] exposeHeaders;

  /** 是否允许 Credentials */
  private boolean allowCredentials = false;

  /** 缓存时间 */
  private long maxAge = 86400L;
}
