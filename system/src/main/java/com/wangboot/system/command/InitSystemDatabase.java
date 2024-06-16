package com.wangboot.system.command;

import com.wangboot.core.web.param.IParamConfig;
import com.wangboot.framework.ParamConstants;
import com.wangboot.model.entity.utils.DataPorter;
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
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 初始化模块数据<br>
 * 检查系统参数 init_database，如果未初始化则默认执行。<br>
 * 使用参数 --without-init-data 则不执行。
 */
@RequiredArgsConstructor
@Component
@Order(10)
@Slf4j
public class InitSystemDatabase extends BaseInitDatabaseCommand {

  @Getter private final IParamConfig paramConfig;
  private final SysUserService userService;
  private final SysDepartmentService departmentService;
  private final SysFrontendService frontendService;
  private final SysJobService jobService;
  private final SysMenuService menuService;
  @Getter private final SysParamService paramService;
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

  private static final String DEFAULTP = "123456";

  private List<PermissionGenerator> permissionGenerators;

  private int syncUser() {
    List<SysUser> data =
        Arrays.asList(
            new SysUser(
                "1",
                "admin",
                "Admin",
                passwordEncoder.encode(DEFAULTP),
                "",
                "",
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                "",
                "",
                "",
                "",
                "",
                false),
            new SysUser(
                "2",
                "manager",
                "Manager",
                passwordEncoder.encode(DEFAULTP),
                "",
                "",
                true,
                false,
                true,
                null,
                null,
                null,
                null,
                "",
                "",
                "",
                "",
                "",
                false),
            new SysUser(
                "3",
                "reporter",
                "Reporter",
                passwordEncoder.encode(DEFAULTP),
                "",
                "",
                true,
                false,
                true,
                null,
                null,
                null,
                null,
                "",
                "",
                "",
                "",
                "",
                false),
            new SysUser(
                "4",
                "guest",
                "Guest",
                passwordEncoder.encode(DEFAULTP),
                "",
                "",
                true,
                false,
                true,
                null,
                null,
                null,
                null,
                "",
                "",
                "",
                "",
                "",
                false));
    DataPorter<String, SysUser> porter = new DataPorter<>(data, userService);
    return porter.syncData();
  }

  private int syncDepartment() {
    List<SysDepartment> data =
        Collections.singletonList(new SysDepartment("1", "Default", "Default", ""));
    DataPorter<String, SysDepartment> porter = new DataPorter<>(data, departmentService);
    return porter.syncData();
  }

  private int syncFrontend() {
    List<SysFrontend> data =
        Collections.singletonList(
            new SysFrontend(
                "99",
                "Web Admin",
                "My Administration Platform",
                "author",
                "",
                ClientType.WEB_ADMIN,
                false,
                true));
    DataPorter<String, SysFrontend> porter = new DataPorter<>(data, frontendService);
    return porter.syncData();
  }

  private int syncJob() {
    List<SysJob> data =
        Arrays.asList(
            new SysJob("1", "CEO", "管理"), new SysJob("2", "主管", "管理"), new SysJob("3", "专员", "员工"));
    DataPorter<String, SysJob> porter = new DataPorter<>(data, jobService);
    return porter.syncData();
  }

  private int syncMenu() {
    List<SysMenu> data =
        Arrays.asList(
            new SysMenu("home", "首页", "", "home", "/", null, 1),
            new SysMenu("dashboard", "工作台", "", "dashboard", "", null, 10),
            new SysMenu("quick_link", "快捷菜单", "", "link", "/task/quick_link", "dashboard", 1),
            new SysMenu("organization", "机构管理", "用户及部门管理", "corporate_fare", "", null, 20),
            new SysMenu(
                "user_management",
                "用户管理",
                "",
                "room_preferences",
                "/system/user",
                "organization",
                1),
            new SysMenu(
                "department_management",
                "部门管理",
                "",
                "home",
                "/system/department",
                "organization",
                2),
            new SysMenu("job_management", "岗位管理", "", "work", "/system/job", "organization", 3),
            new SysMenu("permission", "权限管理", "角色策略权限管理", "perm_data_setting", "", null, 30),
            new SysMenu(
                "role_management", "角色管理", "", "perm_identity", "/system/role", "permission", 1),
            new SysMenu(
                "policy_management", "策略管理", "", "policy", "/system/policy", "permission", 2),
            new SysMenu(
                "permission_management",
                "权限管理",
                "",
                "verified_user",
                "/system/permission",
                "permission",
                3),
            new SysMenu(
                "data_scope_management",
                "数据权限管理",
                "",
                "dataset",
                "/system/data_scope",
                "permission",
                4),
            new SysMenu("system", "系统管理", "菜单配置管理", "desktop_windows", "", null, 40),
            new SysMenu("menu_management", "菜单管理", "", "menu", "/system/menu", "system", 1),
            new SysMenu("frontend_management", "应用管理", "", "apps", "/system/frontend", "system", 2),
            new SysMenu(
                "announcement_management",
                "公告管理",
                "",
                "announcement",
                "/system/announcement",
                "system",
                3),
            new SysMenu(
                "user_dict_management",
                "用户字典管理",
                "",
                "bookmark_border",
                "/system/user_dict",
                "system",
                4),
            new SysMenu("support", "运维审计", "日志操作审计", "manage_history", "", null, 50),
            new SysMenu("user_log", "用户日志", "", "event", "/log/user_log", "support", 1),
            new SysMenu("operation_log", "操作日志", "", "source", "/log/operation_log", "support", 2));
    DataPorter<String, SysMenu> porter = new DataPorter<>(data, menuService);
    return porter.syncData();
  }

  private int syncParam() {
    List<SysParam> data =
        Arrays.asList(
            new SysParam(
                "0",
                "Init Database",
                "system",
                ParamConstants.INIT_DATABASE_KEY,
                "0",
                ParamType.BOOL),
            new SysParam("1-1", "服务", "server", "", "", ParamType.STR),
            new SysParam(
                "2-1",
                "密码最短长度",
                "security",
                ParamConstants.PASSWORD_MIN_LENGTH_KEY,
                "6",
                ParamType.INT,
                "0 则不限制"),
            new SysParam(
                "2-2",
                "密码必须包含小写字母",
                "security",
                ParamConstants.PASSWORD_REQUIRE_LOWER_KEY,
                "false",
                ParamType.BOOL),
            new SysParam(
                "2-3",
                "密码必须包含大写字母",
                "security",
                ParamConstants.PASSWORD_REQUIRE_UPPER_KEY,
                "false",
                ParamType.BOOL),
            new SysParam(
                "2-4",
                "密码必须包含数字",
                "security",
                ParamConstants.PASSWORD_REQUIRE_NUMBER_KEY,
                "false",
                ParamType.BOOL),
            new SysParam(
                "2-5",
                "密码必须包含特殊字符",
                "security",
                ParamConstants.PASSWORD_REQUIRE_SYMBOL_KEY,
                "false",
                ParamType.BOOL),
            new SysParam(
                "2-6",
                "登录失败锁定次数",
                "security",
                ParamConstants.LOGIN_FAILED_THRESHOLD_KEY,
                "0",
                ParamType.INT,
                "0 则不限制"),
            new SysParam(
                "2-7",
                "登录失败锁定时间",
                "security",
                ParamConstants.LOGIN_FAILED_LOCK_SECONDS_KEY,
                "300",
                ParamType.INT,
                "单位：秒"));
    DataPorter<String, SysParam> porter = new DataPorter<>(data, paramService);
    return porter.syncData();
  }

  private int syncUserDict() {
    List<SysUserDict> data =
        Arrays.asList(
            new SysUserDict("sex-male", "性别-男", "sex", "male", "男", ParamType.STR, 0),
            new SysUserDict("sex-female", "性别-女", "sex", "female", "女", ParamType.STR, 1));

    DataPorter<String, SysUserDict> porter = new DataPorter<>(data, userDictService);
    return porter.syncData();
  }

  private int syncAnnouncement() {
    List<SysAnnouncement> data =
        Collections.singletonList(
            new SysAnnouncement(
                "1", "欢迎使用 WangBoot！", "WangBoot", AnnouncementType.IMPORTANT, true, 1, null));
    DataPorter<String, SysAnnouncement> porter = new DataPorter<>(data, announcementService);
    return porter.syncData();
  }

  private void initPermissionGenerators() {
    this.permissionGenerators = new ArrayList<>();
    this.permissionGenerators.add(new PermissionGenerator("公告", "system", "announcement"));
    this.permissionGenerators.add(new PermissionGenerator("数据范围", "system", "data_scope"));
    this.permissionGenerators.add(new PermissionGenerator("部门", "system", "department"));
    this.permissionGenerators.add(new PermissionGenerator("前端", "system", "frontend"));
    this.permissionGenerators.add(new PermissionGenerator("岗位", "system", "job"));
    this.permissionGenerators.add(new PermissionGenerator("菜单", "system", "menu"));
    this.permissionGenerators.add(
        new PermissionGenerator("操作日志", "log", "operation_log", true, false, true));
    this.permissionGenerators.add(new PermissionGenerator("参数", "system", "param"));
    this.permissionGenerators.add(new PermissionGenerator("权限", "system", "permission"));
    this.permissionGenerators.add(new PermissionGenerator("策略", "system", "policy"));
    this.permissionGenerators.add(new PermissionGenerator("角色", "system", "role"));
    this.permissionGenerators.add(new PermissionGenerator("用户", "system", "user"));
    this.permissionGenerators.add(new PermissionGenerator("用户字典", "system", "user_dict"));
    this.permissionGenerators.add(
        new PermissionGenerator("用户日志", "log", "user_log", true, false, true));
  }

  private int syncPermission() {
    int n = 0;
    // 添加权限
    List<SysPermission> data = new ArrayList<>();
    this.permissionGenerators.forEach(g -> data.addAll(g.generatePermissions()));
    // 添加其他权限
    String attachmentUpload = "common:attachment:upload";
    String attachmentView = "common:attachment:view";
    String attachmentDownload = "common:attachment:download";
    data.add(new SysPermission(attachmentUpload, attachmentUpload, "上传文件权限", true));
    data.add(new SysPermission(attachmentView, attachmentView, "查看文件权限", true));
    data.add(new SysPermission(attachmentDownload, attachmentDownload, "下载文件权限", true));
    DataPorter<String, SysPermission> porter = new DataPorter<>(data, permissionService);
    int m = porter.syncData();
    n += m;
    // 添加策略
    List<SysPolicy> data2 = new ArrayList<>();
    this.permissionGenerators.forEach(g -> data2.addAll(g.generatePolicies()));
    // 添加其他策略
    String policyManageMenu = "manage_menu";
    String policyCommonMenu = "common_menu";
    String policyFile = "common_file";
    data2.add(new SysPolicy(policyManageMenu, policyManageMenu, "管理功能菜单", true, null, null, null));
    data2.add(new SysPolicy(policyCommonMenu, policyCommonMenu, "通用功能菜单", true, null, null, null));
    data2.add(new SysPolicy(policyFile, policyFile, "通用文件策略", true, null, null, null));
    DataPorter<String, SysPolicy> porter2 = new DataPorter<>(data2, policyService);
    m = porter2.syncData();
    n += m;
    // 添加策略权限关系
    List<SysPolicyPermissionRel> data3 = new ArrayList<>();
    this.permissionGenerators.forEach(g -> data3.addAll(g.generatePolicyPermissionRel()));
    // 其他策略权限关系
    data3.add(
        new SysPolicyPermissionRel(
            policyFile + PermissionGenerator.DEL_UNDERSCORE + attachmentUpload,
            policyFile,
            attachmentUpload));
    data3.add(
        new SysPolicyPermissionRel(
            policyFile + PermissionGenerator.DEL_UNDERSCORE + attachmentView,
            policyFile,
            attachmentView));
    data3.add(
        new SysPolicyPermissionRel(
            policyFile + PermissionGenerator.DEL_UNDERSCORE + attachmentDownload,
            policyFile,
            attachmentDownload));
    DataPorter<String, SysPolicyPermissionRel> porter3 =
        new DataPorter<>(data3, policyPermissionRelService);
    porter3.syncData();
    // 添加策略菜单关系
    List<SysPolicyMenuRel> data4 =
        Arrays.asList(
            new SysPolicyMenuRel(policyManageMenu + "_home", policyManageMenu, "home"),
            new SysPolicyMenuRel(policyManageMenu + "_dashboard", policyManageMenu, "dashboard"),
            new SysPolicyMenuRel(policyManageMenu + "_quick_link", policyManageMenu, "quick_link"),
            new SysPolicyMenuRel(
                policyManageMenu + "_organization", policyManageMenu, "organization"),
            new SysPolicyMenuRel(
                policyManageMenu + "_user_management", policyManageMenu, "user_management"),
            new SysPolicyMenuRel(
                policyManageMenu + "_department_management",
                policyManageMenu,
                "department_management"),
            new SysPolicyMenuRel(
                policyManageMenu + "_job_management", policyManageMenu, "job_management"),
            new SysPolicyMenuRel(policyManageMenu + "_permission", policyManageMenu, "permission"),
            new SysPolicyMenuRel(
                policyManageMenu + "_role_management", policyManageMenu, "role_management"),
            new SysPolicyMenuRel(
                policyManageMenu + "_policy_management", policyManageMenu, "policy_management"),
            new SysPolicyMenuRel(
                policyManageMenu + "_permission_management",
                policyManageMenu,
                "permission_management"),
            new SysPolicyMenuRel(
                policyManageMenu + "_data_scope_management",
                policyManageMenu,
                "data_scope_management"),
            new SysPolicyMenuRel(policyManageMenu + "_system", policyManageMenu, "system"),
            new SysPolicyMenuRel(
                policyManageMenu + "_menu_management", policyManageMenu, "menu_management"),
            new SysPolicyMenuRel(
                policyManageMenu + "_frontend_management", policyManageMenu, "frontend_management"),
            new SysPolicyMenuRel(
                policyManageMenu + "_announcement_management",
                policyManageMenu,
                "announcement_management"),
            new SysPolicyMenuRel(
                policyManageMenu + "_user_dict_management",
                policyManageMenu,
                "user_dict_management"),
            new SysPolicyMenuRel(policyManageMenu + "_support", policyManageMenu, "support"),
            new SysPolicyMenuRel(policyManageMenu + "_user_log", policyManageMenu, "user_log"),
            new SysPolicyMenuRel(
                policyManageMenu + "_operation_log", policyManageMenu, "operation_log"),
            new SysPolicyMenuRel(policyCommonMenu + "_home", policyCommonMenu, "home"),
            new SysPolicyMenuRel(policyCommonMenu + "_dashboard", policyCommonMenu, "dashboard"),
            new SysPolicyMenuRel(policyCommonMenu + "_quick_link", policyCommonMenu, "quick_link"));
    DataPorter<String, SysPolicyMenuRel> porter4 = new DataPorter<>(data4, policyMenuRelService);
    porter4.syncData();
    // 添加角色
    final String roleIdSystem = "1";
    final String roleIdAudit = "2";
    final String roleIdCommon = "3";
    List<SysRole> data5 =
        Arrays.asList(
            new SysRole(roleIdSystem, "系统管理员", null),
            new SysRole(roleIdAudit, "系统审计员", null),
            new SysRole(roleIdCommon, "普通后台用户", null));
    DataPorter<String, SysRole> porter5 = new DataPorter<>(data5, roleService);
    m = porter5.syncData();
    n += m;
    // 添加角色策略关系
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
    data6.add(
        new SysRolePolicyRel(prefixSystem + policyManageMenu, roleIdSystem, policyManageMenu));
    data6.add(new SysRolePolicyRel(prefixSystem + policyFile, roleIdSystem, policyFile));
    // 系统审计员
    final String prefixAudit = "audit_";
    this.permissionGenerators.forEach(
        g ->
            data6.add(
                new SysRolePolicyRel(
                    prefixAudit + g.getReadPolicyId(), roleIdAudit, g.getReadPolicyId())));
    data6.add(new SysRolePolicyRel(prefixAudit + policyManageMenu, roleIdAudit, policyManageMenu));
    data6.add(new SysRolePolicyRel(prefixAudit + policyFile, roleIdAudit, policyFile));
    // 普通后台用户
    final String prefixCommon = "common_";
    data6.add(
        new SysRolePolicyRel(prefixCommon + policyCommonMenu, roleIdCommon, policyCommonMenu));
    data6.add(new SysRolePolicyRel(prefixCommon + policyFile, roleIdCommon, policyFile));
    DataPorter<String, SysRolePolicyRel> porter6 = new DataPorter<>(data6, rolePolicyRelService);
    porter6.syncData();
    // 添加用户角色关系
    List<SysUserRoleRel> data7 =
        Arrays.asList(
            new SysUserRoleRel("manager_role", "2", roleIdSystem),
            new SysUserRoleRel("reporter_role", "3", roleIdAudit),
            new SysUserRoleRel("guest_role", "4", roleIdCommon));
    DataPorter<String, SysUserRoleRel> porter7 = new DataPorter<>(data7, userRoleRelService);
    porter7.syncData();
    return n;
  }

  @Override
  public String getModuleName() {
    return "系统";
  }

  @Override
  protected void init() {
    int n;
    List<String> msg = new ArrayList<>();
    n = this.syncUser();
    msg.add(String.format("插入/更新 %s 条用户数据", n));
    n = this.syncDepartment();
    msg.add(String.format("插入/更新 %s 条部门数据", n));
    n = this.syncFrontend();
    msg.add(String.format("插入/更新 %s 条应用数据", n));
    n = this.syncJob();
    msg.add(String.format("插入/更新 %s 条岗位数据", n));
    n = this.syncMenu();
    msg.add(String.format("插入/更新 %s 条菜单数据", n));
    n = this.syncParam();
    msg.add(String.format("插入/更新 %s 条参数数据", n));
    n = this.syncUserDict();
    msg.add(String.format("插入/更新 %s 条用户字典数据", n));
    n = this.syncAnnouncement();
    msg.add(String.format("插入/更新 %s 条公告", n));
    this.initPermissionGenerators();
    n = this.syncPermission();
    msg.add(String.format("插入/更新 %s 条角色策略权限数据", n));
    msg.forEach(log::info);
  }
}
