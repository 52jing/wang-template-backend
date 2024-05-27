package com.wangboot.app;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangboot.core.test.TestClient;
import com.wangboot.core.test.auth.WithAdminUser;
import com.wangboot.core.test.auth.WithAuthContextTestExecutionListener;
import com.wangboot.core.test.auth.WithMockUser;
import com.wangboot.system.entity.*;
import com.wangboot.system.model.ClientType;
import java.time.OffsetDateTime;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DisplayName("API测试")
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  WithAuthContextTestExecutionListener.class
})
@AutoConfigureMockMvc
public class ApiTest {

  private static final String JOB_URL = "/system/job";
  private static final String DATA_SCOPE_URL = "/system/data_scope";
  private static final String ANNOUNCEMENT_URL = "/system/announcement";
  private static final String FRONTEND_URL = "/system/frontend";
  private static final String PARAM_URL = "/system/param";
  private static final String PERMISSION_URL = "/system/permission";
  private static final String POLICY_URL = "/system/policy";
  private static final String ROLE_URL = "/system/role";
  private static final String USER_DICT_URL = "/system/user_dict";
  private static final String USER_URL = "/system/user";
  private static final String DEPARTMENT_URL = "/system/department";
  private static final String MENU_URL = "/system/menu";
  private static final String OPERATION_LOG_URL = "/log/operation_log";
  private static final String USER_LOG_URL = "/log/user_log";

  private static final String DATA_KEY = "$.data";
  private static final String DATA_LENGTH_KEY = "$.data.length()";
  private static final String DATA_TYPE_KEY = "$.data.type";
  private static final String DATA_GROUP_KEY = "$.data.groupName";
  private static final String DATA_SORT_KEY = "$.data.sort";
  private static final String DATA_ID_KEY = "$.data.id";
  private static final String DATA0_ID_KEY = "$.data[0].id";
  private static final String DATA_USERNAME_KEY = "$.data.username";
  private static final String DATA_NICKNAME_KEY = "$.data.nickname";
  private static final String DATA_ADDRESS_KEY = "$.data.address";
  private static final String DATA_NAME_KEY = "$.data.name";
  private static final String DATA_FULLNAME_KEY = "$.data.fullname";
  private static final String DATA_PARENT_ID_KEY = "$.data.parentId";
  private static final String DATA_PATH_KEY = "$.data.path";
  private static final String DATA_ICON_KEY = "$.data.icon";
  private static final String DATA_TITLE_KEY = "$.data.title";
  private static final String DATA_CONTENT_KEY = "$.data.content";
  private static final String DATA_PARAM_KEY_KEY = "$.data.paramKey";
  private static final String DATA_DICT_CODE_KEY = "$.data.dictCode";
  private static final String TOTAL_KEY = "$.total";
  private static final String PAGE_SIZE_KEY = "$.pageSize";
  private static final String PAGE_KEY = "$.page";
  private static final String DATA_FIELD = "data";
  private static final String ID_FIELD = "id";
  private static final String IDS_FIELD = "ids";
  private static final String UPDATE_TIME_FIELD = "updatedTime";

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private TestClient testClient;

  @BeforeEach
  public void initTestClient() {
    this.testClient = new TestClient(this.mockMvc);
  }

  @Test
  @WithAdminUser
  @SneakyThrows
  public void testJobWithSuperuser() {
    String uri = JOB_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysJob entity = new SysJob();
    entity.setName(RandomUtil.randomString(8));
    entity.setType(RandomUtil.randomString(8));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_TYPE_KEY).value(entity.getType()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_TYPE_KEY).value(entity.getType()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setName(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:job:view",
        "system:job:create",
        "system:job:update",
        "system:job:delete"
      })
  @SneakyThrows
  public void testJob() {
    String uri = JOB_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysJob entity = new SysJob();
    entity.setName(RandomUtil.randomString(8));
    entity.setType(RandomUtil.randomString(8));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_TYPE_KEY).value(entity.getType()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_TYPE_KEY).value(entity.getType()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setName(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(id = "99", username = "user", staff = true)
  @SneakyThrows
  public void testJobWithoutPermission() {
    String uri = JOB_URL;
    // list
    testClient.get(uri).andExpect(MockMvcResultMatchers.status().isForbidden());
    // create
    SysJob entity = new SysJob();
    entity.setName(RandomUtil.randomString(8));
    entity.setType(RandomUtil.randomString(8));
    testClient
        .post(uri, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      authorities = {
        "system:job:view",
        "system:job:create",
        "system:job:update",
        "system:job:delete"
      })
  @SneakyThrows
  public void testJobWithoutStaff() {
    String uri = JOB_URL;
    // list
    testClient.get(uri).andExpect(MockMvcResultMatchers.status().isForbidden());
    // create
    SysJob entity = new SysJob();
    entity.setName(RandomUtil.randomString(8));
    entity.setType(RandomUtil.randomString(8));
    testClient
        .post(uri, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:data_scope:view",
        "system:data_scope:create",
        "system:data_scope:update",
        "system:data_scope:delete"
      })
  @SneakyThrows
  public void testDataScope() {
    String uri = DATA_SCOPE_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysDataScope entity = new SysDataScope();
    entity.setName(RandomUtil.randomString(8));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setName(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:announcement:view",
        "system:announcement:create",
        "system:announcement:update",
        "system:announcement:delete"
      })
  @SneakyThrows
  public void testAnnouncement() {
    String uri = ANNOUNCEMENT_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysAnnouncement entity = new SysAnnouncement();
    entity.setTitle(RandomUtil.randomString(8));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_TITLE_KEY).value(entity.getTitle()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_TITLE_KEY).value(entity.getTitle()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setTitle(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_TITLE_KEY).value(entity.getTitle()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:frontend:view",
        "system:frontend:create",
        "system:frontend:update",
        "system:frontend:delete"
      })
  @SneakyThrows
  public void testFrontend() {
    String uri = FRONTEND_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysFrontend entity = new SysFrontend();
    entity.setName(RandomUtil.randomString(8));
    entity.setClientType(ClientType.WEB);
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setName(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:param:view",
        "system:param:create",
        "system:param:update",
        "system:param:delete"
      })
  @SneakyThrows
  public void testParam() {
    String uri = PARAM_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysParam entity = new SysParam();
    entity.setName(RandomUtil.randomString(8));
    entity.setParamKey(RandomUtil.randomString(10));
    entity.setParamVal(RandomUtil.randomString(12));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_PARAM_KEY_KEY).value(entity.getParamKey()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_PARAM_KEY_KEY).value(entity.getParamKey()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setName(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_PARAM_KEY_KEY).value(entity.getParamKey()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:permission:view",
        "system:permission:create",
        "system:permission:update",
        "system:permission:delete"
      })
  @SneakyThrows
  public void testPermission() {
    String uri = PERMISSION_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysPermission entity = new SysPermission();
    entity.setName(RandomUtil.randomString(8));
    entity.setLabel(RandomUtil.randomString(10));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setName(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:policy:view",
        "system:policy:create",
        "system:policy:update",
        "system:policy:delete"
      })
  @SneakyThrows
  public void testPolicy() {
    String uri = POLICY_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysPolicy entity = new SysPolicy();
    entity.setName(RandomUtil.randomString(8));
    entity.setLabel(RandomUtil.randomString(10));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setName(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:role:view",
        "system:role:create",
        "system:role:update",
        "system:role:delete"
      })
  @SneakyThrows
  public void testRole() {
    String uri = ROLE_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysRole entity = new SysRole();
    entity.setName(RandomUtil.randomString(8));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setName(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:user_dict:view",
        "system:user_dict:create",
        "system:user_dict:update",
        "system:user_dict:delete"
      })
  @SneakyThrows
  public void testUserDict() {
    String uri = USER_DICT_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysUserDict entity = new SysUserDict();
    entity.setName(RandomUtil.randomString(8));
    entity.setDictGroup(RandomUtil.randomString(10));
    entity.setDictCode(RandomUtil.randomString(12));
    entity.setDictVal(RandomUtil.randomString(8));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_DICT_CODE_KEY).value(entity.getDictCode()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_DICT_CODE_KEY).value(entity.getDictCode()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setName(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_DICT_CODE_KEY).value(entity.getDictCode()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:user:view",
        "system:user:create",
        "system:user:update",
        "system:user:delete"
      })
  @SneakyThrows
  public void testUser() {
    String uri = USER_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysUser entity = new SysUser();
    entity.setUsername(RandomUtil.randomString(8));
    entity.setNickname(RandomUtil.randomString(10));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_USERNAME_KEY).value(entity.getUsername()))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_NICKNAME_KEY).value(entity.getNickname()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_USERNAME_KEY).value(entity.getUsername()))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_NICKNAME_KEY).value(entity.getNickname()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setNickname(RandomUtil.randomString(12));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_USERNAME_KEY).value(entity.getUsername()))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NICKNAME_KEY).value(entity.getNickname()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:department:view",
        "system:department:create",
        "system:department:update",
        "system:department:delete"
      })
  @SneakyThrows
  public void testDepartment() {
    String uri = DEPARTMENT_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysDepartment entity = new SysDepartment();
    entity.setName(RandomUtil.randomString(8));
    entity.setFullname(RandomUtil.randomString(10));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_FULLNAME_KEY).value(entity.getFullname()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_FULLNAME_KEY).value(entity.getFullname()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setName(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_FULLNAME_KEY).value(entity.getFullname()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // create child
    SysDepartment childEntity = new SysDepartment();
    childEntity.setName(RandomUtil.randomString(8));
    childEntity.setFullname(RandomUtil.randomString(10));
    childEntity.setParentId(id);
    content =
        testClient
            .post(uri, objectMapper.writeValueAsString(childEntity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(childEntity.getName()))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_FULLNAME_KEY).value(childEntity.getFullname()))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_PARENT_ID_KEY).value(childEntity.getParentId()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String childId = data.get(DATA_FIELD).get(ID_FIELD).asText();
    childEntity.setId(id);
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    testClient.delete(uri + "/" + childId).andExpect(MockMvcResultMatchers.status().isNoContent());
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {
        "system:menu:view",
        "system:menu:create",
        "system:menu:update",
        "system:menu:delete"
      })
  @SneakyThrows
  public void testMenu() {
    String uri = MENU_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysMenu entity = new SysMenu();
    entity.setName(RandomUtil.randomString(8));
    entity.setPath(RandomUtil.randomString(10));
    String content =
        testClient
            .post(uri, objectMapper.writeValueAsString(entity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_PATH_KEY).value(entity.getPath()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    JsonNode data = objectMapper.readTree(content);
    String id = data.get(DATA_FIELD).get(ID_FIELD).asText();
    entity.setId(id);
    log.info("-----> ID：{}", id);
    // get
    content =
        testClient
            .get(uri + "/" + id)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_PATH_KEY).value(entity.getPath()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String updateTime = data.get(DATA_FIELD).get(UPDATE_TIME_FIELD).asText();
    // update
    entity.setName(RandomUtil.randomString(10));
    entity.setUpdatedTime(OffsetDateTime.parse(updateTime));
    testClient
        .put(uri + "/" + id, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_ID_KEY).value(id))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(entity.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_PATH_KEY).value(entity.getPath()))
        .andReturn()
        .getResponse()
        .getContentAsString();
    // create child
    SysMenu childEntity = new SysMenu();
    childEntity.setName(RandomUtil.randomString(8));
    childEntity.setPath(RandomUtil.randomString(10));
    childEntity.setParentId(id);
    content =
        testClient
            .post(uri, objectMapper.writeValueAsString(childEntity))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isMap())
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_NAME_KEY).value(childEntity.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath(DATA_PATH_KEY).value(childEntity.getPath()))
            .andExpect(
                MockMvcResultMatchers.jsonPath(DATA_PARENT_ID_KEY).value(childEntity.getParentId()))
            .andReturn()
            .getResponse()
            .getContentAsString();
    data = objectMapper.readTree(content);
    String childId = data.get(DATA_FIELD).get(ID_FIELD).asText();
    childEntity.setId(id);
    // delete
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    testClient.delete(uri + "/" + childId).andExpect(MockMvcResultMatchers.status().isNoContent());
    testClient.delete(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNoContent());
    // get
    testClient.get(uri + "/" + id).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {"log:operation_log:view", "log:operation_log:create"})
  @SneakyThrows
  public void testOperationLog() {
    String uri = OPERATION_LOG_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysOperationLog entity = new SysOperationLog();
    entity.setEvent(RandomUtil.randomString(8));
    testClient
        .post(uri, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().is5xxServerError());
  }

  @Test
  @WithMockUser(
      id = "99",
      username = "user",
      staff = true,
      authorities = {"log:user_log:view", "log:user_log:create"})
  @SneakyThrows
  public void testUserLog() {
    String uri = USER_LOG_URL;
    // list
    testClient
        .get(uri)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(DATA_KEY).isArray())
        .andExpect(MockMvcResultMatchers.jsonPath(TOTAL_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_SIZE_KEY).isNumber())
        .andExpect(MockMvcResultMatchers.jsonPath(PAGE_KEY).isNumber());
    // create
    SysUserLog entity = new SysUserLog();
    entity.setEvent(RandomUtil.randomString(8));
    testClient
        .post(uri, objectMapper.writeValueAsString(entity))
        .andExpect(MockMvcResultMatchers.status().is5xxServerError());
  }
}
