package com.wangboot.system.command;

import com.wangboot.core.web.param.IParamConfig;
import com.wangboot.framework.ParamConstants;
import com.wangboot.framework.command.IBaseCommand;
import com.wangboot.system.service.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.util.StringUtils;

@Slf4j
public abstract class BaseInitDatabaseCommand implements IBaseCommand {

  @Override
  public void callCommand(ApplicationArguments args) {
    if (this.isInitialized()) {
      return;
    }
    log.info("启动{}模块数据初始化...", this.getModuleName());
    this.init();
    this.setInitialized();
    log.info("数据初始化完成");
  }

  @Override
  public boolean whetherToRun(ApplicationArguments args) {
    return !args.containsOption("without-init-data");
  }

  public abstract IParamConfig getParamConfig();

  public abstract SysParamService getParamService();

  public abstract String getModuleName();

  protected abstract void init();

  protected boolean isInitialized() {
    String init = this.getParamConfig().getParamConfig(ParamConstants.INIT_DATABASE_KEY);
    return StringUtils.hasText(init) && init.equals("1");
  }

  protected void setInitialized() {
    this.getParamService()
        .updateChain()
        .eq("param_key", ParamConstants.INIT_DATABASE_KEY)
        .set("param_val", "1")
        .update();
  }
}
