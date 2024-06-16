DROP TABLE IF EXISTS wb_sys_frontend;
CREATE TABLE wb_sys_frontend(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    name VARCHAR(90) NOT NULL,
    description VARCHAR(900) NOT NULL,
    author VARCHAR(90) NOT NULL,
    domain VARCHAR(255) NOT NULL,
    client_type VARCHAR(32) NOT NULL,
    allow_register BOOLEAN NOT NULL,
    staff_only BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_frontend_created_by_idx ON wb_sys_frontend(created_by);
CREATE INDEX wb_sys_frontend_created_time_idx ON wb_sys_frontend(created_time);
CREATE INDEX wb_sys_frontend_updated_by_idx ON wb_sys_frontend(updated_by);
CREATE INDEX wb_sys_frontend_updated_time_idx ON wb_sys_frontend(updated_time);
CREATE INDEX wb_sys_frontend_name_idx ON wb_sys_frontend(name);
CREATE INDEX wb_sys_frontend_client_type_idx ON wb_sys_frontend(client_type);

DROP TABLE IF EXISTS wb_sys_user;
CREATE TABLE wb_sys_user(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    username VARCHAR(90) NOT NULL,
    nickname VARCHAR(90) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    tel VARCHAR(90) NOT NULL,
    active BOOLEAN NOT NULL,
    superuser BOOLEAN NOT NULL,
    staff BOOLEAN NOT NULL,
    expired_time TIMESTAMP,
    sex VARCHAR(64),
    department_id VARCHAR(64),
    job_id VARCHAR(64),
    province VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    area VARCHAR(255) NOT NULL,
    town VARCHAR(255) NOT NULL,
    address VARCHAR(900) NOT NULL,
    need_change_pwd BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);


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
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    parent_id VARCHAR(64),
    sort INTEGER NOT NULL,
    name VARCHAR(90) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    leader VARCHAR(90) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_department_created_by_idx ON wb_sys_department(created_by);
CREATE INDEX wb_sys_department_created_time_idx ON wb_sys_department(created_time);
CREATE INDEX wb_sys_department_updated_by_idx ON wb_sys_department(updated_by);
CREATE INDEX wb_sys_department_updated_time_idx ON wb_sys_department(updated_time);
CREATE INDEX wb_sys_department_parent_id_idx ON wb_sys_department(parent_id);
CREATE INDEX wb_sys_department_sort_idx ON wb_sys_department(sort);
CREATE INDEX wb_sys_department_name_idx ON wb_sys_department(name);

DROP TABLE IF EXISTS wb_sys_job;
CREATE TABLE wb_sys_job(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    name VARCHAR(90) NOT NULL,
    type VARCHAR(90) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_job_created_by_idx ON wb_sys_job(created_by);
CREATE INDEX wb_sys_job_created_time_idx ON wb_sys_job(created_time);
CREATE INDEX wb_sys_job_updated_by_idx ON wb_sys_job(updated_by);
CREATE INDEX wb_sys_job_updated_time_idx ON wb_sys_job(updated_time);
CREATE INDEX wb_sys_job_name_idx ON wb_sys_job(name);
CREATE INDEX wb_sys_job_type_idx ON wb_sys_job(type);

DROP TABLE IF EXISTS wb_sys_role;
CREATE TABLE wb_sys_role(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    name VARCHAR(90) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_role_created_by_idx ON wb_sys_role(created_by);
CREATE INDEX wb_sys_role_created_time_idx ON wb_sys_role(created_time);
CREATE INDEX wb_sys_role_updated_by_idx ON wb_sys_role(updated_by);
CREATE INDEX wb_sys_role_updated_time_idx ON wb_sys_role(updated_time);
CREATE INDEX wb_sys_role_name_idx ON wb_sys_role(name);

DROP TABLE IF EXISTS wb_sys_policy;
CREATE TABLE wb_sys_policy(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    name VARCHAR(90) NOT NULL,
    label VARCHAR(90) NOT NULL,
    readonly BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX wb_sys_policy_created_by_idx ON wb_sys_policy(created_by);
CREATE INDEX wb_sys_policy_created_time_idx ON wb_sys_policy(created_time);
CREATE INDEX wb_sys_policy_updated_by_idx ON wb_sys_policy(updated_by);
CREATE INDEX wb_sys_policy_updated_time_idx ON wb_sys_policy(updated_time);
CREATE INDEX wb_sys_policy_name_idx ON wb_sys_policy(name);

DROP TABLE IF EXISTS wb_sys_permission;
CREATE TABLE wb_sys_permission(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    name VARCHAR(90) NOT NULL,
    label VARCHAR(90) NOT NULL,
    readonly BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_permission_created_by_idx ON wb_sys_permission(created_by);
CREATE INDEX wb_sys_permission_created_time_idx ON wb_sys_permission(created_time);
CREATE INDEX wb_sys_permission_updated_by_idx ON wb_sys_permission(updated_by);
CREATE INDEX wb_sys_permission_updated_time_idx ON wb_sys_permission(updated_time);
CREATE INDEX wb_sys_permission_name_idx ON wb_sys_permission(name);

DROP TABLE IF EXISTS wb_sys_role_policy_rel;
CREATE TABLE wb_sys_role_policy_rel(
    id VARCHAR(64) NOT NULL,
    role_id VARCHAR(64) NOT NULL,
    policy_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_role_policy_rel_role_id_idx ON wb_sys_role_policy_rel(role_id);
CREATE INDEX wb_sys_role_policy_rel_policy_id_idx ON wb_sys_role_policy_rel(policy_id);
CREATE UNIQUE INDEX wb_sys_role_policy_rel_role_id_policy_id_idx ON wb_sys_role_policy_rel(role_id,policy_id);

DROP TABLE IF EXISTS wb_sys_policy_permission_rel;
CREATE TABLE wb_sys_policy_permission_rel(
    id VARCHAR(64) NOT NULL,
    policy_id VARCHAR(64) NOT NULL,
    permission_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_policy_permission_policy_id_idx ON wb_sys_policy_permission_rel(policy_id);
CREATE INDEX wb_sys_policy_permission_permission_id_idx ON wb_sys_policy_permission_rel(permission_id);
CREATE UNIQUE INDEX wb_sys_policy_permission_policy_id_permission_id_idx ON wb_sys_policy_permission_rel(policy_id,permission_id);

DROP TABLE IF EXISTS wb_sys_data_scope;
CREATE TABLE wb_sys_data_scope(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    name VARCHAR(90) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_data_scope_created_by_idx ON wb_sys_data_scope(created_by);
CREATE INDEX wb_sys_data_scope_created_time_idx ON wb_sys_data_scope(created_time);
CREATE INDEX wb_sys_data_scope_updated_by_idx ON wb_sys_data_scope(updated_by);
CREATE INDEX wb_sys_data_scope_updated_time_idx ON wb_sys_data_scope(updated_time);
CREATE INDEX wb_sys_data_scope_name_idx ON wb_sys_data_scope(name);

DROP TABLE IF EXISTS wb_sys_user_role_rel;
CREATE TABLE wb_sys_user_role_rel(
    id VARCHAR(64) NOT NULL,
    user_id VARCHAR(64) NOT NULL,
    role_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_user_role_rel_user_id_idx ON wb_sys_user_role_rel(user_id);
CREATE INDEX wb_sys_user_role_rel_role_id_idx ON wb_sys_user_role_rel(role_id);
CREATE INDEX wb_sys_user_role_rel_user_id_role_id_idx ON wb_sys_user_role_rel(user_id,role_id);

DROP TABLE IF EXISTS wb_sys_policy_data_scope_rel;
CREATE TABLE wb_sys_policy_data_scope_rel(
    id VARCHAR(64) NOT NULL,
    policy_id VARCHAR(64) NOT NULL,
    data_scope_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_policy_data_scope_rel_policy_id_idx ON wb_sys_policy_data_scope_rel(policy_id);
CREATE INDEX wb_sys_policy_data_scope_rel_datascope_id_idx ON wb_sys_policy_data_scope_rel(data_scope_id);
CREATE UNIQUE INDEX wb_sys_policy_data_scope_rel_policy_id_datascope_id_idx ON wb_sys_policy_data_scope_rel(policy_id,data_scope_id);

DROP TABLE IF EXISTS wb_sys_menu;
CREATE TABLE wb_sys_menu(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    parent_id VARCHAR(64),
    sort INTEGER NOT NULL,
    name VARCHAR(90) NOT NULL,
    caption VARCHAR(90) NOT NULL,
    path VARCHAR(255) NOT NULL,
    icon VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_menu_created_by_idx ON wb_sys_menu(created_by);
CREATE INDEX wb_sys_menu_created_time_idx ON wb_sys_menu(created_time);
CREATE INDEX wb_sys_menu_updated_by_idx ON wb_sys_menu(updated_by);
CREATE INDEX wb_sys_menu_updated_time_idx ON wb_sys_menu(updated_time);
CREATE INDEX wb_sys_menu_parent_id_idx ON wb_sys_menu(parent_id);
CREATE INDEX wb_sys_menu_sort_idx ON wb_sys_menu(sort);
CREATE INDEX wb_sys_menu_name_idx ON wb_sys_menu(name);

DROP TABLE IF EXISTS wb_sys_policy_menu_rel;
CREATE TABLE wb_sys_policy_menu_rel(
    id VARCHAR(64) NOT NULL,
    policy_id VARCHAR(64) NOT NULL,
    menu_id VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_policy_menu_rel_policy_id_idx ON wb_sys_policy_menu_rel(policy_id);
CREATE INDEX wb_sys_policy_menu_rel_menu_id_idx ON wb_sys_policy_menu_rel(menu_id);
CREATE UNIQUE INDEX wb_sys_policy_menu_rel_policy_id_menu_id_idx ON wb_sys_policy_menu_rel(policy_id,menu_id);

DROP TABLE IF EXISTS wb_sys_param;
CREATE TABLE wb_sys_param(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    name VARCHAR(90) NOT NULL,
    param_group VARCHAR(90) NOT NULL,
    param_key VARCHAR(90) NOT NULL,
    param_val VARCHAR(900),
    param_type VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_param_created_by_idx ON wb_sys_param(created_by);
CREATE INDEX wb_sys_param_created_time_idx ON wb_sys_param(created_time);
CREATE INDEX wb_sys_param_updated_by_idx ON wb_sys_param(updated_by);
CREATE INDEX wb_sys_param_updated_time_idx ON wb_sys_param(updated_time);
CREATE INDEX wb_sys_param_name_idx ON wb_sys_param(name);
CREATE INDEX wb_sys_param_param_group_idx ON wb_sys_param(param_group);
CREATE UNIQUE INDEX wb_sys_param_param_key_idx ON wb_sys_param(param_key);

DROP TABLE IF EXISTS wb_sys_user_dict;
CREATE TABLE wb_sys_user_dict(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    name VARCHAR(90) NOT NULL,
    dict_group VARCHAR(90) NOT NULL,
    dict_code VARCHAR(90) NOT NULL,
    dict_val VARCHAR(900),
    dict_type VARCHAR(32) NOT NULL,
    sort INTEGER NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_user_dict_created_by_idx ON wb_sys_user_dict(created_by);
CREATE INDEX wb_sys_user_dict_created_time_idx ON wb_sys_user_dict(created_time);
CREATE INDEX wb_sys_user_dict_updated_by_idx ON wb_sys_user_dict(updated_by);
CREATE INDEX wb_sys_user_dict_updated_time_idx ON wb_sys_user_dict(updated_time);
CREATE INDEX wb_sys_user_dict_name_idx ON wb_sys_user_dict(name);
CREATE UNIQUE INDEX wb_sys_user_dict_dict_group_dict_code_idx ON wb_sys_user_dict(dict_group,dict_code);

DROP TABLE IF EXISTS wb_sys_user_log;
CREATE TABLE wb_sys_user_log(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    event VARCHAR(32) NOT NULL,
    status VARCHAR(32) NOT NULL,
    message VARCHAR(255) NOT NULL,
    user_id VARCHAR(64),
    username VARCHAR(90) NOT NULL,
    frontend_id VARCHAR(64),
    frontend_name VARCHAR(90) NOT NULL,
    ip VARCHAR(255) NOT NULL,
    ua VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_user_log_created_by_idx ON wb_sys_user_log(created_by);
CREATE INDEX wb_sys_user_log_created_time_idx ON wb_sys_user_log(created_time);
CREATE INDEX wb_sys_user_log_event_idx ON wb_sys_user_log(event);
CREATE INDEX wb_sys_user_log_user_id_idx ON wb_sys_user_log(user_id);
CREATE INDEX wb_sys_user_log_frontend_id_idx ON wb_sys_user_log(frontend_id);
CREATE INDEX wb_sys_user_log_ip_idx ON wb_sys_user_log(ip);

DROP TABLE IF EXISTS wb_sys_operation_log;
CREATE TABLE wb_sys_operation_log(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    event VARCHAR(32) NOT NULL,
    resource VARCHAR(255) NOT NULL,
    resource_id VARCHAR(64) NOT NULL,
    obj TEXT,
    username VARCHAR(90) NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_operation_log_created_by_idx ON wb_sys_operation_log(created_by);
CREATE INDEX wb_sys_operation_log_created_time_idx ON wb_sys_operation_log(created_time);
CREATE INDEX wb_sys_operation_log_event_idx ON wb_sys_operation_log(event);
CREATE INDEX wb_sys_operation_log_resource_resource_id_idx ON wb_sys_operation_log(resource,resource_id);
CREATE INDEX wb_sys_operation_log_username_idx ON wb_sys_operation_log(username);

DROP TABLE IF EXISTS wb_task_quick_link;
CREATE TABLE wb_task_quick_link(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    name VARCHAR(90) NOT NULL,
    icon VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    sort INTEGER NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_task_quick_link_created_by_idx ON wb_task_quick_link(created_by);
CREATE INDEX wb_task_quick_link_created_time_idx ON wb_task_quick_link(created_time);
CREATE INDEX wb_task_quick_link_updated_by_idx ON wb_task_quick_link(updated_by);
CREATE INDEX wb_task_quick_link_updated_time_idx ON wb_task_quick_link(updated_time);
CREATE INDEX wb_task_quick_link_name_idx ON wb_task_quick_link(name);
CREATE INDEX wb_task_quick_link_sort_idx ON wb_task_quick_link(sort);

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


CREATE INDEX wb_task_inside_message_created_by_idx ON wb_task_inside_message(created_by);
CREATE INDEX wb_task_inside_message_created_time_idx ON wb_task_inside_message(created_time);
CREATE INDEX wb_task_inside_message_from_user_id_idx ON wb_task_inside_message(from_user_id);
CREATE INDEX wb_task_inside_message_to_user_id_idx ON wb_task_inside_message(to_user_id);

DROP TABLE IF EXISTS wb_sys_announcement;
CREATE TABLE wb_sys_announcement(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(32) NOT NULL,
    display BOOLEAN NOT NULL,
    sort INTEGER NOT NULL,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_announcement_created_by_idx ON wb_sys_announcement(created_by);
CREATE INDEX wb_sys_announcement_created_time_idx ON wb_sys_announcement(created_time);
CREATE INDEX wb_sys_announcement_updated_by_idx ON wb_sys_announcement(updated_by);
CREATE INDEX wb_sys_announcement_updated_time_idx ON wb_sys_announcement(updated_time);
CREATE INDEX wb_sys_announcement_type_display_idx ON wb_sys_announcement(type,display);
CREATE INDEX wb_sys_announcement_sort_idx ON wb_sys_announcement(sort);

DROP TABLE IF EXISTS wb_sys_attachment;
CREATE TABLE wb_sys_attachment(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    url VARCHAR(900) NOT NULL,
    size BIGINT NOT NULL,
    filename VARCHAR(255) NOT NULL,
    original_filename VARCHAR(255) NOT NULL,
    base_path VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    ext VARCHAR(90) NOT NULL,
    content_type VARCHAR(90) NOT NULL,
    platform VARCHAR(90) NOT NULL,
    th_url VARCHAR(900) NOT NULL,
    th_filename VARCHAR(255) NOT NULL,
    th_size BIGINT NOT NULL,
    th_content_type VARCHAR(90) NOT NULL,
    hash_info VARCHAR(900) NOT NULL,
    attr VARCHAR(900) NOT NULL,
    object_id VARCHAR(64),
    object_type VARCHAR(255) NOT NULL,
    deleted TINYINT NOT NULL DEFAULT  0,
    PRIMARY KEY (id)
);


CREATE INDEX wb_sys_attachment_created_by_idx ON wb_sys_attachment(created_by);
CREATE INDEX wb_sys_attachment_created_time_idx ON wb_sys_attachment(created_time);
CREATE INDEX wb_sys_attachment_content_type_idx ON wb_sys_attachment(content_type);
CREATE INDEX wb_sys_attachment_platform_idx ON wb_sys_attachment(platform);
CREATE INDEX wb_sys_attachment_object_idx ON wb_sys_attachment(object_type,object_id);
CREATE INDEX wb_sys_attachment_deleted_idx ON wb_sys_attachment(deleted);

DROP TABLE IF EXISTS wb_sys_bg_task;
CREATE TABLE wb_sys_bg_task(
    id VARCHAR(64) NOT NULL,
    created_by VARCHAR(64),
    created_time TIMESTAMP,
    updated_by VARCHAR(64),
    updated_time TIMESTAMP,
    remark VARCHAR(900) NOT NULL,
    name VARCHAR(90) NOT NULL,
    type VARCHAR(32) NOT NULL,
    status VARCHAR(32) NOT NULL,
    result TEXT NOT NULL,
    PRIMARY KEY (id)
);


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
