package com.wangboot.framework.config;

import com.wangboot.core.web.interceptor.AuthenticationInterceptor;
import com.wangboot.core.web.interceptor.InMaintenanceInterceptor;
import com.wangboot.core.web.interceptor.PermissionInterceptor;
import com.wangboot.core.web.interceptor.RequestRecordInterceptor;
import com.wangboot.framework.config.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 通用配置
 *
 * @author wwtg99
 */
@Configuration
@EnableConfigurationProperties({CorsProperties.class})
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final CorsProperties corsProperties;

  private final RequestRecordInterceptor requestRecordInterceptor;

  private final AuthenticationInterceptor authenticationInterceptor;

  private final PermissionInterceptor permissionInterceptor;

  private final InMaintenanceInterceptor inMaintenanceInterceptor;

  /** CORS 配置 */
  @Override
  public void addCorsMappings(@NonNull CorsRegistry registry) {
    if (corsProperties.isEnabled()) {
      registry
          .addMapping("/**")
          .allowedMethods(corsProperties.getMethods())
          .allowedOrigins(corsProperties.getOrigins())
          .allowedHeaders(corsProperties.getHeaders())
          .exposedHeaders(corsProperties.getExposeHeaders())
          .maxAge(corsProperties.getMaxAge())
          .allowCredentials(corsProperties.isAllowCredentials());
    }
  }

  //  @Override
  //  public void addResourceHandlers(ResourceHandlerRegistry registry) {
  //    // swagger配置
  //    registry.addResourceHandler("/swagger-ui/**");
  //  }

  /** 拦截器配置 */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 请求记录
    registry.addInterceptor(requestRecordInterceptor).addPathPatterns("/**");
    // 维护
    registry.addInterceptor(inMaintenanceInterceptor).addPathPatterns("/**");
    // 认证拦截器
    registry
        .addInterceptor(authenticationInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns(
            // 认证
            "/login",
            "/refresh_token",
            "/register",
            "/captcha/**",
            // 通用
            "/error",
            "/request/info",
            "/system_dict/*",
            "/user_dict",
            "/user_dict/*",
            "/frontend/*",
            // druid
            "/druid/**",
            "/druid_stat",
            // swagger
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-resources",
            "/v3/api-docs/**",
            // actuator
            "/actuator/**");
    // 权限拦截器
    registry.addInterceptor(permissionInterceptor).addPathPatterns("/**");
  }
}
