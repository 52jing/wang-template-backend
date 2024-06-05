DROP TABLE IF EXISTS wb_template_template;
CREATE TABLE wb_template_template(
    `id` VARCHAR(64) NOT NULL  COMMENT '租户号' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `type` VARCHAR(128) NOT NULL  COMMENT '类型' ,
    `def_filename` VARCHAR(255) NOT NULL  COMMENT '默认文件名' ,
    PRIMARY KEY (id)
)  COMMENT = '模板';


CREATE INDEX created_by_idx ON wb_template_template(created_by);
CREATE INDEX created_time_idx ON wb_template_template(created_time);
CREATE INDEX updated_by_idx ON wb_template_template(updated_by);
CREATE INDEX updated_time_idx ON wb_template_template(updated_time);
CREATE INDEX name_idx ON wb_template_template(name);
CREATE INDEX type_idx ON wb_template_template(type);

DROP TABLE IF EXISTS wb_template_datasource;
CREATE TABLE wb_template_datasource(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '名称' ,
    `type` VARCHAR(128) NOT NULL  COMMENT '类型' ,
    `config` TEXT NOT NULL  COMMENT '配置' ,
    PRIMARY KEY (id)
)  COMMENT = '数据源';


CREATE INDEX created_by_idx ON wb_template_datasource(created_by);
CREATE INDEX created_time_idx ON wb_template_datasource(created_time);
CREATE INDEX updated_by_idx ON wb_template_datasource(updated_by);
CREATE INDEX updated_time_idx ON wb_template_datasource(updated_time);
CREATE INDEX name_idx ON wb_template_datasource(name);
CREATE INDEX type_idx ON wb_template_datasource(type);

DROP TABLE IF EXISTS wb_template_datasource_param;
CREATE TABLE wb_template_datasource_param(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `name` VARCHAR(128) NOT NULL  COMMENT '参数编码' ,
    `label` VARCHAR(128) NOT NULL  COMMENT '参数标签' ,
    `datasource_id` VARCHAR(64) NOT NULL  COMMENT '数据源ID' ,
    `required` INT NOT NULL  COMMENT '是否必填' ,
    `def_val` VARCHAR(512) NOT NULL  COMMENT '默认值' ,
    `config` TEXT NOT NULL  COMMENT '配置' ,
    PRIMARY KEY (id)
)  COMMENT = '数据源参数';


CREATE INDEX created_by_idx ON wb_template_datasource_param(created_by);
CREATE INDEX created_time_idx ON wb_template_datasource_param(created_time);
CREATE INDEX updated_by_idx ON wb_template_datasource_param(updated_by);
CREATE INDEX updated_time_idx ON wb_template_datasource_param(updated_time);
CREATE INDEX name_idx ON wb_template_datasource_param(name);
CREATE INDEX datasource_id ON wb_template_datasource_param(datasource_id);

DROP TABLE IF EXISTS wb_template_render_execution;
CREATE TABLE wb_template_render_execution(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `updated_by` VARCHAR(64)   COMMENT '更新人' ,
    `updated_time` DATETIME   COMMENT '更新时间' ,
    `remark` VARCHAR(512) NOT NULL  COMMENT '备注' ,
    `template_id` VARCHAR(64) NOT NULL  COMMENT '模板ID' ,
    `template_name` VARCHAR(128) NOT NULL  COMMENT '模板名称' ,
    `template_type` VARCHAR(128) NOT NULL  COMMENT '模板类型' ,
    `datasource_id` VARCHAR(64) NOT NULL  COMMENT '数据源ID' ,
    `datasource_name` VARCHAR(128) NOT NULL  COMMENT '数据源名称' ,
    `datasource_type` VARCHAR(128) NOT NULL  COMMENT '数据源类型' ,
    `params` TEXT NOT NULL  COMMENT '执行参数' ,
    `status` VARCHAR(128) NOT NULL  COMMENT '执行状态' ,
    `filename` VARCHAR(255) NOT NULL  COMMENT '文件名' ,
    PRIMARY KEY (id)
)  COMMENT = '渲染执行';


CREATE INDEX created_by_idx ON wb_template_render_execution(created_by);
CREATE INDEX created_time_idx ON wb_template_render_execution(created_time);
CREATE INDEX updated_by_idx ON wb_template_render_execution(updated_by);
CREATE INDEX updated_time_idx ON wb_template_render_execution(updated_time);
CREATE INDEX template_id_idx ON wb_template_render_execution(template_id);
CREATE INDEX datasource_id_idx ON wb_template_render_execution(datasource_id);
CREATE INDEX status_idx ON wb_template_render_execution(status);
CREATE INDEX template_type_idx ON wb_template_render_execution(template_type);
CREATE INDEX datasource_type_idx ON wb_template_render_execution(datasource_type);

DROP TABLE IF EXISTS wb_template_execution_result;
CREATE TABLE wb_template_execution_result(
    `id` VARCHAR(64) NOT NULL  COMMENT 'ID' ,
    `created_by` VARCHAR(64)   COMMENT '创建人' ,
    `created_time` DATETIME   COMMENT '创建时间' ,
    `execution_id` VARCHAR(64) NOT NULL  COMMENT '执行ID' ,
    `message` TEXT NOT NULL  COMMENT '结果描述' ,
    `status` VARCHAR(128) NOT NULL  COMMENT '结果状态' ,
    PRIMARY KEY (id)
)  COMMENT = '执行结果';


CREATE INDEX created_by_idx ON wb_template_execution_result(created_by);
CREATE INDEX created_time_idx ON wb_template_execution_result(created_time);
CREATE INDEX execution_id_idx ON wb_template_execution_result(execution_id);

