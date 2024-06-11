package com.wangboot.app.analysis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = AnalysisProperties.PREFIX, ignoreInvalidFields = true)
public class AnalysisProperties {
  public static final String PREFIX = "analysis";

  @NestedConfigurationProperty
  private MoonShot moonShot;
  private boolean indicatorAnalysis = false;

  @Data
  static class MoonShot {
    private String baseUrl;
    private String accessToken;
  }
}
