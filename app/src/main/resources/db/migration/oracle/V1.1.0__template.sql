CREATE TABLE wb_template_template(
    id VARCHAR2(64) NOT NULL,
    created_by VARCHAR2(64),
    created_time DATE,
    updated_by VARCHAR2(64),
    updated_time DATE,
    remark VARCHAR2(900) NOT NULL,
    name VARCHAR2(90) NOT NULL,
    type VARCHAR2(32) NOT NULL,
    def_filename VARCHAR2(255) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_template_template IS '模板';
COMMENT ON COLUMN wb_template_template.id IS '租户号';
COMMENT ON COLUMN wb_template_template.created_by IS '创建人';
COMMENT ON COLUMN wb_template_template.created_time IS '创建时间';
COMMENT ON COLUMN wb_template_template.updated_by IS '更新人';
COMMENT ON COLUMN wb_template_template.updated_time IS '更新时间';
COMMENT ON COLUMN wb_template_template.remark IS '备注';
COMMENT ON COLUMN wb_template_template.name IS '名称';
COMMENT ON COLUMN wb_template_template.type IS '类型';
COMMENT ON COLUMN wb_template_template.def_filename IS '默认文件名';


CREATE INDEX wb_template_template_created_by_idx ON wb_template_template(created_by);
CREATE INDEX wb_template_template_created_time_idx ON wb_template_template(created_time);
CREATE INDEX wb_template_template_updated_by_idx ON wb_template_template(updated_by);
CREATE INDEX wb_template_template_updated_time_idx ON wb_template_template(updated_time);
CREATE INDEX wb_template_template_name_idx ON wb_template_template(name);
CREATE INDEX wb_template_template_type_idx ON wb_template_template(type);

CREATE TABLE wb_template_datasource(
    id VARCHAR2(64) NOT NULL,
    created_by VARCHAR2(64),
    created_time DATE,
    updated_by VARCHAR2(64),
    updated_time DATE,
    remark VARCHAR2(900) NOT NULL,
    name VARCHAR2(90) NOT NULL,
    type VARCHAR2(32) NOT NULL,
    config CLOB NOT NULL,
    connected BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_template_datasource IS '数据源';
COMMENT ON COLUMN wb_template_datasource.id IS 'ID';
COMMENT ON COLUMN wb_template_datasource.created_by IS '创建人';
COMMENT ON COLUMN wb_template_datasource.created_time IS '创建时间';
COMMENT ON COLUMN wb_template_datasource.updated_by IS '更新人';
COMMENT ON COLUMN wb_template_datasource.updated_time IS '更新时间';
COMMENT ON COLUMN wb_template_datasource.remark IS '备注';
COMMENT ON COLUMN wb_template_datasource.name IS '名称';
COMMENT ON COLUMN wb_template_datasource.type IS '类型';
COMMENT ON COLUMN wb_template_datasource.config IS '配置';
COMMENT ON COLUMN wb_template_datasource.connected IS '是否已连接';


CREATE INDEX wb_template_datasource_created_by_idx ON wb_template_datasource(created_by);
CREATE INDEX wb_template_datasource_created_by_idxcreated_time_idx ON wb_template_datasource(created_time);
CREATE INDEX wb_template_datasource_created_by_idxupdated_by_idx ON wb_template_datasource(updated_by);
CREATE INDEX wb_template_datasource_created_by_idxupdated_time_idx ON wb_template_datasource(updated_time);
CREATE INDEX wb_template_datasource_created_by_idxname_idx ON wb_template_datasource(name);
CREATE INDEX twb_template_datasource_created_by_idxype_idx ON wb_template_datasource(type);

CREATE TABLE wb_template_datasource_param(
    id VARCHAR2(64) NOT NULL,
    created_by VARCHAR2(64),
    created_time DATE,
    updated_by VARCHAR2(64),
    updated_time DATE,
    remark VARCHAR2(900) NOT NULL,
    name VARCHAR2(90) NOT NULL,
    label VARCHAR2(90) NOT NULL,
    datasource_id VARCHAR2(64) NOT NULL,
    required BOOLEAN NOT NULL,
    def_val VARCHAR2(255) NOT NULL,
    config CLOB NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_template_datasource_param IS '数据源参数';
COMMENT ON COLUMN wb_template_datasource_param.id IS 'ID';
COMMENT ON COLUMN wb_template_datasource_param.created_by IS '创建人';
COMMENT ON COLUMN wb_template_datasource_param.created_time IS '创建时间';
COMMENT ON COLUMN wb_template_datasource_param.updated_by IS '更新人';
COMMENT ON COLUMN wb_template_datasource_param.updated_time IS '更新时间';
COMMENT ON COLUMN wb_template_datasource_param.remark IS '备注';
COMMENT ON COLUMN wb_template_datasource_param.name IS '参数编码';
COMMENT ON COLUMN wb_template_datasource_param.label IS '参数标签';
COMMENT ON COLUMN wb_template_datasource_param.datasource_id IS '数据源ID';
COMMENT ON COLUMN wb_template_datasource_param.required IS '是否必填';
COMMENT ON COLUMN wb_template_datasource_param.def_val IS '默认值';
COMMENT ON COLUMN wb_template_datasource_param.config IS '配置';


CREATE INDEX wb_template_datasource_param_created_by_idx ON wb_template_datasource_param(created_by);
CREATE INDEX wb_template_datasource_param_created_time_idx ON wb_template_datasource_param(created_time);
CREATE INDEX wb_template_datasource_param_updated_by_idx ON wb_template_datasource_param(updated_by);
CREATE INDEX wb_template_datasource_param_updated_time_idx ON wb_template_datasource_param(updated_time);
CREATE INDEX wb_template_datasource_param_name_idx ON wb_template_datasource_param(name);
CREATE INDEX wb_template_datasource_param_datasource_id ON wb_template_datasource_param(datasource_id);

CREATE TABLE wb_template_render_execution(
    id VARCHAR2(64) NOT NULL,
    created_by VARCHAR2(64),
    created_time DATE,
    updated_by VARCHAR2(64),
    updated_time DATE,
    remark VARCHAR2(900) NOT NULL,
    template_id VARCHAR2(64) NOT NULL,
    template_name VARCHAR2(90) NOT NULL,
    template_type VARCHAR2(32) NOT NULL,
    datasource_id VARCHAR2(64) NOT NULL,
    datasource_name VARCHAR2(90) NOT NULL,
    datasource_type VARCHAR2(32) NOT NULL,
    params CLOB NOT NULL,
    status VARCHAR2(32) NOT NULL,
    filename VARCHAR2(255) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_template_render_execution IS '渲染执行';
COMMENT ON COLUMN wb_template_render_execution.id IS 'ID';
COMMENT ON COLUMN wb_template_render_execution.created_by IS '创建人';
COMMENT ON COLUMN wb_template_render_execution.created_time IS '创建时间';
COMMENT ON COLUMN wb_template_render_execution.updated_by IS '更新人';
COMMENT ON COLUMN wb_template_render_execution.updated_time IS '更新时间';
COMMENT ON COLUMN wb_template_render_execution.remark IS '备注';
COMMENT ON COLUMN wb_template_render_execution.template_id IS '模板ID';
COMMENT ON COLUMN wb_template_render_execution.template_name IS '模板名称';
COMMENT ON COLUMN wb_template_render_execution.template_type IS '模板类型';
COMMENT ON COLUMN wb_template_render_execution.datasource_id IS '数据源ID';
COMMENT ON COLUMN wb_template_render_execution.datasource_name IS '数据源名称';
COMMENT ON COLUMN wb_template_render_execution.datasource_type IS '数据源类型';
COMMENT ON COLUMN wb_template_render_execution.params IS '执行参数';
COMMENT ON COLUMN wb_template_render_execution.status IS '执行状态';
COMMENT ON COLUMN wb_template_render_execution.filename IS '文件名';


CREATE INDEX wb_template_render_execution_created_by_idx ON wb_template_render_execution(created_by);
CREATE INDEX wb_template_render_execution_created_time_idx ON wb_template_render_execution(created_time);
CREATE INDEX wb_template_render_execution_updated_by_idx ON wb_template_render_execution(updated_by);
CREATE INDEX wb_template_render_execution_updated_time_idx ON wb_template_render_execution(updated_time);
CREATE INDEX wb_template_render_execution_template_id_idx ON wb_template_render_execution(template_id);
CREATE INDEX wb_template_render_execution_datasource_id_idx ON wb_template_render_execution(datasource_id);
CREATE INDEX wb_template_render_execution_status_idx ON wb_template_render_execution(status);
CREATE INDEX wb_template_render_execution_template_type_idx ON wb_template_render_execution(template_type);
CREATE INDEX wb_template_render_execution_datasource_type_idx ON wb_template_render_execution(datasource_type);

CREATE TABLE wb_template_execution_result(
    id VARCHAR2(64) NOT NULL,
    created_by VARCHAR2(64),
    created_time DATE,
    execution_id VARCHAR2(64) NOT NULL,
    message VARCHAR2(900) NOT NULL,
    status VARCHAR2(32) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE wb_template_execution_result IS '执行结果';
COMMENT ON COLUMN wb_template_execution_result.id IS 'ID';
COMMENT ON COLUMN wb_template_execution_result.created_by IS '创建人';
COMMENT ON COLUMN wb_template_execution_result.created_time IS '创建时间';
COMMENT ON COLUMN wb_template_execution_result.execution_id IS '执行ID';
COMMENT ON COLUMN wb_template_execution_result.message IS '结果描述';
COMMENT ON COLUMN wb_template_execution_result.status IS '结果状态';


CREATE INDEX wb_template_execution_result_created_by_idx ON wb_template_execution_result(created_by);
CREATE INDEX wb_template_execution_result_created_time_idx ON wb_template_execution_result(created_time);
CREATE INDEX wb_template_execution_result_execution_id_idx ON wb_template_execution_result(execution_id);

