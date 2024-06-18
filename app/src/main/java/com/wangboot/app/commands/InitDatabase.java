package com.wangboot.app.commands;

import com.google.common.collect.Lists;
import com.wangboot.core.web.param.IParamConfig;
import com.wangboot.model.entity.utils.DataPorter;
import com.wangboot.system.command.BaseInitDatabaseCommand;
import com.wangboot.system.command.InitSystemDatabase;
import com.wangboot.system.entity.SysMenu;
import com.wangboot.system.entity.SysPermission;
import com.wangboot.system.entity.SysPolicy;
import com.wangboot.system.entity.SysRole;
import com.wangboot.system.entity.relation.SysPolicyMenuRel;
import com.wangboot.system.entity.relation.SysPolicyPermissionRel;
import com.wangboot.system.entity.relation.SysRolePolicyRel;
import com.wangboot.system.entity.relation.SysUserRoleRel;
import com.wangboot.system.model.PermissionGenerator;
import com.wangboot.system.service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 初始化数据<br>
 * 检查系统参数 init_database，如果未初始化则默认执行。<br>
 * 使用参数 --without-init-data 则不执行。
 */
@Slf4j
@RequiredArgsConstructor
@Order(30)
@Component
public class InitDatabase extends BaseInitDatabaseCommand {

  public static class MenuIds {
    public static final String RENDER = "render";
    public static final String DATASOURCE_MANAGEMENT = "datasource_management";
    public static final String TEMPLATE_MANAGEMENT = "template_management";
    public static final String RENDER_EXECUTION = "render_execution";
    public static final String ANALYSIS = "analysis";
  }

  public static class PolicyIds {
    public static final String RENDER_MENU = "render_menu";
    public static final String ANALYSIS_POLICY = "analysis_policy";
  }

  public static class RoleIds {
    public static final String RENDER = "4";
  }

  public static final String INIT_PARAM_KEY = "init_app_data";

  @Getter
  private final IParamConfig paramConfig;
  private final SysMenuService menuService;
  private final SysPermissionService permissionService;
  private final SysPolicyService policyService;
  private final SysPolicyPermissionRelService policyPermissionRelService;
  private final SysPolicyMenuRelService policyMenuRelService;
  private final SysRoleService roleService;
  private final SysRolePolicyRelService rolePolicyRelService;
  private final SysUserRoleRelService userRoleRelService;

  private List<PermissionGenerator> permissionGenerators;

  private int syncMenu() {
    List<SysMenu> data =
      Arrays.asList(
            new SysMenu(MenuIds.RENDER, "报告生成", "", "work", "", null, 2),
            new SysMenu(
                MenuIds.DATASOURCE_MANAGEMENT,
                "数据源管理",
                "",
                "dataset",
                "/template/datasource",
                "render",
                1),
            new SysMenu(
                MenuIds.TEMPLATE_MANAGEMENT, "模板管理", "", "article", "/template/template", "render", 2),
            new SysMenu(
                MenuIds.RENDER_EXECUTION,
                "文档生成",
                "",
                "assignment",
                "/template/render_execution",
                "render",
                3),
            new SysMenu(MenuIds.ANALYSIS, "文档分析", "", "fact_check", "/analysis", null, 3));
    DataPorter<String, SysMenu> porter = new DataPorter<>(data, menuService);
    return porter.syncData();
  }

  private void initPermissionGenerators() {
    this.permissionGenerators = new ArrayList<>();
    this.permissionGenerators.add(new PermissionGenerator("数据源", "template", "datasource"));
    this.permissionGenerators.add(new PermissionGenerator("模板", "template", "template"));
    this.permissionGenerators.add(new PermissionGenerator("渲染执行", "template", "render_execution"));
  }

  private int syncPermission() {
    int n = 0;
    // 添加权限
    List<SysPermission> data = new ArrayList<>();
    this.permissionGenerators.forEach(g -> data.addAll(g.generatePermissions()));
    // 添加其他权限
    String analysisUpload = "template:analysis:upload";
    data.add(new SysPermission(analysisUpload, analysisUpload, "分析上传权限", true));
    DataPorter<String, SysPermission> porter = new DataPorter<>(data, permissionService);
    int m = porter.syncData();
    n += m;
    // 添加策略
    List<SysPolicy> data2 = new ArrayList<>();
    this.permissionGenerators.forEach(g -> data2.addAll(g.generatePolicies()));
    // 添加其他策略
    data2.add(new SysPolicy(PolicyIds.RENDER_MENU, PolicyIds.RENDER_MENU, "报告生成菜单", true, null, null, null));
    data2.add(new SysPolicy(PolicyIds.ANALYSIS_POLICY, PolicyIds.ANALYSIS_POLICY, "报告分析策略", true, null, null, null));
    DataPorter<String, SysPolicy> porter2 = new DataPorter<>(data2, policyService);
    m = porter2.syncData();
    n += m;
    // 添加策略权限关系
    List<SysPolicyPermissionRel> data3 = new ArrayList<>();
    this.permissionGenerators.forEach(g -> data3.addAll(g.generatePolicyPermissionRel()));
    // 其他策略权限关系
    data3.add(
      PermissionGenerator.createPolicyPermissionRel(PolicyIds.ANALYSIS_POLICY, analysisUpload));
    DataPorter<String, SysPolicyPermissionRel> porter3 =
        new DataPorter<>(data3, policyPermissionRelService);
    porter3.syncData();
    // 添加策略菜单关系
    List<SysPolicyMenuRel> data4 =
        Arrays.asList(
          PermissionGenerator.createPolicyMenuRel(PolicyIds.RENDER_MENU, MenuIds.RENDER),
          PermissionGenerator.createPolicyMenuRel(PolicyIds.RENDER_MENU, MenuIds.DATASOURCE_MANAGEMENT),
          PermissionGenerator.createPolicyMenuRel(PolicyIds.RENDER_MENU, MenuIds.TEMPLATE_MANAGEMENT),
          PermissionGenerator.createPolicyMenuRel(PolicyIds.RENDER_MENU, MenuIds.RENDER_EXECUTION),
          PermissionGenerator.createPolicyMenuRel(PolicyIds.RENDER_MENU, MenuIds.ANALYSIS));
    DataPorter<String, SysPolicyMenuRel> porter4 = new DataPorter<>(data4, policyMenuRelService);
    porter4.syncData();
    // 添加角色
    List<SysRole> data5 =
        Lists.newArrayList(
            new SysRole(RoleIds.RENDER, "报告分析用户", null));
    DataPorter<String, SysRole> porter5 = new DataPorter<>(data5, roleService);
    m = porter5.syncData();
    n += m;
    // 添加角色策略关系
    List<SysRolePolicyRel> data6 = new ArrayList<>();
    // 系统管理员
    this.permissionGenerators.forEach(
      g ->
        data6.add(
          PermissionGenerator.createRolePolicyRel(InitSystemDatabase.RoleIds.SYSTEM_ID, g.getWritePolicyId())
          ));
    this.permissionGenerators.forEach(
      g ->
        data6.add(
          PermissionGenerator.createRolePolicyRel(InitSystemDatabase.RoleIds.SYSTEM_ID, g.getReadPolicyId())
          ));
    data6.add(PermissionGenerator.createRolePolicyRel(InitSystemDatabase.RoleIds.SYSTEM_ID, PolicyIds.RENDER_MENU));
    data6.add(PermissionGenerator.createRolePolicyRel(InitSystemDatabase.RoleIds.SYSTEM_ID, PolicyIds.ANALYSIS_POLICY));
    // 系统审计员
    this.permissionGenerators.forEach(
      g ->
        data6.add(
          PermissionGenerator.createRolePolicyRel(InitSystemDatabase.RoleIds.AUDIT_ID, g.getReadPolicyId())
          ));
    data6.add(PermissionGenerator.createRolePolicyRel(InitSystemDatabase.RoleIds.AUDIT_ID, PolicyIds.RENDER_MENU));
    data6.add(PermissionGenerator.createRolePolicyRel(InitSystemDatabase.RoleIds.AUDIT_ID, PolicyIds.ANALYSIS_POLICY));
    // 报告分析用户
    this.permissionGenerators.forEach(
      g ->
        data6.add(
          PermissionGenerator.createRolePolicyRel(RoleIds.RENDER, g.getWritePolicyId())
          ));
    this.permissionGenerators.forEach(
      g ->
        data6.add(
          PermissionGenerator.createRolePolicyRel(RoleIds.RENDER, g.getReadPolicyId())
          ));
    data6.add(PermissionGenerator.createRolePolicyRel(RoleIds.RENDER, PolicyIds.RENDER_MENU));
    data6.add(PermissionGenerator.createRolePolicyRel(RoleIds.RENDER, PolicyIds.ANALYSIS_POLICY));
    DataPorter<String, SysRolePolicyRel> porter6 = new DataPorter<>(data6, rolePolicyRelService);
    porter6.syncData();
    // 添加用户角色关系
    List<SysUserRoleRel> data7 =
        Lists.newArrayList(
            new SysUserRoleRel("render_role", "4", RoleIds.RENDER));
    DataPorter<String, SysUserRoleRel> porter7 = new DataPorter<>(data7, userRoleRelService);
    porter7.syncData();
    return n;
  }

  @Override
  public String getModuleName() {
    return "app";
  }

  @Override
  protected void init() {
    int n;
    List<String> msg = new ArrayList<>();
    n = this.syncMenu();
    msg.add(String.format("插入/更新 %s 条菜单数据", n));
    this.initPermissionGenerators();
    n = this.syncPermission();
    msg.add(String.format("插入/更新 %s 条权限数据", n));
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
