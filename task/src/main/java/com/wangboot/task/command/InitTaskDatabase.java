package com.wangboot.task.command;

import com.wangboot.core.web.param.IParamConfig;
import com.wangboot.model.entity.utils.DataPorter;
import com.wangboot.system.command.BaseInitDatabaseCommand;
import com.wangboot.system.command.InitSystemDatabase;
import com.wangboot.system.entity.SysMenu;
import com.wangboot.system.entity.SysParam;
import com.wangboot.system.entity.relation.SysPolicyMenuRel;
import com.wangboot.system.model.ParamType;
import com.wangboot.system.model.PermissionGenerator;
import com.wangboot.system.service.SysMenuService;
import com.wangboot.system.service.SysParamService;
import com.wangboot.system.service.SysPolicyMenuRelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
@Order(20)
@Slf4j
public class InitTaskDatabase extends BaseInitDatabaseCommand {

  private static final String INIT_PARAM_KEY = "init_task_data";

  @Getter private final IParamConfig paramConfig;

  private final SysMenuService menuService;

  private final SysPolicyMenuRelService policyMenuRelService;

  private final SysParamService paramService;

  private int syncMenu() {
    String quickLinkId = "quick_link";
    List<SysMenu> data =
        Collections.singletonList(
            new SysMenu(
                quickLinkId,
                "快捷菜单",
                "",
                "link",
                "/task/quick_link",
                InitSystemDatabase.MenuIds.DASHBOARD,
                1));
    DataPorter<String, SysMenu> porter = new DataPorter<>(data, menuService);
    int n = porter.syncData();
    // 添加策略菜单关系
    List<SysPolicyMenuRel> data2 =
        Arrays.asList(
            PermissionGenerator.createPolicyMenuRel(
                InitSystemDatabase.AdditionalPolicyIds.POLICY_MANAGE_MENU, quickLinkId),
            PermissionGenerator.createPolicyMenuRel(
                InitSystemDatabase.AdditionalPolicyIds.POLICY_COMMON_MENU, quickLinkId));
    DataPorter<String, SysPolicyMenuRel> porter2 = new DataPorter<>(data2, policyMenuRelService);
    porter2.syncData();
    return n;
  }

  private int syncParam() {
    List<SysParam> data =
        Collections.singletonList(
            new SysParam(
                "0-2",
                "Init Task Database",
                InitSystemDatabase.PARAM_GROUP_SYSTEM,
                INIT_PARAM_KEY,
                "0",
                ParamType.INT));
    DataPorter<String, SysParam> porter = new DataPorter<>(data, paramService);
    return porter.syncData();
  }

  @Override
  public String getModuleName() {
    return "任务";
  }

  @Override
  protected void init() {
    int n;
    List<String> msg = new ArrayList<>();
    n = this.syncMenu();
    msg.add(String.format("插入/更新 %s 条菜单数据", n));
    n = this.syncParam();
    msg.add(String.format("插入/更新 %s 条参数数据", n));
    msg.forEach(log::info);
  }

  @Override
  protected boolean notInitialized() {
    String init = this.getParamConfig().getParamConfig(INIT_PARAM_KEY);
    return !StringUtils.hasText(init) || "0".equals(init);
  }

  @Override
  protected void setInitialized() {
    this.getParamConfig().setParamConfig(INIT_PARAM_KEY, "1");
  }
}
