package com.wangboot.app;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.datasource.DataSourceKey;
import com.mybatisflex.core.datasource.FlexDataSource;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.wangboot.app.execution.datasource.dbsql.DatabaseSql;
import com.wangboot.app.execution.datasource.dbsql.DatabaseSqlConfig;
import com.wangboot.core.test.auth.WithAuthContextTestExecutionListener;
import com.wangboot.core.web.response.ListBody;
import com.wangboot.model.entity.exception.DeleteCascadeFailedException;
import com.wangboot.system.entity.*;
import com.wangboot.system.entity.vo.SysUserView;
import com.wangboot.system.event.BgTaskResult;
import com.wangboot.system.model.BgTaskStatus;
import com.wangboot.system.model.ClientType;
import com.wangboot.system.service.*;

import java.util.*;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("服务测试")
@SpringBootTest
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  WithAuthContextTestExecutionListener.class
})
public class ServiceTest {

  @Autowired private SysParamService paramService;

  @Autowired private SysJobService jobService;

  @Autowired private SysAnnouncementService announcementService;

  @Autowired private SysBgTaskService bgTaskService;

  @Autowired private SysFrontendService frontendService;

  @Autowired private SysDataScopeService dataScopeService;

  @Autowired private SysPermissionService permissionService;

  @Autowired private SysPolicyService policyService;

  @Autowired private SysRoleService roleService;

  @Autowired private SysUserDictService userDictService;

  @Autowired private SysUserService userService;

  @Autowired private SysUserViewService userViewService;

  @Autowired private SysOperationLogService operationLogService;

  @Autowired private SysUserLogService userLogService;

  @Autowired private SysDepartmentService departmentService;

  @Autowired private SysMenuService menuService;

  @Test
  @Transactional
  public void testParamService() {
    SysParam param = new SysParam();
    String name = RandomUtil.randomString(6);
    String key = RandomUtil.randomString(6);
    String val = RandomUtil.randomString(6);
    param.setName(name);
    param.setParamKey(key);
    param.setParamVal(val);
    // create
    Assertions.assertTrue(paramService.createResource(param));
    SysParam param1 = paramService.viewResource(param.getId());
    Assertions.assertNotNull(param1);
    Assertions.assertEquals(param.getName(), param1.getName());
    Assertions.assertEquals(param.getParamKey(), param1.getParamKey());
    Assertions.assertEquals(param.getParamVal(), param1.getParamVal());
    // update
    param.setParamGroup(RandomUtil.randomString(5));
    Assertions.assertTrue(paramService.updateResource(param));
    SysParam param2 = paramService.viewResource(param.getId());
    Assertions.assertNotNull(param2);
    Assertions.assertEquals(param.getParamGroup(), param2.getParamGroup());
    // get param config
    Assertions.assertEquals("", paramService.getParamConfig(""));
    String config = paramService.getParamConfig(key);
    Assertions.assertEquals(config, val);
    // not exists config
    String config2 = paramService.getParamConfig(key + "no");
    Assertions.assertEquals("", config2);
    // delete
    Assertions.assertTrue(paramService.deleteResource(param));
  }

  @Test
  @Transactional
  public void testJobService() {
    final SysJobService service = jobService;
    // list
    ListBody<SysJob> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    // create
    SysJob entity = new SysJob();
    entity.setName(RandomUtil.randomString(6));
    entity.setType(RandomUtil.randomString(10));
    Assertions.assertTrue(service.createResource(entity));
    SysJob obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    Assertions.assertEquals(entity.getType(), obj.getType());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // update
    entity.setName(RandomUtil.randomString(7));
    Assertions.assertTrue(service.updateResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    Assertions.assertEquals(entity.getType(), obj.getType());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // delete
    Assertions.assertTrue(service.deleteResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNull(obj);
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num, resources.getTotal());
  }

  @Test
  @Transactional
  public void testAnnouncementService() {
    final SysAnnouncementService service = announcementService;
    // list
    ListBody<SysAnnouncement> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    // create
    SysAnnouncement entity = new SysAnnouncement();
    entity.setTitle(RandomUtil.randomString(6));
    entity.setContent(RandomUtil.randomString(10));
    Assertions.assertTrue(service.createResource(entity));
    SysAnnouncement obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getTitle(), obj.getTitle());
    Assertions.assertEquals(entity.getContent(), obj.getContent());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // update
    entity.setTitle(RandomUtil.randomString(7));
    Assertions.assertTrue(service.updateResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getTitle(), obj.getTitle());
    Assertions.assertEquals(entity.getContent(), obj.getContent());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // delete
    Assertions.assertTrue(service.deleteResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNull(obj);
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num, resources.getTotal());
  }

  @Test
  @Transactional
  @SneakyThrows
  public void testBgTaskService() {
    final SysBgTaskService service = bgTaskService;
    // list
    ListBody<SysBgTask> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    if (num > 0) {
      Optional<SysBgTask> optional = resources.getData().stream().findFirst();
      if (optional.isPresent()) {
        // view
        String id = optional.get().getId();
        SysBgTask resource = service.viewResource(id);
        Assertions.assertNull(resource);
      }
    }
    // add task
    String name = RandomUtil.randomString(6);
    SysBgTask task =
        service.addBackgroundTask(
            name, "test", () -> new BgTaskResult("", "test", BgTaskStatus.COMPLETED));
    Assertions.assertNotNull(task);
    Thread.sleep(100);
    SysBgTask obj = service.getDataById(task.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(name, obj.getName());
    Assertions.assertEquals(BgTaskStatus.COMPLETED, obj.getStatus());
  }

  @Test
  @Transactional
  public void testFrontendService() {
    final SysFrontendService service = frontendService;
    // list
    ListBody<SysFrontend> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    // create
    SysFrontend entity = new SysFrontend();
    entity.setName(RandomUtil.randomString(6));
    entity.setClientType(ClientType.WEB);
    Assertions.assertTrue(service.createResource(entity));
    SysFrontend obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    Assertions.assertEquals(entity.getType(), obj.getType());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // update
    entity.setName(RandomUtil.randomString(7));
    Assertions.assertTrue(service.updateResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    Assertions.assertEquals(entity.getType(), obj.getType());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // delete
    Assertions.assertTrue(service.deleteResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNull(obj);
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num, resources.getTotal());
  }

  @Test
  @Transactional
  public void testDataScopeService() {
    final SysDataScopeService service = dataScopeService;
    // list
    ListBody<SysDataScope> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    // create
    SysDataScope entity = new SysDataScope();
    entity.setName(RandomUtil.randomString(6));
    Assertions.assertTrue(service.createResource(entity));
    SysDataScope obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // update
    entity.setName(RandomUtil.randomString(7));
    Assertions.assertTrue(service.updateResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // delete
    Assertions.assertTrue(service.deleteResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNull(obj);
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num, resources.getTotal());
  }

  @Test
  @Transactional
  public void testPermissionService() {
    final SysPermissionService service = permissionService;
    // list
    ListBody<SysPermission> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    // create
    SysPermission entity = new SysPermission();
    entity.setName(RandomUtil.randomString(6));
    entity.setLabel(RandomUtil.randomString(8));
    Assertions.assertTrue(service.createResource(entity));
    SysPermission obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    Assertions.assertEquals(entity.getLabel(), obj.getLabel());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // update
    entity.setName(RandomUtil.randomString(7));
    Assertions.assertTrue(service.updateResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // delete
    Assertions.assertTrue(service.deleteResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNull(obj);
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num, resources.getTotal());
  }

  @Test
  @Transactional
  public void testPolicyService() {
    final SysPolicyService service = policyService;
    // list
    ListBody<SysPolicy> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    // create
    SysPolicy entity = new SysPolicy();
    entity.setName(RandomUtil.randomString(6));
    entity.setLabel(RandomUtil.randomString(8));
    Assertions.assertTrue(service.createResource(entity));
    SysPolicy obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    Assertions.assertEquals(entity.getLabel(), obj.getLabel());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // update
    entity.setName(RandomUtil.randomString(7));
    Assertions.assertTrue(service.updateResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // delete
    Assertions.assertTrue(service.deleteResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNull(obj);
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num, resources.getTotal());
  }

  @Test
  @Transactional
  public void testRoleService() {
    final SysRoleService service = roleService;
    // list
    ListBody<SysRole> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    // create
    SysRole entity = new SysRole();
    entity.setName(RandomUtil.randomString(6));
    Assertions.assertTrue(service.createResource(entity));
    SysRole obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // update
    entity.setName(RandomUtil.randomString(7));
    Assertions.assertTrue(service.updateResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // delete
    Assertions.assertTrue(service.deleteResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNull(obj);
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num, resources.getTotal());
  }

  @Test
  @Transactional
  public void testUserDictService() {
    final SysUserDictService service = userDictService;
    // list
    ListBody<SysUserDict> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    // create
    SysUserDict entity = new SysUserDict();
    entity.setName(RandomUtil.randomString(6));
    entity.setDictGroup(RandomUtil.randomString(6));
    entity.setDictCode(RandomUtil.randomString(6));
    entity.setDictVal(RandomUtil.randomString(8));
    Assertions.assertTrue(service.createResource(entity));
    SysUserDict obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    Assertions.assertEquals(entity.getDictGroup(), obj.getDictGroup());
    Assertions.assertEquals(entity.getDictCode(), obj.getDictCode());
    Assertions.assertEquals(entity.getDictVal(), obj.getDictVal());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // update
    entity.setName(RandomUtil.randomString(7));
    Assertions.assertTrue(service.updateResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // delete
    Assertions.assertTrue(service.deleteResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNull(obj);
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num, resources.getTotal());
  }

  @Test
  @Transactional
  public void testUserService() {
    final SysUserService service = userService;
    // list
    ListBody<SysUser> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    // create
    SysUser entity = new SysUser();
    entity.setUsername(RandomUtil.randomString(6));
    entity.setNickname(RandomUtil.randomString(6));
    entity.setAddress(RandomUtil.randomString(8));
    Assertions.assertTrue(service.createResource(entity));
    SysUser obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getUsername(), obj.getUsername());
    Assertions.assertEquals(entity.getNickname(), obj.getNickname());
    Assertions.assertEquals(entity.getAddress(), obj.getAddress());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // update
    entity.setUsername(RandomUtil.randomString(7));
    Assertions.assertTrue(service.updateResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getUsername(), obj.getUsername());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // delete
    Assertions.assertTrue(service.deleteResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNull(obj);
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num, resources.getTotal());
  }

  @Test
  @Transactional
  public void testUserViewService() {
    final SysUserViewService service = userViewService;
    // list
    ListBody<SysUserView> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    if (num > 0) {
      Optional<SysUserView> optional = resources.getData().stream().findFirst();
      if (optional.isPresent()) {
        // view
        String id = optional.get().getId();
        SysUserView resource = service.viewResource(id);
        Assertions.assertNotNull(resource);
      }
    }
  }

  @Test
  @Transactional
  public void testOperationLogService() {
    final SysOperationLogService service = operationLogService;
    // list
    ListBody<SysOperationLog> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    if (num > 0) {
      Optional<SysOperationLog> optional = resources.getData().stream().findFirst();
      if (optional.isPresent()) {
        // view
        String id = optional.get().getId();
        SysOperationLog resource = service.viewResource(id);
        Assertions.assertNotNull(resource);
      }
    }
  }

  @Test
  @Transactional
  public void testUserLogService() {
    final SysUserLogService service = userLogService;
    // list
    ListBody<SysUserLog> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    if (num > 0) {
      Optional<SysUserLog> optional = resources.getData().stream().findFirst();
      if (optional.isPresent()) {
        // view
        String id = optional.get().getId();
        SysUserLog resource = service.viewResource(id);
        Assertions.assertNotNull(resource);
      }
    }
  }

  @Test
  @Transactional
  public void testDepartmentService() {
    final SysDepartmentService service = departmentService;
    // list
    ListBody<SysDepartment> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    // create
    SysDepartment entity = new SysDepartment();
    entity.setName(RandomUtil.randomString(6));
    entity.setFullname(RandomUtil.randomString(10));
    entity.setLeader(RandomUtil.randomString(10));
    Assertions.assertTrue(service.createResource(entity));
    SysDepartment obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    Assertions.assertEquals(entity.getFullname(), obj.getFullname());
    Assertions.assertEquals(entity.getLeader(), obj.getLeader());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // update
    entity.setName(RandomUtil.randomString(7));
    Assertions.assertTrue(service.updateResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // list children
    long count = service.getDirectChildrenCount(entity.getId());
    Assertions.assertEquals(0, count);
    // add child
    SysDepartment child = new SysDepartment();
    child.setName(RandomUtil.randomString(5));
    child.setParentId(entity.getId());
    Assertions.assertTrue(service.createResource(child));
    SysDepartment obj1 = service.viewResource(child.getId());
    Assertions.assertNotNull(obj1);
    Assertions.assertEquals(child.getName(), obj1.getName());
    List<SysDepartment> children = service.listDirectChildren(entity.getId());
    Assertions.assertEquals(1, children.size());
    Assertions.assertEquals(child.getId(), children.get(0).getId());
    // delete failed with children
    Assertions.assertThrows(
        DeleteCascadeFailedException.class, () -> service.deleteResource(entity));
    // delete
    Assertions.assertTrue(service.deleteResource(child));
    Assertions.assertTrue(service.deleteResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNull(obj);
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num, resources.getTotal());
  }

  @Test
  @Transactional
  public void testMenuService() {
    final SysMenuService service = menuService;
    // list
    ListBody<SysMenu> resources = service.listResourcesAll(null, null, null);
    long num = resources.getTotal();
    // create
    SysMenu entity = new SysMenu();
    entity.setName(RandomUtil.randomString(6));
    entity.setPath(RandomUtil.randomString(10));
    Assertions.assertTrue(service.createResource(entity));
    SysMenu obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    Assertions.assertEquals(entity.getPath(), obj.getPath());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // update
    entity.setName(RandomUtil.randomString(7));
    Assertions.assertTrue(service.updateResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNotNull(obj);
    Assertions.assertEquals(entity.getName(), obj.getName());
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num + 1, resources.getTotal());
    // list children
    long count = service.getDirectChildrenCount(entity.getId());
    Assertions.assertEquals(0, count);
    // add child
    SysMenu child = new SysMenu();
    child.setName(RandomUtil.randomString(5));
    child.setParentId(entity.getId());
    Assertions.assertTrue(service.createResource(child));
    SysMenu obj1 = service.viewResource(child.getId());
    Assertions.assertNotNull(obj1);
    Assertions.assertEquals(child.getName(), obj1.getName());
    List<SysMenu> children = service.listDirectChildren(entity.getId());
    Assertions.assertEquals(1, children.size());
    Assertions.assertEquals(child.getId(), children.get(0).getId());
    // delete failed with children
    Assertions.assertThrows(
        DeleteCascadeFailedException.class, () -> service.deleteResource(entity));
    // delete
    Assertions.assertTrue(service.deleteResource(child));
    Assertions.assertTrue(service.deleteResource(entity));
    obj = service.viewResource(entity.getId());
    Assertions.assertNull(obj);
    resources = service.listResourcesAll(null, null, null);
    Assertions.assertEquals(num, resources.getTotal());
  }

  @Test
  public void test1() {
    FlexDataSource flexDataSource = FlexGlobalConfig.getDefaultConfig()
      .getDataSource();

    DruidDataSource newDataSource = new DruidDataSource();
    Properties properties = new Properties();
    properties.put("druid.name", "db1");
    properties.put("druid.url", "jdbc:mysql://localhost:3306/wb-template-dev");
    properties.put("druid.username", "root");
    properties.put("druid.password", "123456");
    newDataSource.configFromPropeties(properties);

    flexDataSource.addDataSource("n1", newDataSource);

    try{
      DataSourceKey.use("n1");
      List<Row> rows = Db.selectAll("wb_sys_job");
      System.out.println(rows);
    }finally{
      DataSourceKey.clear();
    }
  }

  @Test
  public void testDatabaseSql() {
    String name = RandomUtil.randomString(6);
    DatabaseSqlConfig config = new DatabaseSqlConfig("jdbc:mysql://localhost:3306/wb-template-dev", "root", "123456", "select * from wb_sys_job where type = '${type}'");
    DatabaseSql datasource = new DatabaseSql(name, config);
    datasource.configDatasource();
    Map<String, String> params = new HashMap<>();
    params.put("type", "管理");
    Object obj = datasource.retrieveData(params);
    System.out.println(obj.getClass());

  }
}
