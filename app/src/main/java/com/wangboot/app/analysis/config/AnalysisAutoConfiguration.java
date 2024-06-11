package com.wangboot.app.analysis.config;

import com.wangboot.app.analysis.MoonShotClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties({AnalysisProperties.class})
@RequiredArgsConstructor
public class AnalysisAutoConfiguration {

  private final AnalysisProperties analysisProperties;

  @Bean
  public MoonShotClient moonShotClient() {
    String baseUrl = StringUtils.hasText(analysisProperties.getMoonShot().getBaseUrl()) ? analysisProperties.getMoonShot().getBaseUrl() : MoonShotClient.MOONSHOT_BASE;
    String accessToken = analysisProperties.getMoonShot().getAccessToken();
    if (StringUtils.hasText(accessToken)) {
      return new MoonShotClient(baseUrl, accessToken, null);
    }
    return null;
  }
}
