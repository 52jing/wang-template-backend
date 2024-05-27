package com.wangboot.framework.config;

import com.wangboot.starter.autoconfiguration.WbProperties;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步线程池配置
 *
 * @author wuwentao
 */
@Configuration
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

  private final WbProperties wbProperties;

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setThreadNamePrefix(wbProperties.getThreads().getPrefix());
    executor.setMaxPoolSize(wbProperties.getThreads().getMaxPoolSize());
    executor.setCorePoolSize(wbProperties.getThreads().getCorePoolSize());
    executor.setQueueCapacity(wbProperties.getThreads().getQueueCapacity());
    executor.setKeepAliveSeconds(wbProperties.getThreads().getKeepAliveSeconds());
    executor.setWaitForTasksToCompleteOnShutdown(true);
    // 拒绝处理策略：抛出异常
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
    executor.initialize();
    return executor;
  }

  @Generated
  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (ex, method, params) -> log.error("{} 发生异常：{}", method.getName(), ex.getMessage());
  }
}
