package com.wangboot.app;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author wwtg99
 */
@SpringBootApplication(
    exclude = {
      DataSourceAutoConfiguration.class,
      SecurityAutoConfiguration.class,
      ManagementWebSecurityAutoConfiguration.class
    })
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 启用缓存
@EnableCaching
// 启用计划任务
@EnableScheduling
// 启用存储
@EnableFileStorage
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.wangboot.**.mapper")
// 指定要扫描的组件的包的路径
@ComponentScan("com.wangboot.**")
public class Application {
  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    Environment env = context.getEnvironment();
    String port = env.getProperty("server.port");
    String path = env.getProperty("server.servlet.context-path");
    System.out.println("^O^ 后台服务启动成功 o(^@^)o");
    System.out.println("服务地址：http://localhost:" + port + path);
  }
}
