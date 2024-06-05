DROP TABLE IF EXISTS wb_sys_frontend;
CREATE TABLE wb_sys_frontend(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    description TEXT NOT NULL  , --描述
    author TEXT(128) NOT NULL  , --作者
    domain TEXT(255) NOT NULL  , --域名
    client_type TEXT(32) NOT NULL  , --客户端类型
    allow_register tinyint NOT NULL  , --是否允许注册
    staff_only tinyint NOT NULL  , --是否内部使用
    PRIMARY KEY (id)
)  ; --前端


CREATE INDEX wb_sys_frontend_created_by_idx ON wb_sys_frontend(created_by);
CREATE INDEX wb_sys_frontend_created_time_idx ON wb_sys_frontend(created_time);
CREATE INDEX wb_sys_frontend_updated_by_idx ON wb_sys_frontend(updated_by);
CREATE INDEX wb_sys_frontend_updated_time_idx ON wb_sys_frontend(updated_time);
CREATE INDEX wb_sys_frontend_name_idx ON wb_sys_frontend(name);
CREATE INDEX wb_sys_frontend_client_type_idx ON wb_sys_frontend(client_type);

DROP TABLE IF EXISTS wb_sys_user;
CREATE TABLE wb_sys_user(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    username TEXT(128) NOT NULL  , --用户名
    nickname TEXT(128) NOT NULL  , --昵称
    password TEXT(255) NOT NULL  , --密码
    email TEXT(255) NOT NULL  , --邮箱
    tel TEXT(128) NOT NULL  , --手机
    active tinyint NOT NULL  , --是否激活
    superuser tinyint NOT NULL  , --是否超级管理员
    staff tinyint NOT NULL  , --是否内部用户
    expired_time NUMERIC   , --失效时间
    sex TEXT(255)   , --性别
    department_id TEXT(64)   , --部门ID
    job_id TEXT(64)   , --岗位ID
    province TEXT(255) NOT NULL  , --省份
    city TEXT(255) NOT NULL  , --城市
    area TEXT(255) NOT NULL  , --区
    town TEXT(255) NOT NULL  , --县
    address TEXT NOT NULL  , --详细地址
    need_change_pwd tinyint NOT NULL  , --是否需要修改密码
    PRIMARY KEY (id)
)  ; --用户


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
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    parent_id TEXT(64)   , --父节点ID
    sort INTEGER NOT NULL  , --排序
    name TEXT(128) NOT NULL  , --名称
    fullname TEXT(255) NOT NULL  , --全称
    leader TEXT(128) NOT NULL  , --负责人
    PRIMARY KEY (id)
)  ; --部门


CREATE INDEX wb_sys_department_created_by_idx ON wb_sys_department(created_by);
CREATE INDEX wb_sys_department_created_time_idx ON wb_sys_department(created_time);
CREATE INDEX wb_sys_department_updated_by_idx ON wb_sys_department(updated_by);
CREATE INDEX wb_sys_department_updated_time_idx ON wb_sys_department(updated_time);
CREATE INDEX wb_sys_department_parent_id_idx ON wb_sys_department(parent_id);
CREATE INDEX wb_sys_department_sort_idx ON wb_sys_department(sort);
CREATE INDEX wb_sys_department_name_idx ON wb_sys_department(name);

DROP TABLE IF EXISTS wb_sys_job;
CREATE TABLE wb_sys_job(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    type TEXT(128) NOT NULL  , --类型
    PRIMARY KEY (id)
)  ; --岗位


CREATE INDEX wb_sys_job_created_by_idx ON wb_sys_job(created_by);
CREATE INDEX wb_sys_job_created_time_idx ON wb_sys_job(created_time);
CREATE INDEX wb_sys_job_updated_by_idx ON wb_sys_job(updated_by);
CREATE INDEX wb_sys_job_updated_time_idx ON wb_sys_job(updated_time);
CREATE INDEX wb_sys_job_name_idx ON wb_sys_job(name);
CREATE INDEX wb_sys_job_type_idx ON wb_sys_job(type);

DROP TABLE IF EXISTS wb_sys_role;
CREATE TABLE wb_sys_role(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    PRIMARY KEY (id)
)  ; --角色


CREATE INDEX wb_sys_role_created_by_idx ON wb_sys_role(created_by);
CREATE INDEX wb_sys_role_created_time_idx ON wb_sys_role(created_time);
CREATE INDEX wb_sys_role_updated_by_idx ON wb_sys_role(updated_by);
CREATE INDEX wb_sys_role_updated_time_idx ON wb_sys_role(updated_time);
CREATE INDEX wb_sys_role_name_idx ON wb_sys_role(name);

DROP TABLE IF EXISTS wb_sys_policy;
CREATE TABLE wb_sys_policy(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    label TEXT(128) NOT NULL  , --显示名
    readonly tinyint NOT NULL  , --是否只读
    PRIMARY KEY (id)
)  ; --策略


CREATE INDEX wb_sys_policy_created_by_idx ON wb_sys_policy(created_by);
CREATE INDEX wb_sys_policy_created_time_idx ON wb_sys_policy(created_time);
CREATE INDEX wb_sys_policy_updated_by_idx ON wb_sys_policy(updated_by);
CREATE INDEX wb_sys_policy_updated_time_idx ON wb_sys_policy(updated_time);
CREATE INDEX wb_sys_policy_name_idx ON wb_sys_policy(name);

DROP TABLE IF EXISTS wb_sys_permission;
CREATE TABLE wb_sys_permission(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    label TEXT(128) NOT NULL  , --显示名
    readonly tinyint NOT NULL  , --是否只读
    PRIMARY KEY (id)
)  ; --权限


CREATE INDEX wb_sys_permission_created_by_idx ON wb_sys_permission(created_by);
CREATE INDEX wb_sys_permission_created_time_idx ON wb_sys_permission(created_time);
CREATE INDEX wb_sys_permission_updated_by_idx ON wb_sys_permission(updated_by);
CREATE INDEX wb_sys_permission_updated_time_idx ON wb_sys_permission(updated_time);
CREATE INDEX wb_sys_permission_name_idx ON wb_sys_permission(name);

DROP TABLE IF EXISTS wb_sys_role_policy_rel;
CREATE TABLE wb_sys_role_policy_rel(
    id TEXT(64) NOT NULL  , --ID
    role_id TEXT(64) NOT NULL  , --角色ID
    policy_id TEXT(64) NOT NULL  , --策略ID
    PRIMARY KEY (id)
)  ; --角色策略关系


CREATE INDEX wb_sys_role_policy_rel_role_id_idx ON wb_sys_role_policy_rel(role_id);
CREATE INDEX wb_sys_role_policy_rel_policy_id_idx ON wb_sys_role_policy_rel(policy_id);
CREATE UNIQUE INDEX wb_sys_role_policy_rel_role_id_policy_id_idx ON wb_sys_role_policy_rel(role_id,policy_id);

DROP TABLE IF EXISTS wb_sys_policy_permission_rel;
CREATE TABLE wb_sys_policy_permission_rel(
    id TEXT(64) NOT NULL  , --ID
    policy_id TEXT(64) NOT NULL  , --策略ID
    permission_id TEXT(64) NOT NULL  , --权限ID
    PRIMARY KEY (id)
)  ; --策略权限关系


CREATE INDEX wb_sys_policy_permission_policy_id_idx ON wb_sys_policy_permission_rel(policy_id);
CREATE INDEX wb_sys_policy_permission_permission_id_idx ON wb_sys_policy_permission_rel(permission_id);
CREATE UNIQUE INDEX wb_sys_policy_permission_policy_id_permission_id_idx ON wb_sys_policy_permission_rel(policy_id,permission_id);

DROP TABLE IF EXISTS wb_sys_data_scope;
CREATE TABLE wb_sys_data_scope(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    PRIMARY KEY (id)
)  ; --数据权限


CREATE INDEX wb_sys_data_scope_created_by_idx ON wb_sys_data_scope(created_by);
CREATE INDEX wb_sys_data_scope_created_time_idx ON wb_sys_data_scope(created_time);
CREATE INDEX wb_sys_data_scope_updated_by_idx ON wb_sys_data_scope(updated_by);
CREATE INDEX wb_sys_data_scope_updated_time_idx ON wb_sys_data_scope(updated_time);
CREATE INDEX wb_sys_data_scope_name_idx ON wb_sys_data_scope(name);

DROP TABLE IF EXISTS wb_sys_user_role_rel;
CREATE TABLE wb_sys_user_role_rel(
    id TEXT(64) NOT NULL  , --ID
    user_id TEXT(64) NOT NULL  , --用户ID
    role_id TEXT(64) NOT NULL  , --角色ID
    PRIMARY KEY (id)
)  ; --用户角色关系


CREATE INDEX wb_sys_user_role_rel_user_id_idx ON wb_sys_user_role_rel(user_id);
CREATE INDEX wb_sys_user_role_rel_role_id_idx ON wb_sys_user_role_rel(role_id);
CREATE INDEX wb_sys_user_role_rel_user_id_role_id_idx ON wb_sys_user_role_rel(user_id,role_id);

DROP TABLE IF EXISTS wb_sys_policy_data_scope_rel;
CREATE TABLE wb_sys_policy_data_scope_rel(
    id TEXT(64) NOT NULL  , --ID
    policy_id TEXT(64) NOT NULL  , --策略ID
    data_scope_id TEXT(64) NOT NULL  , --数据权限ID
    PRIMARY KEY (id)
)  ; --策略数据权限关系


CREATE INDEX wb_sys_policy_data_scope_rel_policy_id_idx ON wb_sys_policy_data_scope_rel(policy_id);
CREATE INDEX wb_sys_policy_data_scope_rel_datascope_id_idx ON wb_sys_policy_data_scope_rel(data_scope_id);
CREATE UNIQUE INDEX wb_sys_policy_data_scope_rel_policy_id_datascope_id_idx ON wb_sys_policy_data_scope_rel(policy_id,data_scope_id);

DROP TABLE IF EXISTS wb_sys_menu;
CREATE TABLE wb_sys_menu(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    parent_id TEXT(64)   , --父节点ID
    sort INTEGER NOT NULL  , --排序
    name TEXT(128) NOT NULL  , --名称
    caption TEXT(128) NOT NULL  , --子标题
    path TEXT(255) NOT NULL  , --路径
    icon TEXT(255) NOT NULL  , --图标
    PRIMARY KEY (id)
)  ; --菜单


CREATE INDEX wb_sys_menu_created_by_idx ON wb_sys_menu(created_by);
CREATE INDEX wb_sys_menu_created_time_idx ON wb_sys_menu(created_time);
CREATE INDEX wb_sys_menu_updated_by_idx ON wb_sys_menu(updated_by);
CREATE INDEX wb_sys_menu_updated_time_idx ON wb_sys_menu(updated_time);
CREATE INDEX wb_sys_menu_parent_id_idx ON wb_sys_menu(parent_id);
CREATE INDEX wb_sys_menu_sort_idx ON wb_sys_menu(sort);
CREATE INDEX wb_sys_menu_name_idx ON wb_sys_menu(name);

DROP TABLE IF EXISTS wb_sys_policy_menu_rel;
CREATE TABLE wb_sys_policy_menu_rel(
    id TEXT(64) NOT NULL  , --ID
    policy_id TEXT(64) NOT NULL  , --策略ID
    menu_id TEXT(64) NOT NULL  , --菜单ID
    PRIMARY KEY (id)
)  ; --策略菜单关系


CREATE INDEX wb_sys_policy_menu_rel_policy_id_idx ON wb_sys_policy_menu_rel(policy_id);
CREATE INDEX wb_sys_policy_menu_rel_menu_id_idx ON wb_sys_policy_menu_rel(menu_id);
CREATE UNIQUE INDEX wb_sys_policy_menu_rel_policy_id_menu_id_idx ON wb_sys_policy_menu_rel(policy_id,menu_id);

DROP TABLE IF EXISTS wb_sys_param;
CREATE TABLE wb_sys_param(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    param_group TEXT(128) NOT NULL  , --配置组
    param_key TEXT(128) NOT NULL  , --配置键
    param_val TEXT   , --配置值
    param_type TEXT(32) NOT NULL  , --配置值类型
    PRIMARY KEY (id)
)  ; --配置


CREATE INDEX wb_sys_param_created_by_idx ON wb_sys_param(created_by);
CREATE INDEX wb_sys_param_created_time_idx ON wb_sys_param(created_time);
CREATE INDEX wb_sys_param_updated_by_idx ON wb_sys_param(updated_by);
CREATE INDEX wb_sys_param_updated_time_idx ON wb_sys_param(updated_time);
CREATE INDEX wb_sys_param_name_idx ON wb_sys_param(name);
CREATE INDEX wb_sys_param_param_group_idx ON wb_sys_param(param_group);
CREATE UNIQUE INDEX wb_sys_param_param_key_idx ON wb_sys_param(param_key);

DROP TABLE IF EXISTS wb_sys_user_dict;
CREATE TABLE wb_sys_user_dict(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    dict_group TEXT(128) NOT NULL  , --字典组
    dict_code TEXT(128) NOT NULL  , --字典代码
    dict_val TEXT   , --字典值
    dict_type TEXT(32) NOT NULL  , --字典值类型
    sort INTEGER NOT NULL  , --排序
    PRIMARY KEY (id)
)  ; --用户字典


CREATE INDEX wb_sys_user_dict_created_by_idx ON wb_sys_user_dict(created_by);
CREATE INDEX wb_sys_user_dict_created_time_idx ON wb_sys_user_dict(created_time);
CREATE INDEX wb_sys_user_dict_updated_by_idx ON wb_sys_user_dict(updated_by);
CREATE INDEX wb_sys_user_dict_updated_time_idx ON wb_sys_user_dict(updated_time);
CREATE INDEX wb_sys_user_dict_name_idx ON wb_sys_user_dict(name);
CREATE UNIQUE INDEX wb_sys_user_dict_dict_group_dict_code_idx ON wb_sys_user_dict(dict_group,dict_code);

DROP TABLE IF EXISTS wb_sys_user_log;
CREATE TABLE wb_sys_user_log(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    event TEXT(32) NOT NULL  , --事件
    status TEXT(32) NOT NULL  , --状态
    message TEXT(255) NOT NULL  , --消息
    user_id TEXT(64)   , --用户ID
    username TEXT(128) NOT NULL  , --用户名
    frontend_id TEXT(64)   , --前端ID
    frontend_name TEXT(128) NOT NULL  , --前端名
    ip TEXT(255) NOT NULL  , --来源IP
    ua TEXT(255) NOT NULL  , --用户客户端
    PRIMARY KEY (id)
)  ; --用户活动日志


CREATE INDEX wb_sys_user_log_created_by_idx ON wb_sys_user_log(created_by);
CREATE INDEX wb_sys_user_log_created_time_idx ON wb_sys_user_log(created_time);
CREATE INDEX wb_sys_user_log_event_idx ON wb_sys_user_log(event);
CREATE INDEX wb_sys_user_log_user_id_idx ON wb_sys_user_log(user_id);
CREATE INDEX wb_sys_user_log_frontend_id_idx ON wb_sys_user_log(frontend_id);
CREATE INDEX wb_sys_user_log_ip_idx ON wb_sys_user_log(ip);

DROP TABLE IF EXISTS wb_sys_operation_log;
CREATE TABLE wb_sys_operation_log(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    event TEXT(32) NOT NULL  , --事件
    resource TEXT(255) NOT NULL  , --资源名称
    resource_id TEXT(64) NOT NULL  , --资源ID
    obj TEXT   , --资源对象
    username TEXT(128) NOT NULL  , --用户名
    PRIMARY KEY (id)
)  ; --操作日志


CREATE INDEX wb_sys_operation_log_created_by_idx ON wb_sys_operation_log(created_by);
CREATE INDEX wb_sys_operation_log_created_time_idx ON wb_sys_operation_log(created_time);
CREATE INDEX wb_sys_operation_log_event_idx ON wb_sys_operation_log(event);
CREATE INDEX wb_sys_operation_log_resource_resource_id_idx ON wb_sys_operation_log(resource,resource_id);
CREATE INDEX wb_sys_operation_log_username_idx ON wb_sys_operation_log(username);

DROP TABLE IF EXISTS wb_task_quick_link;
CREATE TABLE wb_task_quick_link(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    icon TEXT(255) NOT NULL  , --图标
    path TEXT(255) NOT NULL  , --路径
    sort INTEGER NOT NULL  , --排序
    PRIMARY KEY (id)
)  ; --快捷菜单


CREATE INDEX wb_task_quick_link_created_by_idx ON wb_task_quick_link(created_by);
CREATE INDEX wb_task_quick_link_created_time_idx ON wb_task_quick_link(created_time);
CREATE INDEX wb_task_quick_link_updated_by_idx ON wb_task_quick_link(updated_by);
CREATE INDEX wb_task_quick_link_updated_time_idx ON wb_task_quick_link(updated_time);
CREATE INDEX wb_task_quick_link_name_idx ON wb_task_quick_link(name);
CREATE INDEX wb_task_quick_link_sort_idx ON wb_task_quick_link(sort);

DROP TABLE IF EXISTS wb_task_inside_message;
CREATE TABLE wb_task_inside_message(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    title TEXT(255) NOT NULL  , --标题
    content TEXT NOT NULL  , --内容
    type TEXT(32) NOT NULL  , --类型
    from_user_id TEXT(64)   , --来源用户ID
    to_user_id TEXT(64) NOT NULL  , --目标用户ID
    read_time NUMERIC   , --读取时间
    PRIMARY KEY (id)
)  ; --站内消息


CREATE INDEX wb_task_inside_message_created_by_idx ON wb_task_inside_message(created_by);
CREATE INDEX wb_task_inside_message_created_time_idx ON wb_task_inside_message(created_time);
CREATE INDEX wb_task_inside_message_from_user_id_idx ON wb_task_inside_message(from_user_id);
CREATE INDEX wb_task_inside_message_to_user_id_idx ON wb_task_inside_message(to_user_id);

DROP TABLE IF EXISTS wb_sys_announcement;
CREATE TABLE wb_sys_announcement(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    title TEXT(255) NOT NULL  , --标题
    content TEXT NOT NULL  , --内容
    type TEXT(32) NOT NULL  , --类型
    display tinyint NOT NULL  , --是否展示
    sort INTEGER NOT NULL  , --排序
    PRIMARY KEY (id)
)  ; --公告


CREATE INDEX wb_sys_announcement_created_by_idx ON wb_sys_announcement(created_by);
CREATE INDEX wb_sys_announcement_created_time_idx ON wb_sys_announcement(created_time);
CREATE INDEX wb_sys_announcement_updated_by_idx ON wb_sys_announcement(updated_by);
CREATE INDEX wb_sys_announcement_updated_time_idx ON wb_sys_announcement(updated_time);
CREATE INDEX wb_sys_announcement_type_display_idx ON wb_sys_announcement(type,display);
CREATE INDEX wb_sys_announcement_sort_idx ON wb_sys_announcement(sort);

DROP TABLE IF EXISTS wb_sys_attachment;
CREATE TABLE wb_sys_attachment(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    url TEXT(512) NOT NULL  , --文件访问地址
    size BIGINT NOT NULL  , --文件大小
    filename TEXT(255) NOT NULL  , --文件名称
    original_filename TEXT(255) NOT NULL  , --原始文件名
    base_path TEXT(255) NOT NULL  , --基础存储路径
    path TEXT(255) NOT NULL  , --存储路径
    ext TEXT(128) NOT NULL  , --文件扩展名
    content_type TEXT(128) NOT NULL  , --MIME类型
    platform TEXT(128) NOT NULL  , --存储平台
    th_url TEXT(512) NOT NULL  , --缩略图访问路径
    th_filename TEXT(255) NOT NULL  , --缩略图名称
    th_size BIGINT NOT NULL  , --缩略图大小
    th_content_type TEXT(128) NOT NULL  , --缩略图MIME类型
    hash_info TEXT NOT NULL  , --哈希信息
    attr TEXT NOT NULL  , --附加属性
    object_id TEXT(64)   , --关联对象ID
    object_type TEXT(255) NOT NULL  , --关联对象类型
    deleted shortint NOT NULL  DEFAULT 0, --删除标示
    PRIMARY KEY (id)
)  ; --附件


CREATE INDEX wb_sys_attachment_created_by_idx ON wb_sys_attachment(created_by);
CREATE INDEX wb_sys_attachment_created_time_idx ON wb_sys_attachment(created_time);
CREATE INDEX wb_sys_attachment_url_idx ON wb_sys_attachment(url);
CREATE INDEX wb_sys_attachment_content_type_idx ON wb_sys_attachment(content_type);
CREATE INDEX wb_sys_attachment_platform_idx ON wb_sys_attachment(platform);
CREATE INDEX wb_sys_attachment_object_idx ON wb_sys_attachment(object_type,object_id);
CREATE INDEX wb_sys_attachment_deleted_idx ON wb_sys_attachment(deleted);

DROP TABLE IF EXISTS wb_sys_bg_task;
CREATE TABLE wb_sys_bg_task(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    type TEXT(32) NOT NULL  , --类型
    status TEXT(32) NOT NULL  , --状态
    result TEXT(512) NOT NULL  , --结果
    PRIMARY KEY (id)
)  ; --后台任务


CREATE INDEX wb_sys_bg_task_created_by_idx ON wb_sys_bg_task(created_by);
CREATE INDEX wb_sys_bg_task_created_time_idx ON wb_sys_bg_task(created_time);
CREATE INDEX wb_sys_bg_task_updated_by_idx ON wb_sys_bg_task(updated_by);
CREATE INDEX wb_sys_bg_task_updated_time_idx ON wb_sys_bg_task(updated_time);
CREATE INDEX wb_sys_bg_task_type_idx ON wb_sys_bg_task(type);
CREATE INDEX wb_sys_bg_task_status_idx ON wb_sys_bg_task(status);


-- views
create view wb_sys_user_view as
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
