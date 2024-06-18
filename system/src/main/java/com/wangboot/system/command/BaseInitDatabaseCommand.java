package com.wangboot.system.command;

import com.wangboot.core.web.param.IParamConfig;
import com.wangboot.framework.command.IBaseCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;

@Slf4j
public abstract class BaseInitDatabaseCommand implements IBaseCommand {

  @Override
  public void callCommand(ApplicationArguments args) {
    log.info("启动{}模块数据初始化...", this.getModuleName());
    this.init();
    this.setInitialized();
    log.info("模块{}数据初始化完成", this.getModuleName());
  }

  @Override
  public boolean whetherToRun(ApplicationArguments args) {
    return !args.containsOption("without-init-data") && this.notInitialized();
  }

  public abstract IParamConfig getParamConfig();

  public abstract String getModuleName();

  /** 执行初始化 */
  protected abstract void init();

  /**
   * 是否未初始化
   *
   * @return boolean
   */
  protected abstract boolean notInitialized();

  /** 设置已初始化 */
  protected abstract void setInitialized();
}
