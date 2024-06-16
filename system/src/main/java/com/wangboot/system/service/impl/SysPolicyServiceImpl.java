package com.wangboot.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.exception.DeleteCascadeFailedException;
import com.wangboot.system.entity.SysDataScope;
import com.wangboot.system.entity.SysMenu;
import com.wangboot.system.entity.SysPermission;
import com.wangboot.system.entity.SysPolicy;
import com.wangboot.system.entity.relation.SysPolicyDataScopeRel;
import com.wangboot.system.entity.relation.SysPolicyMenuRel;
import com.wangboot.system.entity.relation.SysPolicyPermissionRel;
import com.wangboot.system.entity.relation.table.SysPolicyDataScopeRelTableDef;
import com.wangboot.system.entity.relation.table.SysPolicyMenuRelTableDef;
import com.wangboot.system.entity.relation.table.SysPolicyPermissionRelTableDef;
import com.wangboot.system.entity.relation.table.SysRolePolicyRelTableDef;
import com.wangboot.system.entity.table.SysDataScopeTableDef;
import com.wangboot.system.entity.table.SysMenuTableDef;
import com.wangboot.system.entity.table.SysPermissionTableDef;
import com.wangboot.system.mapper.SysPolicyMapper;
import com.wangboot.system.service.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 服务层实现。
 *
 * @author wwtg99
 */
@Service
@RequiredArgsConstructor
public class SysPolicyServiceImpl extends ServiceImpl<SysPolicyMapper, SysPolicy>
    implements SysPolicyService {

  private final SysPolicyPermissionRelService policyPermissionRelService;

  private final SysPolicyDataScopeRelService policyDataScopeRelService;

  private final SysPolicyMenuRelService policyMenuRelService;

  private final SysRolePolicyRelService rolePolicyRelService;

  @Override
  @Nullable
  public SysPolicy getDataById(@NonNull String id) {
    return this.getMapper().selectOneWithRelationsById(id);
  }

  @Override
  @NonNull
  public SysPolicy checkBeforeDeleteObject(@NonNull SysPolicy entity) {
    entity = SysPolicyService.super.checkBeforeDeleteObject(entity);
    // 存在关联角色或权限、菜单、数据权限，则不允许删除
    QueryWrapper wrapper =
        policyPermissionRelService
            .query()
            .where(
                SysPolicyPermissionRelTableDef.SYS_POLICY_PERMISSION_REL.POLICY_ID.eq(
                    entity.getId()));
    if (policyPermissionRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(entity.getId());
    }
    wrapper =
        policyMenuRelService
            .query()
            .where(SysPolicyMenuRelTableDef.SYS_POLICY_MENU_REL.POLICY_ID.eq(entity.getId()));
    if (policyMenuRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(entity.getId());
    }
    wrapper =
        policyDataScopeRelService
            .query()
            .where(
                SysPolicyDataScopeRelTableDef.SYS_POLICY_DATA_SCOPE_REL.POLICY_ID.eq(
                    entity.getId()));
    if (policyDataScopeRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(entity.getId());
    }
    wrapper =
        rolePolicyRelService
            .query()
            .where(SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL.POLICY_ID.eq(entity.getId()));
    if (rolePolicyRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(entity.getId());
    }
    return entity;
  }

  @Override
  @NonNull
  public Collection<SysPolicy> checkBeforeBatchDeleteObjects(
      @NonNull Collection<SysPolicy> entities) {
    entities = SysPolicyService.super.checkBeforeBatchDeleteObjects(entities);
    // 存在关联角色或权限、菜单、数据权限，则不允许删除
    Collection<String> ids = entities.stream().map(IdEntity::getId).collect(Collectors.toList());
    QueryWrapper wrapper =
        policyPermissionRelService
            .query()
            .where(SysPolicyPermissionRelTableDef.SYS_POLICY_PERMISSION_REL.POLICY_ID.in(ids));
    if (policyPermissionRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(String.join(",", ids));
    }
    wrapper =
        policyMenuRelService
            .query()
            .where(SysPolicyMenuRelTableDef.SYS_POLICY_MENU_REL.POLICY_ID.in(ids));
    if (policyMenuRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(String.join(",", ids));
    }
    wrapper =
        policyDataScopeRelService
            .query()
            .where(SysPolicyDataScopeRelTableDef.SYS_POLICY_DATA_SCOPE_REL.POLICY_ID.in(ids));
    if (policyDataScopeRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(String.join(",", ids));
    }
    wrapper =
        rolePolicyRelService
            .query()
            .where(SysRolePolicyRelTableDef.SYS_ROLE_POLICY_REL.POLICY_ID.in(ids));
    if (rolePolicyRelService.count(wrapper) > 0) {
      throw new DeleteCascadeFailedException(String.join(",", ids));
    }
    return entities;
  }

  @Override
  @NonNull
  public List<SysPermission> getPolicyPermissions(@Nullable Collection<String> ids) {
    if (Objects.isNull(ids) || ids.isEmpty()) {
      return Collections.emptyList();
    }
    QueryWrapper wrapper =
        query()
            .select(SysPermissionTableDef.SYS_PERMISSION.ALL_COLUMNS)
            .from(SysPermissionTableDef.SYS_PERMISSION.as("r"))
            .leftJoin(SysPolicyPermissionRelTableDef.SYS_POLICY_PERMISSION_REL)
            .as("ur")
            .on(
                SysPolicyPermissionRelTableDef.SYS_POLICY_PERMISSION_REL.PERMISSION_ID.eq(
                    SysPermissionTableDef.SYS_PERMISSION.ID))
            .where(SysPolicyPermissionRelTableDef.SYS_POLICY_PERMISSION_REL.POLICY_ID.in(ids));
    return this.getMapper().selectListByQueryAs(wrapper, SysPermission.class);
  }

  @Override
  public boolean addPolicyPermissions(String id, @Nullable List<SysPermission> entities) {
    if (!StringUtils.hasText(id) || Objects.isNull(entities) || entities.isEmpty()) {
      return false;
    }
    List<SysPolicyPermissionRel> rels =
        entities.stream()
            .map(d -> SysPolicyPermissionRel.builder().policyId(id).permissionId(d.getId()).build())
            .collect(Collectors.toList());
    return this.policyPermissionRelService.saveBatch(rels);
  }

  @Override
  public boolean setPolicyPermissions(String id, @Nullable List<SysPermission> entities) {
    if (!StringUtils.hasText(id) || Objects.isNull(entities) || entities.isEmpty()) {
      return false;
    }
    QueryWrapper wrapper =
        query().where(SysPolicyPermissionRelTableDef.SYS_POLICY_PERMISSION_REL.POLICY_ID.eq(id));
    this.policyPermissionRelService.remove(wrapper);
    return addPolicyPermissions(id, entities);
  }

  @Override
  public boolean removePolicyPermissions(String id, @Nullable List<String> entityIds) {
    if (!StringUtils.hasText(id) || Objects.isNull(entityIds) || entityIds.isEmpty()) {
      return false;
    }
    QueryWrapper wrapper =
        query()
            .where(SysPolicyPermissionRelTableDef.SYS_POLICY_PERMISSION_REL.POLICY_ID.eq(id))
            .and(
                SysPolicyPermissionRelTableDef.SYS_POLICY_PERMISSION_REL.PERMISSION_ID.in(
                    entityIds));
    return this.policyPermissionRelService.remove(wrapper);
  }

  @Override
  @NonNull
  public List<SysDataScope> getPolicyDataScopes(@Nullable Collection<? extends Serializable> ids) {
    if (Objects.isNull(ids) || ids.isEmpty()) {
      return Collections.emptyList();
    }
    QueryWrapper wrapper =
        query()
            .select(SysDataScopeTableDef.SYS_DATA_SCOPE.ALL_COLUMNS)
            .from(SysDataScopeTableDef.SYS_DATA_SCOPE.as("r"))
            .leftJoin(SysPolicyDataScopeRelTableDef.SYS_POLICY_DATA_SCOPE_REL)
            .as("ur")
            .on(
                SysPolicyDataScopeRelTableDef.SYS_POLICY_DATA_SCOPE_REL.DATA_SCOPE_ID.eq(
                    SysDataScopeTableDef.SYS_DATA_SCOPE.ID))
            .where(SysPolicyDataScopeRelTableDef.SYS_POLICY_DATA_SCOPE_REL.POLICY_ID.in(ids));
    return this.getMapper().selectListByQueryAs(wrapper, SysDataScope.class);
  }

  @Override
  public boolean addPolicyDataScopes(String id, @Nullable List<SysDataScope> entities) {
    if (!StringUtils.hasText(id) || Objects.isNull(entities) || entities.isEmpty()) {
      return false;
    }
    List<SysPolicyDataScopeRel> rels =
        entities.stream()
            .map(d -> SysPolicyDataScopeRel.builder().policyId(id).dataScopeId(d.getId()).build())
            .collect(Collectors.toList());
    return this.policyDataScopeRelService.saveBatch(rels);
  }

  @Override
  public boolean setPolicyDataScopes(String id, @Nullable List<SysDataScope> entities) {
    if (!StringUtils.hasText(id) || Objects.isNull(entities) || entities.isEmpty()) {
      return false;
    }
    QueryWrapper wrapper =
        query().where(SysPolicyDataScopeRelTableDef.SYS_POLICY_DATA_SCOPE_REL.POLICY_ID.eq(id));
    this.policyDataScopeRelService.remove(wrapper);
    return addPolicyDataScopes(id, entities);
  }

  @Override
  public boolean removePolicyDataScopes(String id, @Nullable List<String> entityIds) {
    if (!StringUtils.hasText(id) || Objects.isNull(entityIds) || entityIds.isEmpty()) {
      return false;
    }
    QueryWrapper wrapper =
        query()
            .where(SysPolicyDataScopeRelTableDef.SYS_POLICY_DATA_SCOPE_REL.POLICY_ID.eq(id))
            .and(
                SysPolicyDataScopeRelTableDef.SYS_POLICY_DATA_SCOPE_REL.DATA_SCOPE_ID.in(
                    entityIds));
    return this.policyDataScopeRelService.remove(wrapper);
  }

  @Override
  @NonNull
  public List<SysMenu> getPolicyMenus(@Nullable Collection<? extends Serializable> ids) {
    if (Objects.isNull(ids) || ids.isEmpty()) {
      return Collections.emptyList();
    }
    QueryWrapper wrapper =
        query()
            .select(SysMenuTableDef.SYS_MENU.ALL_COLUMNS)
            .from(SysMenuTableDef.SYS_MENU.as("r"))
            .leftJoin(SysPolicyMenuRelTableDef.SYS_POLICY_MENU_REL)
            .as("ur")
            .on(
                SysPolicyMenuRelTableDef.SYS_POLICY_MENU_REL.MENU_ID.eq(
                    SysMenuTableDef.SYS_MENU.ID))
            .where(SysPolicyMenuRelTableDef.SYS_POLICY_MENU_REL.POLICY_ID.in(ids))
            .orderBy(SysMenuTableDef.SYS_MENU.SORT, true);
    return this.getMapper().selectListByQueryAs(wrapper, SysMenu.class);
  }

  @Override
  public boolean addPolicyMenus(String id, @Nullable List<SysMenu> entities) {
    if (!StringUtils.hasText(id) || Objects.isNull(entities) || entities.isEmpty()) {
      return false;
    }
    List<SysPolicyMenuRel> rels =
        entities.stream()
            .map(d -> SysPolicyMenuRel.builder().policyId(id).menuId(d.getId()).build())
            .collect(Collectors.toList());
    return this.policyMenuRelService.saveBatch(rels);
  }

  @Override
  public boolean setPolicyMenus(String id, @Nullable List<SysMenu> entities) {
    if (!StringUtils.hasText(id) || Objects.isNull(entities) || entities.isEmpty()) {
      return false;
    }
    QueryWrapper wrapper =
        query().where(SysPolicyMenuRelTableDef.SYS_POLICY_MENU_REL.POLICY_ID.eq(id));
    this.policyMenuRelService.remove(wrapper);
    return addPolicyMenus(id, entities);
  }

  @Override
  public boolean removePolicyMenus(String id, @Nullable List<String> entityIds) {
    if (!StringUtils.hasText(id) || Objects.isNull(entityIds) || entityIds.isEmpty()) {
      return false;
    }
    QueryWrapper wrapper =
        query()
            .where(SysPolicyMenuRelTableDef.SYS_POLICY_MENU_REL.POLICY_ID.eq(id))
            .and(SysPolicyMenuRelTableDef.SYS_POLICY_MENU_REL.MENU_ID.in(entityIds));
    return this.policyMenuRelService.remove(wrapper);
  }
}
