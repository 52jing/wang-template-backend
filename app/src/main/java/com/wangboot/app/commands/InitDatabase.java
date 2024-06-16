package com.wangboot.app.commands;

import com.google.common.collect.Lists;
import com.wangboot.core.auth.authorization.resource.ApiResource;
import com.wangboot.core.web.param.IParamConfig;
import com.wangboot.framework.ParamConstants;
import com.wangboot.framework.command.IBaseCommand;
import com.wangboot.model.entity.utils.DataPorter;
import com.wangboot.system.command.BaseInitDatabaseCommand;
import com.wangboot.system.entity.*;
import com.wangboot.system.entity.relation.SysPolicyMenuRel;
import com.wangboot.system.entity.relation.SysPolicyPermissionRel;
import com.wangboot.system.entity.relation.SysRolePolicyRel;
import com.wangboot.system.entity.relation.SysUserRoleRel;
import com.wangboot.system.model.AnnouncementType;
import com.wangboot.system.model.ClientType;
import com.wangboot.system.model.ParamType;
import com.wangboot.system.model.PermissionGenerator;
import com.wangboot.system.service.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 初始化数据<br>
 * 检查系统参数 init_database，如果未初始化则默认执行。<br>
 * 使用参数 --without-init-data 则不执行。
 */
@Slf4j
@RequiredArgsConstructor
@Order(20)
@Component
public class InitDatabase extends BaseInitDatabaseCommand {

  private final IParamConfig paramConfig;
  private final SysUserService userService;
  private final SysDepartmentService departmentService;
  private final SysFrontendService frontendService;
  private final SysJobService jobService;
  private final SysMenuService menuService;
  private final SysParamService paramService;
  private final SysUserDictService userDictService;
  private final SysAnnouncementService announcementService;
  private final SysPermissionService permissionService;
  private final SysPolicyService policyService;
  private final SysPolicyPermissionRelService policyPermissionRelService;
  private final SysPolicyMenuRelService policyMenuRelService;
  private final SysRoleService roleService;
  private final SysRolePolicyRelService rolePolicyRelService;
  private final SysUserRoleRelService userRoleRelService;
  private final PasswordEncoder passwordEncoder;

  private List<PermissionGenerator> permissionGenerators;

  private int syncMenu() {
    List<SysMenu> data =
      Arrays.asList(
            new SysMenu("render", "报告生成", "", "work", "", null, 2),
            new SysMenu(
                "datasource_management",
                "数据源管理",
                "",
                "dataset",
                "/template/datasource",
                "render",
                1),
            new SysMenu(
                "template_management", "模板管理", "", "article", "/template/template", "render", 2),
            new SysMenu(
                "render_execution",
                "文档生成",
                "",
                "assignment",
                "/template/render_execution",
                "render",
                3),
            new SysMenu("analysis", "文档分析", "", "fact_check", "/analysis", null, 3));
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
    String renderMenuPolicy = "render_menu";
    String analysisPolicy = "analysis_policy";
    data2.add(new SysPolicy(renderMenuPolicy, renderMenuPolicy, "报告生成菜单", true, null, null, null));
    data2.add(new SysPolicy(analysisPolicy, analysisPolicy, "报告分析策略", true, null, null, null));
    DataPorter<String, SysPolicy> porter2 = new DataPorter<>(data2, policyService);
    m = porter2.syncData();
    n += m;
    // 添加策略权限关系
    List<SysPolicyPermissionRel> data3 = new ArrayList<>();
    this.permissionGenerators.forEach(g -> data3.addAll(g.generatePolicyPermissionRel()));
    // 其他策略权限关系
    data3.add(
      new SysPolicyPermissionRel(
        analysisPolicy + PermissionGenerator.DEL_UNDERSCORE + analysisUpload,
        analysisPolicy,
        analysisUpload));
    DataPorter<String, SysPolicyPermissionRel> porter3 =
        new DataPorter<>(data3, policyPermissionRelService);
    porter3.syncData();
    // 添加策略菜单关系
    List<SysPolicyMenuRel> data4 =
        Arrays.asList(
            new SysPolicyMenuRel(renderMenuPolicy + "_render", renderMenuPolicy, "render"),
            new SysPolicyMenuRel(
              renderMenuPolicy + "_datasource_management", renderMenuPolicy, "datasource_management"),
            new SysPolicyMenuRel(
              renderMenuPolicy + "_template_management", renderMenuPolicy, "template_management"),
            new SysPolicyMenuRel(
              renderMenuPolicy + "_render_execution", renderMenuPolicy, "render_execution"),
          new SysPolicyMenuRel(
            renderMenuPolicy + "_analysis", renderMenuPolicy, "analysis"));
    DataPorter<String, SysPolicyMenuRel> porter4 = new DataPorter<>(data4, policyMenuRelService);
    porter4.syncData();
    // 添加角色
    final String roleIdRender = "4";
    List<SysRole> data5 =
        Lists.newArrayList(
            new SysRole(roleIdRender, "报告分析用户", null));
    DataPorter<String, SysRole> porter5 = new DataPorter<>(data5, roleService);
    m = porter5.syncData();
    n += m;
    // 添加角色策略关系
    final String roleIdSystem = "1";
    final String roleIdAudit = "2";
    List<SysRolePolicyRel> data6 = new ArrayList<>();
    // 系统管理员
    final String prefixSystem = "system_";
    this.permissionGenerators.forEach(
      g ->
        data6.add(
          new SysRolePolicyRel(
            prefixSystem + g.getWritePolicyId(), roleIdSystem, g.getWritePolicyId())));
    this.permissionGenerators.forEach(
      g ->
        data6.add(
          new SysRolePolicyRel(
            prefixSystem + g.getReadPolicyId(), roleIdSystem, g.getReadPolicyId())));
    data6.add(new SysRolePolicyRel(prefixSystem + renderMenuPolicy, roleIdSystem, renderMenuPolicy));
    data6.add(new SysRolePolicyRel(prefixSystem + analysisPolicy, roleIdSystem, analysisPolicy));
    // 系统审计员
    final String prefixAudit = "audit_";
    this.permissionGenerators.forEach(
      g ->
        data6.add(
          new SysRolePolicyRel(
            prefixAudit + g.getReadPolicyId(), roleIdAudit, g.getReadPolicyId())));
    data6.add(new SysRolePolicyRel(prefixAudit + renderMenuPolicy, roleIdAudit, renderMenuPolicy));
    data6.add(new SysRolePolicyRel(prefixAudit + analysisPolicy, roleIdAudit, analysisPolicy));
    // 报告分析用户
    final String prefixRender = "render_";
    this.permissionGenerators.forEach(
      g ->
        data6.add(
          new SysRolePolicyRel(
            prefixRender + g.getWritePolicyId(), roleIdRender, g.getWritePolicyId())));
    this.permissionGenerators.forEach(
      g ->
        data6.add(
          new SysRolePolicyRel(
            prefixRender + g.getReadPolicyId(), roleIdRender, g.getReadPolicyId())));
    data6.add(new SysRolePolicyRel(prefixRender + renderMenuPolicy, roleIdRender, renderMenuPolicy));
    data6.add(new SysRolePolicyRel(prefixRender + analysisPolicy, roleIdRender, analysisPolicy));
    DataPorter<String, SysRolePolicyRel> porter6 = new DataPorter<>(data6, rolePolicyRelService);
    porter6.syncData();
    // 添加用户角色关系
    List<SysUserRoleRel> data7 =
        Lists.newArrayList(
            new SysUserRoleRel("render_role", "4", roleIdRender));
    DataPorter<String, SysUserRoleRel> porter7 = new DataPorter<>(data7, userRoleRelService);
    porter7.syncData();
    return n;
  }


  @Override
  public IParamConfig getParamConfig() {
    return this.paramConfig;
  }

  @Override
  public SysParamService getParamService() {
    return this.paramService;
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
  protected boolean isInitialized() {
    return false;
  }
}
