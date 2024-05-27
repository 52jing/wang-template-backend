DROP TABLE IF EXISTS wb_sys_frontend;
CREATE TABLE wb_sys_frontend(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    name VARCHAR(128) NOT NULL,
    description TEXT NOT NULL,
    author VARCHAR(128) NOT NULL,
    domain VARCHAR(255) NOT NULL,
    client_type VARCHAR(32) NOT NULL,
    allow_register INTEGER(1) NOT NULL,
    staff_only INTEGER(1) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_frontend IS '前端';
COMMENT ON COLUMN wb_sys_frontend.id IS 'ID';
COMMENT ON COLUMN wb_sys_frontend.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_frontend.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_frontend.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_frontend.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_frontend.remark IS '备注';
COMMENT ON COLUMN wb_sys_frontend.name IS '名称';
COMMENT ON COLUMN wb_sys_frontend.description IS '描述';
COMMENT ON COLUMN wb_sys_frontend.author IS '作者';
COMMENT ON COLUMN wb_sys_frontend.domain IS '域名';
COMMENT ON COLUMN wb_sys_frontend.client_type IS '客户端类型';
COMMENT ON COLUMN wb_sys_frontend.allow_register IS '是否允许注册';
COMMENT ON COLUMN wb_sys_frontend.staff_only IS '是否内部使用';


CREATE INDEX created_by_idx ON wb_sys_frontend(created_by);
CREATE INDEX created_time_idx ON wb_sys_frontend(created_time);
CREATE INDEX updated_by_idx ON wb_sys_frontend(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_frontend(updated_time);
CREATE INDEX name_idx ON wb_sys_frontend(name);
CREATE INDEX client_type_idx ON wb_sys_frontend(client_type);

DROP TABLE IF EXISTS wb_sys_user;
CREATE TABLE wb_sys_user(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    username VARCHAR(128) NOT NULL,
    nickname VARCHAR(128) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    tel VARCHAR(128) NOT NULL,
    active INTEGER(1) NOT NULL,
    superuser INTEGER(1) NOT NULL,
    staff INTEGER(1) NOT NULL,
    expired_time TIMESTAMP,
    sex VARCHAR(255),
    department_id VARCHAR(64),
    job_id VARCHAR(64),
    province VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    area VARCHAR(255) NOT NULL,
    town VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    need_change_pwd INTEGER(1) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_user IS '用户';
COMMENT ON COLUMN wb_sys_user.id IS 'ID';
COMMENT ON COLUMN wb_sys_user.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_user.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_user.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_user.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_user.remark IS '备注';
COMMENT ON COLUMN wb_sys_user.username IS '用户名';
COMMENT ON COLUMN wb_sys_user.nickname IS '昵称';
COMMENT ON COLUMN wb_sys_user.password IS '密码';
COMMENT ON COLUMN wb_sys_user.email IS '邮箱';
COMMENT ON COLUMN wb_sys_user.tel IS '手机';
COMMENT ON COLUMN wb_sys_user.active IS '是否激活';
COMMENT ON COLUMN wb_sys_user.superuser IS '是否超级管理员';
COMMENT ON COLUMN wb_sys_user.staff IS '是否内部用户';
COMMENT ON COLUMN wb_sys_user.expired_time IS '失效时间';
COMMENT ON COLUMN wb_sys_user.sex IS '性别';
COMMENT ON COLUMN wb_sys_user.department_id IS '部门ID';
COMMENT ON COLUMN wb_sys_user.job_id IS '岗位ID';
COMMENT ON COLUMN wb_sys_user.province IS '省份';
COMMENT ON COLUMN wb_sys_user.city IS '城市';
COMMENT ON COLUMN wb_sys_user.area IS '区';
COMMENT ON COLUMN wb_sys_user.town IS '县';
COMMENT ON COLUMN wb_sys_user.address IS '详细地址';
COMMENT ON COLUMN wb_sys_user.need_change_pwd IS '是否需要修改密码';


CREATE INDEX created_by_idx ON wb_sys_user(created_by);
CREATE INDEX created_time_idx ON wb_sys_user(created_time);
CREATE INDEX updated_by_idx ON wb_sys_user(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_user(updated_time);
CREATE UNIQUE INDEX username_idx ON wb_sys_user(username);
CREATE INDEX email_idx ON wb_sys_user(email);
CREATE INDEX tel_idx ON wb_sys_user(tel);
CREATE INDEX active_idx ON wb_sys_user(active);
CREATE INDEX superuser_idx ON wb_sys_user(superuser);
CREATE INDEX staff_idx ON wb_sys_user(staff);
CREATE INDEX expired_time_idx ON wb_sys_user(expired_time);
CREATE INDEX department_id_idx ON wb_sys_user(department_id);
CREATE INDEX job_id_idx ON wb_sys_user(job_id);

DROP TABLE IF EXISTS wb_sys_department;
CREATE TABLE wb_sys_department(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    parent_id VARCHAR(64),
    sort INTEGER NOT NULL,
    name VARCHAR(128) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    leader VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_department IS '部门';
COMMENT ON COLUMN wb_sys_department.id IS 'ID';
COMMENT ON COLUMN wb_sys_department.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_department.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_department.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_department.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_department.remark IS '备注';
COMMENT ON COLUMN wb_sys_department.parent_id IS '父节点ID';
COMMENT ON COLUMN wb_sys_department.sort IS '排序';
COMMENT ON COLUMN wb_sys_department.name IS '名称';
COMMENT ON COLUMN wb_sys_department.fullname IS '全称';
COMMENT ON COLUMN wb_sys_department.leader IS '负责人';


CREATE INDEX created_by_idx ON wb_sys_department(created_by);
CREATE INDEX created_time_idx ON wb_sys_department(created_time);
CREATE INDEX updated_by_idx ON wb_sys_department(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_department(updated_time);
CREATE INDEX parent_id_idx ON wb_sys_department(parent_id);
CREATE INDEX sort_idx ON wb_sys_department(sort);
CREATE INDEX name_idx ON wb_sys_department(name);

DROP TABLE IF EXISTS wb_sys_job;
CREATE TABLE wb_sys_job(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    name VARCHAR(128) NOT NULL,
    type VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_job IS '岗位';
COMMENT ON COLUMN wb_sys_job.id IS 'ID';
COMMENT ON COLUMN wb_sys_job.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_job.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_job.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_job.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_job.remark IS '备注';
COMMENT ON COLUMN wb_sys_job.name IS '名称';
COMMENT ON COLUMN wb_sys_job.type IS '类型';


CREATE INDEX created_by_idx ON wb_sys_job(created_by);
CREATE INDEX created_time_idx ON wb_sys_job(created_time);
CREATE INDEX updated_by_idx ON wb_sys_job(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_job(updated_time);
CREATE INDEX name_idx ON wb_sys_job(name);
CREATE INDEX type_idx ON wb_sys_job(type);

DROP TABLE IF EXISTS wb_sys_role;
CREATE TABLE wb_sys_role(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_role IS '角色';
COMMENT ON COLUMN wb_sys_role.id IS 'ID';
COMMENT ON COLUMN wb_sys_role.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_role.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_role.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_role.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_role.remark IS '备注';
COMMENT ON COLUMN wb_sys_role.name IS '名称';


CREATE INDEX created_by_idx ON wb_sys_role(created_by);
CREATE INDEX created_time_idx ON wb_sys_role(created_time);
CREATE INDEX updated_by_idx ON wb_sys_role(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_role(updated_time);
CREATE INDEX name_idx ON wb_sys_role(name);

DROP TABLE IF EXISTS wb_sys_policy;
CREATE TABLE wb_sys_policy(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    name VARCHAR(128) NOT NULL,
    label VARCHAR(128) NOT NULL,
    readonly INTEGER(1) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_policy IS '策略';
COMMENT ON COLUMN wb_sys_policy.id IS 'ID';
COMMENT ON COLUMN wb_sys_policy.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_policy.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_policy.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_policy.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_policy.remark IS '备注';
COMMENT ON COLUMN wb_sys_policy.name IS '名称';
COMMENT ON COLUMN wb_sys_policy.label IS '显示名';
COMMENT ON COLUMN wb_sys_policy.readonly IS '是否只读';


CREATE INDEX created_by_idx ON wb_sys_policy(created_by);
CREATE INDEX created_time_idx ON wb_sys_policy(created_time);
CREATE INDEX updated_by_idx ON wb_sys_policy(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_policy(updated_time);
CREATE INDEX name_idx ON wb_sys_policy(name);

DROP TABLE IF EXISTS wb_sys_permission;
CREATE TABLE wb_sys_permission(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    name VARCHAR(128) NOT NULL,
    label VARCHAR(128) NOT NULL,
    readonly INTEGER(1) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_permission IS '权限';
COMMENT ON COLUMN wb_sys_permission.id IS 'ID';
COMMENT ON COLUMN wb_sys_permission.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_permission.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_permission.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_permission.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_permission.remark IS '备注';
COMMENT ON COLUMN wb_sys_permission.name IS '名称';
COMMENT ON COLUMN wb_sys_permission.label IS '显示名';
COMMENT ON COLUMN wb_sys_permission.readonly IS '是否只读';


CREATE INDEX created_by_idx ON wb_sys_permission(created_by);
CREATE INDEX created_time_idx ON wb_sys_permission(created_time);
CREATE INDEX updated_by_idx ON wb_sys_permission(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_permission(updated_time);
CREATE INDEX name_idx ON wb_sys_permission(name);

DROP TABLE IF EXISTS wb_sys_role_policy_rel;
CREATE TABLE wb_sys_role_policy_rel(
    id VARCHAR(64) NOT NULL,
    role_id VARCHAR(64) NOT NULL,
    policy_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_role_policy_rel IS '角色策略关系';
COMMENT ON COLUMN wb_sys_role_policy_rel.id IS 'ID';
COMMENT ON COLUMN wb_sys_role_policy_rel.role_id IS '角色ID';
COMMENT ON COLUMN wb_sys_role_policy_rel.policy_id IS '策略ID';


CREATE INDEX role_id_idx ON wb_sys_role_policy_rel(role_id);
CREATE INDEX policy_id_idx ON wb_sys_role_policy_rel(policy_id);
CREATE UNIQUE INDEX role_id_policy_id_idx ON wb_sys_role_policy_rel(role_id,policy_id);

DROP TABLE IF EXISTS wb_sys_policy_permission_rel;
CREATE TABLE wb_sys_policy_permission_rel(
    id VARCHAR(64) NOT NULL,
    policy_id VARCHAR(64) NOT NULL,
    permission_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_policy_permission_rel IS '策略权限关系';
COMMENT ON COLUMN wb_sys_policy_permission_rel.id IS 'ID';
COMMENT ON COLUMN wb_sys_policy_permission_rel.policy_id IS '策略ID';
COMMENT ON COLUMN wb_sys_policy_permission_rel.permission_id IS '权限ID';


CREATE INDEX policy_id_idx ON wb_sys_policy_permission_rel(policy_id);
CREATE INDEX permission_id_idx ON wb_sys_policy_permission_rel(permission_id);
CREATE UNIQUE INDEX policy_id_permission_id_idx ON wb_sys_policy_permission_rel(policy_id,permission_id);

DROP TABLE IF EXISTS wb_sys_data_scope;
CREATE TABLE wb_sys_data_scope(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_data_scope IS '数据权限';
COMMENT ON COLUMN wb_sys_data_scope.id IS 'ID';
COMMENT ON COLUMN wb_sys_data_scope.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_data_scope.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_data_scope.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_data_scope.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_data_scope.remark IS '备注';
COMMENT ON COLUMN wb_sys_data_scope.name IS '名称';


CREATE INDEX created_by_idx ON wb_sys_data_scope(created_by);
CREATE INDEX created_time_idx ON wb_sys_data_scope(created_time);
CREATE INDEX updated_by_idx ON wb_sys_data_scope(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_data_scope(updated_time);
CREATE INDEX name_idx ON wb_sys_data_scope(name);

DROP TABLE IF EXISTS wb_sys_user_role_rel;
CREATE TABLE wb_sys_user_role_rel(
    id VARCHAR(64) NOT NULL,
    user_id VARCHAR(64) NOT NULL,
    role_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_user_role_rel IS '用户角色关系';
COMMENT ON COLUMN wb_sys_user_role_rel.id IS 'ID';
COMMENT ON COLUMN wb_sys_user_role_rel.user_id IS '用户ID';
COMMENT ON COLUMN wb_sys_user_role_rel.role_id IS '角色ID';


CREATE INDEX user_id_idx ON wb_sys_user_role_rel(user_id);
CREATE INDEX role_id_idx ON wb_sys_user_role_rel(role_id);
CREATE INDEX user_id_role_id_idx ON wb_sys_user_role_rel(user_id,role_id);

DROP TABLE IF EXISTS wb_sys_policy_data_scope_rel;
CREATE TABLE wb_sys_policy_data_scope_rel(
    id VARCHAR(64) NOT NULL,
    policy_id VARCHAR(64) NOT NULL,
    data_scope_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_policy_data_scope_rel IS '策略数据权限关系';
COMMENT ON COLUMN wb_sys_policy_data_scope_rel.id IS 'ID';
COMMENT ON COLUMN wb_sys_policy_data_scope_rel.policy_id IS '策略ID';
COMMENT ON COLUMN wb_sys_policy_data_scope_rel.data_scope_id IS '数据权限ID';


CREATE INDEX policy_id_idx ON wb_sys_policy_data_scope_rel(policy_id);
CREATE INDEX datascope_id_idx ON wb_sys_policy_data_scope_rel(data_scope_id);
CREATE UNIQUE INDEX policy_id_datascope_id_idx ON wb_sys_policy_data_scope_rel(policy_id,data_scope_id);

DROP TABLE IF EXISTS wb_sys_menu;
CREATE TABLE wb_sys_menu(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    parent_id VARCHAR(64),
    sort INTEGER NOT NULL,
    name VARCHAR(128) NOT NULL,
    caption VARCHAR(128) NOT NULL,
    path VARCHAR(255) NOT NULL,
    icon VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_menu IS '菜单';
COMMENT ON COLUMN wb_sys_menu.id IS 'ID';
COMMENT ON COLUMN wb_sys_menu.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_menu.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_menu.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_menu.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_menu.remark IS '备注';
COMMENT ON COLUMN wb_sys_menu.parent_id IS '父节点ID';
COMMENT ON COLUMN wb_sys_menu.sort IS '排序';
COMMENT ON COLUMN wb_sys_menu.name IS '名称';
COMMENT ON COLUMN wb_sys_menu.caption IS '子标题';
COMMENT ON COLUMN wb_sys_menu.path IS '路径';
COMMENT ON COLUMN wb_sys_menu.icon IS '图标';


CREATE INDEX created_by_idx ON wb_sys_menu(created_by);
CREATE INDEX created_time_idx ON wb_sys_menu(created_time);
CREATE INDEX updated_by_idx ON wb_sys_menu(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_menu(updated_time);
CREATE INDEX parent_id_idx ON wb_sys_menu(parent_id);
CREATE INDEX sort_idx ON wb_sys_menu(sort);
CREATE INDEX name_idx ON wb_sys_menu(name);

DROP TABLE IF EXISTS wb_sys_policy_menu_rel;
CREATE TABLE wb_sys_policy_menu_rel(
    id VARCHAR(64) NOT NULL,
    policy_id VARCHAR(64) NOT NULL,
    menu_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_policy_menu_rel IS '策略菜单关系';
COMMENT ON COLUMN wb_sys_policy_menu_rel.id IS 'ID';
COMMENT ON COLUMN wb_sys_policy_menu_rel.policy_id IS '策略ID';
COMMENT ON COLUMN wb_sys_policy_menu_rel.menu_id IS '菜单ID';


CREATE INDEX policy_id_idx ON wb_sys_policy_menu_rel(policy_id);
CREATE INDEX menu_id_idx ON wb_sys_policy_menu_rel(menu_id);
CREATE UNIQUE INDEX policy_id_menu_id_idx ON wb_sys_policy_menu_rel(policy_id,menu_id);

DROP TABLE IF EXISTS wb_sys_param;
CREATE TABLE wb_sys_param(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    name VARCHAR(128) NOT NULL,
    param_group VARCHAR(128) NOT NULL,
    param_key VARCHAR(128) NOT NULL,
    param_val TEXT,
    param_type VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_param IS '配置';
COMMENT ON COLUMN wb_sys_param.id IS 'ID';
COMMENT ON COLUMN wb_sys_param.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_param.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_param.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_param.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_param.remark IS '备注';
COMMENT ON COLUMN wb_sys_param.name IS '名称';
COMMENT ON COLUMN wb_sys_param.param_group IS '配置组';
COMMENT ON COLUMN wb_sys_param.param_key IS '配置键';
COMMENT ON COLUMN wb_sys_param.param_val IS '配置值';
COMMENT ON COLUMN wb_sys_param.param_type IS '配置值类型';


CREATE INDEX created_by_idx ON wb_sys_param(created_by);
CREATE INDEX created_time_idx ON wb_sys_param(created_time);
CREATE INDEX updated_by_idx ON wb_sys_param(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_param(updated_time);
CREATE INDEX name_idx ON wb_sys_param(name);
CREATE INDEX param_group_idx ON wb_sys_param(param_group);
CREATE UNIQUE INDEX param_key_idx ON wb_sys_param(param_key);

DROP TABLE IF EXISTS wb_sys_user_dict;
CREATE TABLE wb_sys_user_dict(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    name VARCHAR(128) NOT NULL,
    dict_group VARCHAR(128) NOT NULL,
    dict_code VARCHAR(128) NOT NULL,
    dict_val TEXT,
    dict_type VARCHAR(32) NOT NULL,
    sort INTEGER NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_user_dict IS '用户字典';
COMMENT ON COLUMN wb_sys_user_dict.id IS 'ID';
COMMENT ON COLUMN wb_sys_user_dict.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_user_dict.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_user_dict.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_user_dict.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_user_dict.remark IS '备注';
COMMENT ON COLUMN wb_sys_user_dict.name IS '名称';
COMMENT ON COLUMN wb_sys_user_dict.dict_group IS '字典组';
COMMENT ON COLUMN wb_sys_user_dict.dict_code IS '字典代码';
COMMENT ON COLUMN wb_sys_user_dict.dict_val IS '字典值';
COMMENT ON COLUMN wb_sys_user_dict.dict_type IS '字典值类型';
COMMENT ON COLUMN wb_sys_user_dict.sort IS '排序';


CREATE INDEX created_by_idx ON wb_sys_user_dict(created_by);
CREATE INDEX created_time_idx ON wb_sys_user_dict(created_time);
CREATE INDEX updated_by_idx ON wb_sys_user_dict(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_user_dict(updated_time);
CREATE INDEX name_idx ON wb_sys_user_dict(name);
CREATE UNIQUE INDEX dict_group_dict_code_idx ON wb_sys_user_dict(dict_group,dict_code);

DROP TABLE IF EXISTS wb_sys_user_log;
CREATE TABLE wb_sys_user_log(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    event VARCHAR(32) NOT NULL,
    status VARCHAR(32) NOT NULL,
    message VARCHAR(255) NOT NULL,
    user_id VARCHAR(64),
    username VARCHAR(128) NOT NULL,
    frontend_id VARCHAR(64),
    frontend_name VARCHAR(128) NOT NULL,
    ip VARCHAR(255) NOT NULL,
    ua VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_user_log IS '用户活动日志';
COMMENT ON COLUMN wb_sys_user_log.id IS 'ID';
COMMENT ON COLUMN wb_sys_user_log.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_user_log.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_user_log.event IS '事件';
COMMENT ON COLUMN wb_sys_user_log.status IS '状态';
COMMENT ON COLUMN wb_sys_user_log.message IS '消息';
COMMENT ON COLUMN wb_sys_user_log.user_id IS '用户ID';
COMMENT ON COLUMN wb_sys_user_log.username IS '用户名';
COMMENT ON COLUMN wb_sys_user_log.frontend_id IS '前端ID';
COMMENT ON COLUMN wb_sys_user_log.frontend_name IS '前端名';
COMMENT ON COLUMN wb_sys_user_log.ip IS '来源IP';
COMMENT ON COLUMN wb_sys_user_log.ua IS '用户客户端';


CREATE INDEX created_by_idx ON wb_sys_user_log(created_by);
CREATE INDEX created_time_idx ON wb_sys_user_log(created_time);
CREATE INDEX event_idx ON wb_sys_user_log(event);
CREATE INDEX user_id_idx ON wb_sys_user_log(user_id);
CREATE INDEX frontend_id_idx ON wb_sys_user_log(frontend_id);
CREATE INDEX ip_idx ON wb_sys_user_log(ip);

DROP TABLE IF EXISTS wb_sys_operation_log;
CREATE TABLE wb_sys_operation_log(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    event VARCHAR(32) NOT NULL,
    resource VARCHAR(255) NOT NULL,
    resource_id VARCHAR(64) NOT NULL,
    obj TEXT,
    username VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_operation_log IS '操作日志';
COMMENT ON COLUMN wb_sys_operation_log.id IS 'ID';
COMMENT ON COLUMN wb_sys_operation_log.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_operation_log.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_operation_log.event IS '事件';
COMMENT ON COLUMN wb_sys_operation_log.resource IS '资源名称';
COMMENT ON COLUMN wb_sys_operation_log.resource_id IS '资源ID';
COMMENT ON COLUMN wb_sys_operation_log.obj IS '资源对象';
COMMENT ON COLUMN wb_sys_operation_log.username IS '用户名';


CREATE INDEX created_by_idx ON wb_sys_operation_log(created_by);
CREATE INDEX created_time_idx ON wb_sys_operation_log(created_time);
CREATE INDEX event_idx ON wb_sys_operation_log(event);
CREATE INDEX resource_resource_id_idx ON wb_sys_operation_log(resource,resource_id);
CREATE INDEX username_idx ON wb_sys_operation_log(username);

DROP TABLE IF EXISTS wb_task_quick_link;
CREATE TABLE wb_task_quick_link(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    name VARCHAR(128) NOT NULL,
    icon VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    sort INTEGER NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_task_quick_link IS '快捷菜单';
COMMENT ON COLUMN wb_task_quick_link.id IS 'ID';
COMMENT ON COLUMN wb_task_quick_link.created_by IS '创建人';
COMMENT ON COLUMN wb_task_quick_link.created_time IS '创建时间';
COMMENT ON COLUMN wb_task_quick_link.updated_by IS '更新人';
COMMENT ON COLUMN wb_task_quick_link.updated_time IS '更新时间';
COMMENT ON COLUMN wb_task_quick_link.remark IS '备注';
COMMENT ON COLUMN wb_task_quick_link.name IS '名称';
COMMENT ON COLUMN wb_task_quick_link.icon IS '图标';
COMMENT ON COLUMN wb_task_quick_link.path IS '路径';
COMMENT ON COLUMN wb_task_quick_link.sort IS '排序';


CREATE INDEX created_by_idx ON wb_task_quick_link(created_by);
CREATE INDEX created_time_idx ON wb_task_quick_link(created_time);
CREATE INDEX updated_by_idx ON wb_task_quick_link(updated_by);
CREATE INDEX updated_time_idx ON wb_task_quick_link(updated_time);
CREATE INDEX name_idx ON wb_task_quick_link(name);
CREATE INDEX sort_idx ON wb_task_quick_link(sort);

DROP TABLE IF EXISTS wb_task_inside_message;
CREATE TABLE wb_task_inside_message(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(32) NOT NULL,
    from_user_id VARCHAR(64),
    to_user_id VARCHAR(64) NOT NULL,
    read_time TIMESTAMP,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_task_inside_message IS '站内消息';
COMMENT ON COLUMN wb_task_inside_message.id IS 'ID';
COMMENT ON COLUMN wb_task_inside_message.created_by IS '创建人';
COMMENT ON COLUMN wb_task_inside_message.created_time IS '创建时间';
COMMENT ON COLUMN wb_task_inside_message.title IS '标题';
COMMENT ON COLUMN wb_task_inside_message.content IS '内容';
COMMENT ON COLUMN wb_task_inside_message.type IS '类型';
COMMENT ON COLUMN wb_task_inside_message.from_user_id IS '来源用户ID';
COMMENT ON COLUMN wb_task_inside_message.to_user_id IS '目标用户ID';
COMMENT ON COLUMN wb_task_inside_message.read_time IS '读取时间';


CREATE INDEX created_by_idx ON wb_task_inside_message(created_by);
CREATE INDEX created_time_idx ON wb_task_inside_message(created_time);
CREATE INDEX from_user_id_idx ON wb_task_inside_message(from_user_id);
CREATE INDEX to_user_id_idx ON wb_task_inside_message(to_user_id);

DROP TABLE IF EXISTS wb_sys_announcement;
CREATE TABLE wb_sys_announcement(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(32) NOT NULL,
    display INTEGER(1) NOT NULL,
    sort INTEGER NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_announcement IS '公告';
COMMENT ON COLUMN wb_sys_announcement.id IS 'ID';
COMMENT ON COLUMN wb_sys_announcement.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_announcement.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_announcement.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_announcement.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_announcement.remark IS '备注';
COMMENT ON COLUMN wb_sys_announcement.title IS '标题';
COMMENT ON COLUMN wb_sys_announcement.content IS '内容';
COMMENT ON COLUMN wb_sys_announcement.type IS '类型';
COMMENT ON COLUMN wb_sys_announcement.display IS '是否展示';
COMMENT ON COLUMN wb_sys_announcement.sort IS '排序';


CREATE INDEX created_by_idx ON wb_sys_announcement(created_by);
CREATE INDEX created_time_idx ON wb_sys_announcement(created_time);
CREATE INDEX updated_by_idx ON wb_sys_announcement(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_announcement(updated_time);
CREATE INDEX type_display_idx ON wb_sys_announcement(type,display);
CREATE INDEX sort_idx ON wb_sys_announcement(sort);

DROP TABLE IF EXISTS wb_sys_attachment;
CREATE TABLE wb_sys_attachment(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    url VARCHAR(512) NOT NULL,
    size bigint(20) NOT NULL,
    filename VARCHAR(255) NOT NULL,
    original_filename VARCHAR(255) NOT NULL,
    base_path VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    ext VARCHAR(128) NOT NULL,
    content_type VARCHAR(128) NOT NULL,
    platform VARCHAR(128) NOT NULL,
    th_url VARCHAR(512) NOT NULL,
    th_filename VARCHAR(255) NOT NULL,
    th_size bigint(20) NOT NULL,
    th_content_type VARCHAR(128) NOT NULL,
    hash_info TEXT NOT NULL,
    attr TEXT NOT NULL,
    object_id VARCHAR(64),
    object_type VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_attachment IS '附件';
COMMENT ON COLUMN wb_sys_attachment.id IS 'ID';
COMMENT ON COLUMN wb_sys_attachment.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_attachment.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_attachment.url IS '文件访问地址';
COMMENT ON COLUMN wb_sys_attachment.size IS '文件大小';
COMMENT ON COLUMN wb_sys_attachment.filename IS '文件名称';
COMMENT ON COLUMN wb_sys_attachment.original_filename IS '原始文件名';
COMMENT ON COLUMN wb_sys_attachment.base_path IS '基础存储路径';
COMMENT ON COLUMN wb_sys_attachment.path IS '存储路径';
COMMENT ON COLUMN wb_sys_attachment.ext IS '文件扩展名';
COMMENT ON COLUMN wb_sys_attachment.content_type IS 'MIME类型';
COMMENT ON COLUMN wb_sys_attachment.platform IS '存储平台';
COMMENT ON COLUMN wb_sys_attachment.th_url IS '缩略图访问路径';
COMMENT ON COLUMN wb_sys_attachment.th_filename IS '缩略图名称';
COMMENT ON COLUMN wb_sys_attachment.th_size IS '缩略图大小';
COMMENT ON COLUMN wb_sys_attachment.th_content_type IS '缩略图MIME类型';
COMMENT ON COLUMN wb_sys_attachment.hash_info IS '哈希信息';
COMMENT ON COLUMN wb_sys_attachment.attr IS '附加属性';
COMMENT ON COLUMN wb_sys_attachment.object_id IS '关联对象ID';
COMMENT ON COLUMN wb_sys_attachment.object_type IS '关联对象类型';


CREATE INDEX created_by_idx ON wb_sys_attachment(created_by);
CREATE INDEX created_time_idx ON wb_sys_attachment(created_time);
CREATE INDEX url_idx ON wb_sys_attachment(url);
CREATE INDEX content_type_idx ON wb_sys_attachment(content_type);
CREATE INDEX platform_idx ON wb_sys_attachment(platform);
CREATE INDEX object_idx ON wb_sys_attachment(object_type,object_id);

DROP TABLE IF EXISTS wb_sys_bg_task;
CREATE TABLE wb_sys_bg_task(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(512) NOT NULL,
    name VARCHAR(128) NOT NULL,
    type VARCHAR(32) NOT NULL,
    status VARCHAR(32) NOT NULL,
    attachment_id VARCHAR(64),
    result VARCHAR(512) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_sys_bg_task IS '后台任务';
COMMENT ON COLUMN wb_sys_bg_task.id IS 'ID';
COMMENT ON COLUMN wb_sys_bg_task.created_by IS '创建人';
COMMENT ON COLUMN wb_sys_bg_task.created_time IS '创建时间';
COMMENT ON COLUMN wb_sys_bg_task.updated_by IS '更新人';
COMMENT ON COLUMN wb_sys_bg_task.updated_time IS '更新时间';
COMMENT ON COLUMN wb_sys_bg_task.remark IS '备注';
COMMENT ON COLUMN wb_sys_bg_task.name IS '名称';
COMMENT ON COLUMN wb_sys_bg_task.type IS '类型';
COMMENT ON COLUMN wb_sys_bg_task.status IS '状态';
COMMENT ON COLUMN wb_sys_bg_task.attachment_id IS '关联附件ID';
COMMENT ON COLUMN wb_sys_bg_task.result IS '结果';


CREATE INDEX created_by_idx ON wb_sys_bg_task(created_by);
CREATE INDEX created_time_idx ON wb_sys_bg_task(created_time);
CREATE INDEX updated_by_idx ON wb_sys_bg_task(updated_by);
CREATE INDEX updated_time_idx ON wb_sys_bg_task(updated_time);
CREATE INDEX type_idx ON wb_sys_bg_task(type);
CREATE INDEX status_idx ON wb_sys_bg_task(status);


-- views
create or replace view wb_sys_user_view as
select u.id, u.username, u.nickname, u.sex, ud.dict_val as sex_val,
u.email, u.tel, u.active, u.superuser, u.staff, u.expired_time,
u.province, u.city, u.area, u.town, u.address, u.need_change_pwd,
u.remark, u.created_by, u.created_time, u.updated_by, u.updated_time,
u.department_id, d.name as department, u.job_id, j.name as job
from wb_sys_user u left join wb_sys_department d
on u.department_id = d.id
left join wb_sys_job j
on u.job_id = j.id
left join wb_sys_user_dict ud
on u.sex = ud.id;
