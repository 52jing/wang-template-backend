package com.wangboot.system.model;

import com.wangboot.core.auth.authorization.resource.ApiResource;
import com.wangboot.system.entity.SysPermission;
import com.wangboot.system.entity.SysPolicy;
import com.wangboot.system.entity.relation.SysPolicyPermissionRel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * 权限生成器
 *
 * @author wwtg99
 */
@Data
public class PermissionGenerator {
  public static final String DEL_SEMICOLON = ":";
  public static final String DEL_UNDERSCORE = "_";
  public static final String VIEW_LABEL = "查看";
  public static final String CREATE_LABEL = "创建";
  public static final String UPDATE_LABEL = "更新";
  public static final String DELETE_LABEL = "删除";
  public static final String PERM_LABEL = "权限";
  public static final String WRITE_POLICY = "write";
  public static final String READ_POLICY = "read";
  public static final String WRITE_POLICY_LABEL = "修改策略";
  public static final String READ_POLICY_LABEL = "只读策略";

  private final String label;
  private final String group;
  private final String name;
  private final boolean readPerm;
  private final boolean writePerm;
  private final boolean readonly;

  private final ApiResource viewResource;
  private final ApiResource createResource;
  private final ApiResource updateResource;
  private final ApiResource deleteResource;

  public PermissionGenerator(
      String label,
      String group,
      String name,
      boolean readPerm,
      boolean writePerm,
      boolean readonly) {
    this.label = label;
    this.group = group;
    this.name = name;
    this.readPerm = readPerm;
    this.writePerm = writePerm;
    this.readonly = readonly;
    this.viewResource = this.getApiPermission(ApiResource.REST_PERMISSION_ACTION_VIEW);
    this.createResource = this.getApiPermission(ApiResource.REST_PERMISSION_ACTION_CREATE);
    this.updateResource = this.getApiPermission(ApiResource.REST_PERMISSION_ACTION_UPDATE);
    this.deleteResource = this.getApiPermission(ApiResource.REST_PERMISSION_ACTION_DELETE);
  }

  public PermissionGenerator(String label, String group, String name) {
    this(label, group, name, true, true, true);
  }

  /**
   * 生成权限集合
   *
   * @return 权限集合
   */
  public List<SysPermission> generatePermissions() {
    List<SysPermission> perms = new ArrayList<>();
    if (this.readPerm) {
      perms.add(
          new SysPermission(
              this.viewResource.getResourceName(),
              this.viewResource.getResourceName(),
              this.getPermissionLabel(VIEW_LABEL),
              this.readonly));
    }
    if (this.writePerm) {
      perms.add(
          new SysPermission(
              this.createResource.getResourceName(),
              this.createResource.getResourceName(),
              this.getPermissionLabel(CREATE_LABEL),
              this.readonly));
      perms.add(
          new SysPermission(
              this.updateResource.getResourceName(),
              this.updateResource.getResourceName(),
              this.getPermissionLabel(UPDATE_LABEL),
              this.readonly));
      perms.add(
          new SysPermission(
              this.deleteResource.getResourceName(),
              this.deleteResource.getResourceName(),
              this.getPermissionLabel(DELETE_LABEL),
              this.readonly));
    }
    return perms;
  }

  /**
   * 获取权限ID集合
   *
   * @return 权限ID集合
   */
  public List<String> generatePermissionIds() {
    return this.generatePermissions().stream()
        .map(SysPermission::getId)
        .collect(Collectors.toList());
  }

  /**
   * 生成策略集合
   *
   * @return 策略集合
   */
  public List<SysPolicy> generatePolicies() {
    List<SysPolicy> policies = new ArrayList<>();
    if (this.readPerm) {
      String name = this.getReadPolicyId();
      SysPolicy policy = new SysPolicy();
      policy.setId(name);
      policy.setName(name);
      policy.setLabel(this.label + READ_POLICY_LABEL);
      policy.setReadonly(this.readonly);
      policies.add(policy);
    }
    if (this.writePerm) {
      String name = this.getWritePolicyId();
      SysPolicy policy = new SysPolicy();
      policy.setId(name);
      policy.setName(name);
      policy.setLabel(this.label + WRITE_POLICY_LABEL);
      policy.setReadonly(this.readonly);
      policies.add(policy);
    }
    return policies;
  }

  /**
   * 获取只读策略ID
   *
   * @return 策略ID
   */
  public String getReadPolicyId() {
    return this.group + DEL_UNDERSCORE + this.name + DEL_UNDERSCORE + READ_POLICY;
  }

  /**
   * 获取修改策略ID
   *
   * @return 策略ID
   */
  public String getWritePolicyId() {
    return this.group + DEL_UNDERSCORE + this.name + DEL_UNDERSCORE + WRITE_POLICY;
  }

  /**
   * 生成策略权限关系集合
   *
   * @return 策略权限关系集合
   */
  public List<SysPolicyPermissionRel> generatePolicyPermissionRel() {
    List<SysPolicyPermissionRel> rels = new ArrayList<>();
    if (this.readPerm) {
      String policyId = this.getReadPolicyId();
      rels.add(
          new SysPolicyPermissionRel(
              policyId + DEL_UNDERSCORE + this.viewResource.getResourceName(),
              policyId,
              this.viewResource.getResourceName()));
    }
    if (this.writePerm) {
      String policyId = this.getWritePolicyId();
      rels.add(
          new SysPolicyPermissionRel(
              policyId + DEL_UNDERSCORE + this.viewResource.getResourceName(),
              policyId,
              this.createResource.getResourceName()));
      rels.add(
          new SysPolicyPermissionRel(
              policyId + DEL_UNDERSCORE + this.updateResource.getResourceName(),
              policyId,
              this.updateResource.getResourceName()));
      rels.add(
          new SysPolicyPermissionRel(
              policyId + DEL_UNDERSCORE + this.deleteResource.getResourceName(),
              policyId,
              this.deleteResource.getResourceName()));
    }
    return rels;
  }

  private ApiResource getApiPermission(String action) {
    return new ApiResource(this.group, this.name, action);
  }

  private String getPermissionLabel(String prefix) {
    return prefix + this.label + PERM_LABEL;
  }
}
