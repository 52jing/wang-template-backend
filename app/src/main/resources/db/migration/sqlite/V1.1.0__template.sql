DROP TABLE IF EXISTS wb_template_template;
CREATE TABLE wb_template_template(
    id TEXT(64) NOT NULL  , --租户号
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    type TEXT(128) NOT NULL  , --类型
    def_filename TEXT(255) NOT NULL  , --默认文件名
    PRIMARY KEY (id)
)  ; --模板


CREATE INDEX wb_template_template_created_by_idx ON wb_template_template(created_by);
CREATE INDEX wb_template_template_created_time_idx ON wb_template_template(created_time);
CREATE INDEX wb_template_template_updated_by_idx ON wb_template_template(updated_by);
CREATE INDEX wb_template_template_updated_time_idx ON wb_template_template(updated_time);
CREATE INDEX wb_template_template_name_idx ON wb_template_template(name);
CREATE INDEX wb_template_template_type_idx ON wb_template_template(type);

DROP TABLE IF EXISTS wb_template_datasource;
CREATE TABLE wb_template_datasource(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --名称
    type TEXT(128) NOT NULL  , --类型
    config TEXT NOT NULL  , --配置
    connected tinyint NOT NULL  , --是否已连接
    PRIMARY KEY (id)
)  ; --数据源


CREATE INDEX wb_template_datasource_created_by_idx ON wb_template_datasource(created_by);
CREATE INDEX wb_template_datasource_created_by_idxcreated_time_idx ON wb_template_datasource(created_time);
CREATE INDEX wb_template_datasource_created_by_idxupdated_by_idx ON wb_template_datasource(updated_by);
CREATE INDEX wb_template_datasource_created_by_idxupdated_time_idx ON wb_template_datasource(updated_time);
CREATE INDEX wb_template_datasource_created_by_idxname_idx ON wb_template_datasource(name);
CREATE INDEX twb_template_datasource_created_by_idxype_idx ON wb_template_datasource(type);

DROP TABLE IF EXISTS wb_template_datasource_param;
CREATE TABLE wb_template_datasource_param(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    name TEXT(128) NOT NULL  , --参数编码
    label TEXT(128) NOT NULL  , --参数标签
    datasource_id TEXT(64) NOT NULL  , --数据源ID
    required INTEGER NOT NULL  , --是否必填
    def_val TEXT(512) NOT NULL  , --默认值
    config TEXT NOT NULL  , --配置
    PRIMARY KEY (id)
)  ; --数据源参数


CREATE INDEX wb_template_datasource_param_created_by_idx ON wb_template_datasource_param(created_by);
CREATE INDEX wb_template_datasource_param_created_time_idx ON wb_template_datasource_param(created_time);
CREATE INDEX wb_template_datasource_param_updated_by_idx ON wb_template_datasource_param(updated_by);
CREATE INDEX wb_template_datasource_param_updated_time_idx ON wb_template_datasource_param(updated_time);
CREATE INDEX wb_template_datasource_param_name_idx ON wb_template_datasource_param(name);
CREATE INDEX wb_template_datasource_param_datasource_id ON wb_template_datasource_param(datasource_id);

DROP TABLE IF EXISTS wb_template_render_execution;
CREATE TABLE wb_template_render_execution(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    updated_by TEXT(64)   , --更新人
    updated_time NUMERIC   , --更新时间
    remark TEXT(512) NOT NULL  , --备注
    template_id TEXT(64) NOT NULL  , --模板ID
    template_name TEXT(128) NOT NULL  , --模板名称
    template_type TEXT(128) NOT NULL  , --模板类型
    datasource_id TEXT(64) NOT NULL  , --数据源ID
    datasource_name TEXT(128) NOT NULL  , --数据源名称
    datasource_type TEXT(128) NOT NULL  , --数据源类型
    params TEXT NOT NULL  , --执行参数
    status TEXT(128) NOT NULL  , --执行状态
    filename TEXT(255) NOT NULL  , --文件名
    PRIMARY KEY (id)
)  ; --渲染执行


CREATE INDEX wb_template_render_execution_created_by_idx ON wb_template_render_execution(created_by);
CREATE INDEX wb_template_render_execution_created_time_idx ON wb_template_render_execution(created_time);
CREATE INDEX wb_template_render_execution_updated_by_idx ON wb_template_render_execution(updated_by);
CREATE INDEX wb_template_render_execution_updated_time_idx ON wb_template_render_execution(updated_time);
CREATE INDEX wb_template_render_execution_template_id_idx ON wb_template_render_execution(template_id);
CREATE INDEX wb_template_render_execution_datasource_id_idx ON wb_template_render_execution(datasource_id);
CREATE INDEX wb_template_render_execution_status_idx ON wb_template_render_execution(status);
CREATE INDEX wb_template_render_execution_template_type_idx ON wb_template_render_execution(template_type);
CREATE INDEX wb_template_render_execution_datasource_type_idx ON wb_template_render_execution(datasource_type);

DROP TABLE IF EXISTS wb_template_execution_result;
CREATE TABLE wb_template_execution_result(
    id TEXT(64) NOT NULL  , --ID
    created_by TEXT(64)   , --创建人
    created_time NUMERIC   , --创建时间
    execution_id TEXT(64) NOT NULL  , --执行ID
    message TEXT NOT NULL  , --结果描述
    status TEXT(128) NOT NULL  , --结果状态
    PRIMARY KEY (id)
)  ; --执行结果


CREATE INDEX wb_template_execution_result_created_by_idx ON wb_template_execution_result(created_by);
CREATE INDEX wb_template_execution_result_created_time_idx ON wb_template_execution_result(created_time);
CREATE INDEX wb_template_execution_result_execution_id_idx ON wb_template_execution_result(execution_id);

