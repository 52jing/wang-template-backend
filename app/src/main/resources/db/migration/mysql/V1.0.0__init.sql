DROP TABLE IF EXISTS wb_sys_frontend;
CREATE TABLE wb_sys_frontend(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `description` TEXT NOT NULL  COMMENT '描述' ,
    `author` VARCHAR(128) NOT NULL  COMMENT '作者' ,
    `domain` VARCHAR(255) NOT NULL  COMMENT '域名' ,
    `client_type` VARCHAR(32) NOT NULL  COMMENT '客户端类型' ,
    `allow_register` BOOLEAN NOT NULL  COMMENT '是否允许注册' ,
    `staff_only` BOOLEAN NOT NULL  COMMENT '是否内部使用' ,
    PRIMARY KEY (id)
)  COMMENT = '前端';


CREATE INDEX wb_sys_frontend_created_by_idx ON wb_sys_frontend(created_by);
CREATE INDEX wb_sys_frontend_created_time_idx ON wb_sys_frontend(created_time);
CREATE INDEX wb_sys_frontend_updated_by_idx ON wb_sys_frontend(updated_by);
CREATE INDEX wb_sys_frontend_updated_time_idx ON wb_sys_frontend(updated_time);
CREATE INDEX wb_sys_frontend_name_idx ON wb_sys_frontend(name);
CREATE INDEX wb_sys_frontend_client_type_idx ON wb_sys_frontend(client_type);

DROP TABLE IF EXISTS wb_sys_user;
CREATE TABLE wb_sys_user(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `username` VARCHAR(128) NOT NULL  COMMENT '用户名' ,
    `nickname` VARCHAR(128) NOT NULL  COMMENT '昵称' ,
    `password` VARCHAR(255) NOT NULL  COMMENT '密码' ,
    `email` VARCHAR(255) NOT NULL  COMMENT '邮箱' ,
    `tel` VARCHAR(128) NOT NULL  COMMENT '手机' ,
    `active` BOOLEAN NOT NULL  COMMENT '是否激活' ,
    `superuser` BOOLEAN NOT NULL  COMMENT '是否超级管理员' ,
    `staff` BOOLEAN NOT NULL  COMMENT '是否内部用户' ,
    `expired_time` DATETIME   COMMENT '失效时间' ,
    `sex` VARCHAR(255)   COMMENT '性别' ,
    `department_id` VARCHAR(64)   COMMENT '部门ID' ,
    `job_id` VARCHAR(64)   COMMENT '岗位ID' ,
    `province` VARCHAR(255) NOT NULL  COMMENT '省份' ,
    `city` VARCHAR(255) NOT NULL  COMMENT '城市' ,
    `area` VARCHAR(255) NOT NULL  COMMENT '区' ,
    `town` VARCHAR(255) NOT NULL  COMMENT '县' ,
    `address` TEXT NOT NULL  COMMENT '详细地址' ,
    `need_change_pwd` BOOLEAN NOT NULL  COMMENT '是否需要修改密码' ,
    PRIMARY KEY (id)
)  COMMENT = '用户';


CREATE INDEX wb_sys_user_created_by_idx ON wb_sys_user(created_by);
CREATE INDEX wb_sys_user_created_time_idx ON wb_sys_user(created_time);
CREATE INDEX wb_sys_user_updated_by_idx ON wb_sys_user(updated_by);
CREATE INDEX wb_sys_user_updated_time_idx ON wb_sys_user(updated_time);
CREATE UNIQUE INDEX wb_sys_user_username_idx ON wb_sys_user(username);
CREATE INDEX wb_sys_user_email_idx ON wb_sys_user(email);
CREATE INDEX wb_sys_user_tel_idx ON wb_sys_user(tel);
CREATE INDEX wb_sys_user_active_idx ON wb_sys_user(active);
CREATE INDEX wb_sys_user_superuser_idx ON wb_sys_user(superuser);
CREATE INDEX wb_sys_user_staff_idx ON wb_sys_user(staff);
CREATE INDEX wb_sys_user_expired_time_idx ON wb_sys_user(expired_time);
CREATE INDEX wb_sys_user_department_id_idx ON wb_sys_user(department_id);
CREATE INDEX wb_sys_user_job_id_idx ON wb_sys_user(job_id);

DROP TABLE IF EXISTS wb_sys_department;
CREATE TABLE wb_sys_department(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `parent_id` VARCHAR(64)   COMMENT '父节点ID' ,
    `sort` INT NOT NULL  COMMENT '排序' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `fullname` VARCHAR(255) NOT NULL  COMMENT '全称' ,
    `leader` VARCHAR(128) NOT NULL  COMMENT '负责人' ,
    PRIMARY KEY (id)
)  COMMENT = '部门';


CREATE INDEX wb_sys_department_created_by_idx ON wb_sys_department(created_by);
CREATE INDEX wb_sys_department_created_time_idx ON wb_sys_department(created_time);
CREATE INDEX wb_sys_department_updated_by_idx ON wb_sys_department(updated_by);
CREATE INDEX wb_sys_department_updated_time_idx ON wb_sys_department(updated_time);
CREATE INDEX wb_sys_department_parent_id_idx ON wb_sys_department(parent_id);
CREATE INDEX wb_sys_department_sort_idx ON wb_sys_department(sort);
CREATE INDEX wb_sys_department_name_idx ON wb_sys_department(name);

DROP TABLE IF EXISTS wb_sys_job;
CREATE TABLE wb_sys_job(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `type` VARCHAR(128) NOT NULL  COMMENT '类型' ,
    PRIMARY KEY (id)
)  COMMENT = '岗位';


CREATE INDEX wb_sys_job_created_by_idx ON wb_sys_job(created_by);
CREATE INDEX wb_sys_job_created_time_idx ON wb_sys_job(created_time);
CREATE INDEX wb_sys_job_updated_by_idx ON wb_sys_job(updated_by);
CREATE INDEX wb_sys_job_updated_time_idx ON wb_sys_job(updated_time);
CREATE INDEX wb_sys_job_name_idx ON wb_sys_job(name);
CREATE INDEX wb_sys_job_type_idx ON wb_sys_job(type);

DROP TABLE IF EXISTS wb_sys_role;
CREATE TABLE wb_sys_role(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    PRIMARY KEY (id)
)  COMMENT = '角色';


CREATE INDEX wb_sys_role_created_by_idx ON wb_sys_role(created_by);
CREATE INDEX wb_sys_role_created_time_idx ON wb_sys_role(created_time);
CREATE INDEX wb_sys_role_updated_by_idx ON wb_sys_role(updated_by);
CREATE INDEX wb_sys_role_updated_time_idx ON wb_sys_role(updated_time);
CREATE INDEX wb_sys_role_name_idx ON wb_sys_role(name);

DROP TABLE IF EXISTS wb_sys_policy;
CREATE TABLE wb_sys_policy(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `label` VARCHAR(128) NOT NULL  COMMENT '显示名' ,
    `readonly` BOOLEAN NOT NULL  COMMENT '是否只读' ,
    PRIMARY KEY (id)
)  COMMENT = '策略';


CREATE INDEX wb_sys_policy_created_by_idx ON wb_sys_policy(created_by);
CREATE INDEX wb_sys_policy_created_time_idx ON wb_sys_policy(created_time);
CREATE INDEX wb_sys_policy_updated_by_idx ON wb_sys_policy(updated_by);
CREATE INDEX wb_sys_policy_updated_time_idx ON wb_sys_policy(updated_time);
CREATE INDEX wb_sys_policy_name_idx ON wb_sys_policy(name);

DROP TABLE IF EXISTS wb_sys_permission;
CREATE TABLE wb_sys_permission(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `label` VARCHAR(128) NOT NULL  COMMENT '显示名' ,
    `readonly` BOOLEAN NOT NULL  COMMENT '是否只读' ,
    PRIMARY KEY (id)
)  COMMENT = '权限';


CREATE INDEX wb_sys_permission_created_by_idx ON wb_sys_permission(created_by);
CREATE INDEX wb_sys_permission_created_time_idx ON wb_sys_permission(created_time);
CREATE INDEX wb_sys_permission_updated_by_idx ON wb_sys_permission(updated_by);
CREATE INDEX wb_sys_permission_updated_time_idx ON wb_sys_permission(updated_time);
CREATE INDEX wb_sys_permission_name_idx ON wb_sys_permission(name);

DROP TABLE IF EXISTS wb_sys_role_policy_rel;
CREATE TABLE wb_sys_role_policy_rel(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `role_id` VARCHAR(64) NOT NULL  COMMENT '角色ID' ,
    `policy_id` VARCHAR(64) NOT NULL  COMMENT '策略ID' ,
    PRIMARY KEY (id)
)  COMMENT = '角色策略关系';


CREATE INDEX wb_sys_role_policy_rel_role_id_idx ON wb_sys_role_policy_rel(role_id);
CREATE INDEX wb_sys_role_policy_rel_policy_id_idx ON wb_sys_role_policy_rel(policy_id);
CREATE UNIQUE INDEX wb_sys_role_policy_rel_role_id_policy_id_idx ON wb_sys_role_policy_rel(role_id,policy_id);

DROP TABLE IF EXISTS wb_sys_policy_permission_rel;
CREATE TABLE wb_sys_policy_permission_rel(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `policy_id` VARCHAR(64) NOT NULL  COMMENT '策略ID' ,
    `permission_id` VARCHAR(64) NOT NULL  COMMENT '权限ID' ,
    PRIMARY KEY (id)
)  COMMENT = '策略权限关系';


CREATE INDEX wb_sys_policy_permission_policy_id_idx ON wb_sys_policy_permission_rel(policy_id);
CREATE INDEX wb_sys_policy_permission_permission_id_idx ON wb_sys_policy_permission_rel(permission_id);
CREATE UNIQUE INDEX wb_sys_policy_permission_policy_id_permission_id_idx ON wb_sys_policy_permission_rel(policy_id,permission_id);

DROP TABLE IF EXISTS wb_sys_data_scope;
CREATE TABLE wb_sys_data_scope(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    PRIMARY KEY (id)
)  COMMENT = '数据权限';


CREATE INDEX wb_sys_data_scope_created_by_idx ON wb_sys_data_scope(created_by);
CREATE INDEX wb_sys_data_scope_created_time_idx ON wb_sys_data_scope(created_time);
CREATE INDEX wb_sys_data_scope_updated_by_idx ON wb_sys_data_scope(updated_by);
CREATE INDEX wb_sys_data_scope_updated_time_idx ON wb_sys_data_scope(updated_time);
CREATE INDEX wb_sys_data_scope_name_idx ON wb_sys_data_scope(name);

DROP TABLE IF EXISTS wb_sys_user_role_rel;
CREATE TABLE wb_sys_user_role_rel(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `user_id` VARCHAR(64) NOT NULL  COMMENT '用户ID' ,
    `role_id` VARCHAR(64) NOT NULL  COMMENT '角色ID' ,
    PRIMARY KEY (id)
)  COMMENT = '用户角色关系';


CREATE INDEX wb_sys_user_role_rel_user_id_idx ON wb_sys_user_role_rel(user_id);
CREATE INDEX wb_sys_user_role_rel_role_id_idx ON wb_sys_user_role_rel(role_id);
CREATE INDEX wb_sys_user_role_rel_user_id_role_id_idx ON wb_sys_user_role_rel(user_id,role_id);

DROP TABLE IF EXISTS wb_sys_policy_data_scope_rel;
CREATE TABLE wb_sys_policy_data_scope_rel(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `policy_id` VARCHAR(64) NOT NULL  COMMENT '策略ID' ,
    `data_scope_id` VARCHAR(64) NOT NULL  COMMENT '数据权限ID' ,
    PRIMARY KEY (id)
)  COMMENT = '策略数据权限关系';


CREATE INDEX wb_sys_policy_data_scope_rel_policy_id_idx ON wb_sys_policy_data_scope_rel(policy_id);
CREATE INDEX wb_sys_policy_data_scope_rel_datascope_id_idx ON wb_sys_policy_data_scope_rel(data_scope_id);
CREATE UNIQUE INDEX wb_sys_policy_data_scope_rel_policy_id_datascope_id_idx ON wb_sys_policy_data_scope_rel(policy_id,data_scope_id);

DROP TABLE IF EXISTS wb_sys_menu;
CREATE TABLE wb_sys_menu(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `parent_id` VARCHAR(64)   COMMENT '父节点ID' ,
    `sort` INT NOT NULL  COMMENT '排序' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `caption` VARCHAR(128) NOT NULL  COMMENT '子标题' ,
    `path` VARCHAR(255) NOT NULL  COMMENT '路径' ,
    `icon` VARCHAR(255) NOT NULL  COMMENT '图标' ,
    PRIMARY KEY (id)
)  COMMENT = '菜单';


CREATE INDEX wb_sys_menu_created_by_idx ON wb_sys_menu(created_by);
CREATE INDEX wb_sys_menu_created_time_idx ON wb_sys_menu(created_time);
CREATE INDEX wb_sys_menu_updated_by_idx ON wb_sys_menu(updated_by);
CREATE INDEX wb_sys_menu_updated_time_idx ON wb_sys_menu(updated_time);
CREATE INDEX wb_sys_menu_parent_id_idx ON wb_sys_menu(parent_id);
CREATE INDEX wb_sys_menu_sort_idx ON wb_sys_menu(sort);
CREATE INDEX wb_sys_menu_name_idx ON wb_sys_menu(name);

DROP TABLE IF EXISTS wb_sys_policy_menu_rel;
CREATE TABLE wb_sys_policy_menu_rel(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `policy_id` VARCHAR(64) NOT NULL  COMMENT '策略ID' ,
    `menu_id` VARCHAR(64) NOT NULL  COMMENT '菜单ID' ,
    PRIMARY KEY (id)
)  COMMENT = '策略菜单关系';


CREATE INDEX wb_sys_policy_menu_rel_policy_id_idx ON wb_sys_policy_menu_rel(policy_id);
CREATE INDEX wb_sys_policy_menu_rel_menu_id_idx ON wb_sys_policy_menu_rel(menu_id);
CREATE UNIQUE INDEX wb_sys_policy_menu_rel_policy_id_menu_id_idx ON wb_sys_policy_menu_rel(policy_id,menu_id);

DROP TABLE IF EXISTS wb_sys_param;
CREATE TABLE wb_sys_param(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `param_group` VARCHAR(128) NOT NULL  COMMENT '配置组' ,
    `param_key` VARCHAR(128) NOT NULL  COMMENT '配置键' ,
    `param_val` TEXT   COMMENT '配置值' ,
    `param_type` VARCHAR(32) NOT NULL  COMMENT '配置值类型' ,
    PRIMARY KEY (id)
)  COMMENT = '配置';


CREATE INDEX wb_sys_param_created_by_idx ON wb_sys_param(created_by);
CREATE INDEX wb_sys_param_created_time_idx ON wb_sys_param(created_time);
CREATE INDEX wb_sys_param_updated_by_idx ON wb_sys_param(updated_by);
CREATE INDEX wb_sys_param_updated_time_idx ON wb_sys_param(updated_time);
CREATE INDEX wb_sys_param_name_idx ON wb_sys_param(name);
CREATE INDEX wb_sys_param_param_group_idx ON wb_sys_param(param_group);
CREATE UNIQUE INDEX wb_sys_param_param_key_idx ON wb_sys_param(param_key);

DROP TABLE IF EXISTS wb_sys_user_dict;
CREATE TABLE wb_sys_user_dict(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `dict_group` VARCHAR(128) NOT NULL  COMMENT '字典组' ,
    `dict_code` VARCHAR(128) NOT NULL  COMMENT '字典代码' ,
    `dict_val` TEXT   COMMENT '字典值' ,
    `dict_type` VARCHAR(32) NOT NULL  COMMENT '字典值类型' ,
    `sort` INT NOT NULL  COMMENT '排序' ,
    PRIMARY KEY (id)
)  COMMENT = '用户字典';


CREATE INDEX wb_sys_user_dict_created_by_idx ON wb_sys_user_dict(created_by);
CREATE INDEX wb_sys_user_dict_created_time_idx ON wb_sys_user_dict(created_time);
CREATE INDEX wb_sys_user_dict_updated_by_idx ON wb_sys_user_dict(updated_by);
CREATE INDEX wb_sys_user_dict_updated_time_idx ON wb_sys_user_dict(updated_time);
CREATE INDEX wb_sys_user_dict_name_idx ON wb_sys_user_dict(name);
CREATE UNIQUE INDEX wb_sys_user_dict_dict_group_dict_code_idx ON wb_sys_user_dict(dict_group,dict_code);

DROP TABLE IF EXISTS wb_sys_user_log;
CREATE TABLE wb_sys_user_log(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `event` VARCHAR(32) NOT NULL  COMMENT '事件' ,
    `status` VARCHAR(32) NOT NULL  COMMENT '状态' ,
    `message` VARCHAR(255) NOT NULL  COMMENT '消息' ,
    `user_id` VARCHAR(64)   COMMENT '用户ID' ,
    `username` VARCHAR(128) NOT NULL  COMMENT '用户名' ,
    `frontend_id` VARCHAR(64)   COMMENT '前端ID' ,
    `frontend_name` VARCHAR(128) NOT NULL  COMMENT '前端名' ,
    `ip` VARCHAR(255) NOT NULL  COMMENT '来源IP' ,
    `ua` VARCHAR(255) NOT NULL  COMMENT '用户客户端' ,
    PRIMARY KEY (id)
)  COMMENT = '用户活动日志';


CREATE INDEX wb_sys_user_log_created_by_idx ON wb_sys_user_log(created_by);
CREATE INDEX wb_sys_user_log_created_time_idx ON wb_sys_user_log(created_time);
CREATE INDEX wb_sys_user_log_event_idx ON wb_sys_user_log(event);
CREATE INDEX wb_sys_user_log_user_id_idx ON wb_sys_user_log(user_id);
CREATE INDEX wb_sys_user_log_frontend_id_idx ON wb_sys_user_log(frontend_id);
CREATE INDEX wb_sys_user_log_ip_idx ON wb_sys_user_log(ip);

DROP TABLE IF EXISTS wb_sys_operation_log;
CREATE TABLE wb_sys_operation_log(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `event` VARCHAR(32) NOT NULL  COMMENT '事件' ,
    `resource` VARCHAR(255) NOT NULL  COMMENT '资源名称' ,
    `resource_id` VARCHAR(64) NOT NULL  COMMENT '资源ID' ,
    `obj` TEXT   COMMENT '资源对象' ,
    `username` VARCHAR(128) NOT NULL  COMMENT '用户名' ,
    PRIMARY KEY (id)
)  COMMENT = '操作日志';


CREATE INDEX wb_sys_operation_log_created_by_idx ON wb_sys_operation_log(created_by);
CREATE INDEX wb_sys_operation_log_created_time_idx ON wb_sys_operation_log(created_time);
CREATE INDEX wb_sys_operation_log_event_idx ON wb_sys_operation_log(event);
CREATE INDEX wb_sys_operation_log_resource_resource_id_idx ON wb_sys_operation_log(resource,resource_id);
CREATE INDEX wb_sys_operation_log_username_idx ON wb_sys_operation_log(username);

DROP TABLE IF EXISTS wb_task_quick_link;
CREATE TABLE wb_task_quick_link(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `icon` VARCHAR(255) NOT NULL  COMMENT '图标' ,
    `path` VARCHAR(255) NOT NULL  COMMENT '路径' ,
    `sort` INT NOT NULL  COMMENT '排序' ,
    PRIMARY KEY (id)
)  COMMENT = '快捷菜单';


CREATE INDEX wb_task_quick_link_created_by_idx ON wb_task_quick_link(created_by);
CREATE INDEX wb_task_quick_link_created_time_idx ON wb_task_quick_link(created_time);
CREATE INDEX wb_task_quick_link_updated_by_idx ON wb_task_quick_link(updated_by);
CREATE INDEX wb_task_quick_link_updated_time_idx ON wb_task_quick_link(updated_time);
CREATE INDEX wb_task_quick_link_name_idx ON wb_task_quick_link(name);
CREATE INDEX wb_task_quick_link_sort_idx ON wb_task_quick_link(sort);

DROP TABLE IF EXISTS wb_task_inside_message;
CREATE TABLE wb_task_inside_message(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `title` VARCHAR(255) NOT NULL  COMMENT '标题' ,
    `content` TEXT NOT NULL  COMMENT '内容' ,
    `type` VARCHAR(32) NOT NULL  COMMENT '类型' ,
    `from_user_id` VARCHAR(64)   COMMENT '来源用户ID' ,
    `to_user_id` VARCHAR(64) NOT NULL  COMMENT '目标用户ID' ,
    `read_time` DATETIME   COMMENT '读取时间' ,
    PRIMARY KEY (id)
)  COMMENT = '站内消息';


CREATE INDEX wb_task_inside_message_created_by_idx ON wb_task_inside_message(created_by);
CREATE INDEX wb_task_inside_message_created_time_idx ON wb_task_inside_message(created_time);
CREATE INDEX wb_task_inside_message_from_user_id_idx ON wb_task_inside_message(from_user_id);
CREATE INDEX wb_task_inside_message_to_user_id_idx ON wb_task_inside_message(to_user_id);

DROP TABLE IF EXISTS wb_sys_announcement;
CREATE TABLE wb_sys_announcement(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `title` VARCHAR(255) NOT NULL  COMMENT '标题' ,
    `content` TEXT NOT NULL  COMMENT '内容' ,
    `type` VARCHAR(32) NOT NULL  COMMENT '类型' ,
    `display` BOOLEAN NOT NULL  COMMENT '是否展示' ,
    `sort` INT NOT NULL  COMMENT '排序' ,
    PRIMARY KEY (id)
)  COMMENT = '公告';


CREATE INDEX wb_sys_announcement_created_by_idx ON wb_sys_announcement(created_by);
CREATE INDEX wb_sys_announcement_created_time_idx ON wb_sys_announcement(created_time);
CREATE INDEX wb_sys_announcement_updated_by_idx ON wb_sys_announcement(updated_by);
CREATE INDEX wb_sys_announcement_updated_time_idx ON wb_sys_announcement(updated_time);
CREATE INDEX wb_sys_announcement_type_display_idx ON wb_sys_announcement(type,display);
CREATE INDEX wb_sys_announcement_sort_idx ON wb_sys_announcement(sort);

DROP TABLE IF EXISTS wb_sys_attachment;
CREATE TABLE wb_sys_attachment(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `url` VARCHAR(512) NOT NULL  COMMENT '文件访问地址' ,
    `size` BIGINT NOT NULL  COMMENT '文件大小' ,
    `filename` VARCHAR(255) NOT NULL  COMMENT '文件名称' ,
    `original_filename` VARCHAR(255) NOT NULL  COMMENT '原始文件名' ,
    `base_path` VARCHAR(255) NOT NULL  COMMENT '基础存储路径' ,
    `path` VARCHAR(255) NOT NULL  COMMENT '存储路径' ,
    `ext` VARCHAR(128) NOT NULL  COMMENT '文件扩展名' ,
    `content_type` VARCHAR(128) NOT NULL  COMMENT 'MIME类型' ,
    `platform` VARCHAR(128) NOT NULL  COMMENT '存储平台' ,
    `th_url` VARCHAR(512) NOT NULL  COMMENT '缩略图访问路径' ,
    `th_filename` VARCHAR(255) NOT NULL  COMMENT '缩略图名称' ,
    `th_size` BIGINT NOT NULL  COMMENT '缩略图大小' ,
    `th_content_type` VARCHAR(128) NOT NULL  COMMENT '缩略图MIME类型' ,
    `hash_info` TEXT NOT NULL  COMMENT '哈希信息' ,
    `attr` TEXT NOT NULL  COMMENT '附加属性' ,
    `object_id` VARCHAR(64)   COMMENT '关联对象ID' ,
    `object_type` VARCHAR(255) NOT NULL  COMMENT '关联对象类型' ,
    `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标示' ,
    PRIMARY KEY (id)
)  COMMENT = '附件';


CREATE INDEX wb_sys_attachment_created_by_idx ON wb_sys_attachment(created_by);
CREATE INDEX wb_sys_attachment_created_time_idx ON wb_sys_attachment(created_time);
CREATE INDEX wb_sys_attachment_url_idx ON wb_sys_attachment(url);
CREATE INDEX wb_sys_attachment_content_type_idx ON wb_sys_attachment(content_type);
CREATE INDEX wb_sys_attachment_platform_idx ON wb_sys_attachment(platform);
CREATE INDEX wb_sys_attachment_object_idx ON wb_sys_attachment(object_type,object_id);
CREATE INDEX wb_sys_attachment_deleted_idx ON wb_sys_attachment(deleted);

DROP TABLE IF EXISTS wb_sys_bg_task;
CREATE TABLE wb_sys_bg_task(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `type` VARCHAR(32) NOT NULL  COMMENT '类型' ,
    `status` VARCHAR(32) NOT NULL  COMMENT '状态' ,
    `result` VARCHAR(512) NOT NULL  COMMENT '结果' ,
    PRIMARY KEY (id)
)  COMMENT = '后台任务';


CREATE INDEX wb_sys_bg_task_created_by_idx ON wb_sys_bg_task(created_by);
CREATE INDEX wb_sys_bg_task_created_time_idx ON wb_sys_bg_task(created_time);
CREATE INDEX wb_sys_bg_task_updated_by_idx ON wb_sys_bg_task(updated_by);
CREATE INDEX wb_sys_bg_task_updated_time_idx ON wb_sys_bg_task(updated_time);
CREATE INDEX wb_sys_bg_task_type_idx ON wb_sys_bg_task(type);
CREATE INDEX wb_sys_bg_task_status_idx ON wb_sys_bg_task(status);


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
