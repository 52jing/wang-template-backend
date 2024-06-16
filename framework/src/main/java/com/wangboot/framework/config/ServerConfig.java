package com.wangboot.framework.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.wangboot.core.auth.AuthFlow;
import com.wangboot.core.auth.authentication.AuthenticationManager;
import com.wangboot.core.auth.authentication.IAuthenticator;
import com.wangboot.core.auth.authentication.provider.UsernamePasswordAuthenticationProvider;
import com.wangboot.core.auth.authorization.IAuthorizerService;
import com.wangboot.core.auth.frontend.FrontendManager;
import com.wangboot.core.auth.frontend.IFrontendService;
import com.wangboot.core.auth.frontend.provider.SimpleFrontendServiceProvider;
import com.wangboot.core.auth.middleware.filter.AccessTokenTypeFilter;
import com.wangboot.core.auth.middleware.filter.BlacklistFilter;
import com.wangboot.core.auth.middleware.filter.LoginRestrictionFilter;
import com.wangboot.core.auth.middleware.filter.UserStatusFilter;
import com.wangboot.core.auth.middleware.generatetoken.LoginRestrictionGuard;
import com.wangboot.core.auth.middleware.login.CaptchaValidation;
import com.wangboot.core.auth.middleware.login.LoginFailedLock;
import com.wangboot.core.auth.middleware.login.StaffOnlyCheck;
import com.wangboot.core.auth.middleware.logout.BlacklistHandler;
import com.wangboot.core.auth.middleware.logout.UserTokenValidation;
import com.wangboot.core.auth.middleware.refreshtoken.BlacklistValidation;
import com.wangboot.core.auth.middleware.refreshtoken.RefreshTokenTypeCheck;
import com.wangboot.core.auth.security.LoginRestriction;
import com.wangboot.core.auth.token.ITokenManager;
import com.wangboot.core.auth.user.IUserService;
import com.wangboot.core.event.IEventBus;
import com.wangboot.core.reliability.blacklist.IBlacklistHolder;
import com.wangboot.core.web.interceptor.AuthenticationInterceptor;
import com.wangboot.core.web.interceptor.InMaintenanceInterceptor;
import com.wangboot.core.web.interceptor.PermissionInterceptor;
import com.wangboot.core.web.interceptor.RequestRecordInterceptor;
import com.wangboot.model.attachment.exporter.ExcelExporter;
import com.wangboot.model.attachment.exporter.IExporter;
import com.wangboot.starter.autoconfiguration.WbProperties;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ServerConfig {

  private final WbProperties wbProperties;

  /** 登录验证管理器 */
  @Bean
  public AuthenticationManager authenticationManager(
      IUserService userService, PasswordEncoder passwordEncoder) {
    AuthenticationManager authenticationManager = new AuthenticationManager();
    // 添加用户名密码认证提供者
    authenticationManager.addProvider(
        "username_password",
        new UsernamePasswordAuthenticationProvider(userService, passwordEncoder));
    return authenticationManager;
  }

  /** 前端验证管理器 */
  @Bean
  public FrontendManager frontendManager(IFrontendService frontendService) {
    FrontendManager frontendManager = new FrontendManager();
    // 添加简单前端服务提供者
    frontendManager.addProvider(new SimpleFrontendServiceProvider(frontendService));
    return frontendManager;
  }

  /** 认证流程管理器 */
  @Bean
  public AuthFlow authFlow(
      AuthenticationManager authenticationManager,
      FrontendManager frontendManager,
      IUserService userService,
      IFrontendService frontendService,
      IAuthorizerService authorizerService,
      ITokenManager tokenManager,
      IAuthenticator authenticator,
      IEventBus eventBus,
      IBlacklistHolder blacklistHolder,
      LoginRestriction loginRestriction) {
    AuthFlow authFlow =
        new AuthFlow(
            authenticationManager,
            frontendManager,
            tokenManager,
            userService,
            frontendService,
            authorizerService,
            authenticator,
            eventBus);
    // 登录中间件
    authFlow.addLoginMiddleware(new StaffOnlyCheck());
    if (wbProperties.getCaptcha().isEnabled()) {
      authFlow.addLoginMiddleware(new CaptchaValidation(new String[] {"image"}));
    }
    authFlow.addLoginMiddleware(new LoginFailedLock(userService));
    // 登出中间件
    authFlow.addLogoutMiddleware(new UserTokenValidation(tokenManager));
    authFlow.addLogoutMiddleware(new BlacklistHandler(blacklistHolder));
    // 刷新令牌中间件
    authFlow.addRefreshTokenMiddleware(new RefreshTokenTypeCheck(tokenManager));
    authFlow.addRefreshTokenMiddleware(
        new BlacklistValidation(blacklistHolder, wbProperties.getAuth().getRefreshExpires()));
    // 生成令牌中间件
    authFlow.addGenerateTokenMiddleware(
        new LoginRestrictionGuard(
            wbProperties.getAuth().getLoginRestrictionStrategy(), loginRestriction));
    // 认证中间件
    authFlow.addFilterMiddleware(new AccessTokenTypeFilter());
    authFlow.addFilterMiddleware(new BlacklistFilter(blacklistHolder));
    authFlow.addFilterMiddleware(new UserStatusFilter());
    authFlow.addFilterMiddleware(
        new LoginRestrictionFilter(
            wbProperties.getAuth().getLoginRestrictionStrategy(), loginRestriction));
    return authFlow;
  }

  /** 认证拦截器 */
  @Bean
  public AuthenticationInterceptor authenticationInterceptor(
      AuthFlow authFlow, IEventBus eventBus) {
    AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authFlow, eventBus);
    interceptor.setTokenType(wbProperties.getAuth().getType());
    return interceptor;
  }

  /** 权限拦截器 */
  @Bean
  public PermissionInterceptor permissionInterceptor(IEventBus eventBus) {
    return new PermissionInterceptor(eventBus);
  }

  /** 请求记录拦截器 */
  @Bean
  public RequestRecordInterceptor requestRecordInterceptor(IEventBus eventBus) {
    return new RequestRecordInterceptor(eventBus);
  }

  /** 维护拦截器 */
  @Bean
  public InMaintenanceInterceptor inMaintenanceInterceptor() {
    return new InMaintenanceInterceptor(
        wbProperties.isInMaintenance(), wbProperties.getMaintenanceNotice());
  }

  /** 导出器 */
  @Bean
  public IExporter exporter() {
    return new ExcelExporter("export");
  }

  /** Jackson 配置 */
  @Bean
  @ConditionalOnMissingBean(Jackson2ObjectMapperBuilderCustomizer.class)
  public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
    return jacksonObjectMapperBuilder -> {
      jacksonObjectMapperBuilder
          .timeZone(wbProperties.getTimezone())
          .simpleDateFormat(wbProperties.getDateFormat())
          .featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
      JavaTimeModule javaTimeModule = new JavaTimeModule();
      javaTimeModule.addSerializer(
          ZonedDateTime.class,
          new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern(wbProperties.getDateFormat())));
      jacksonObjectMapperBuilder.modules(javaTimeModule);
    };
  }
}
