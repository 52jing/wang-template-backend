package com.wangboot.app.execution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangboot.app.execution.datasource.DatasourceProcessor;
import com.wangboot.app.execution.datasource.dbsql.DatabaseSqlFactory;
import com.wangboot.app.execution.render.ITemplateRender;
import com.wangboot.app.execution.render.TemplateRenderFactory;
import com.wangboot.app.execution.render.text.FreeMarkerTemplateRender;
import com.wangboot.app.execution.render.word.PoiWordTemplateRender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置<br>
 * 注册数据源工厂和渲染生成器
 *
 * @author wwtg99
 */
@Configuration
public class RenderExecutionConfig {

  @Bean
  public DatabaseSqlFactory databaseSqlFactory(DatasourceProcessor datasourceProcessor, ObjectMapper objectMapper) {
    DatabaseSqlFactory factory = new DatabaseSqlFactory(objectMapper);
    datasourceProcessor.registerDatasourceFactory("db_sql", factory);
    return factory;
  }

  @Bean
  public ITemplateRender poiWordTemplateRender(TemplateRenderFactory factory) {
    ITemplateRender render = new PoiWordTemplateRender();
    factory.registerTemplateRender("word", render);
    return render;
  }

  @Bean
  public ITemplateRender freeMarkerTemplateRender(TemplateRenderFactory factory) {
    ITemplateRender render = new FreeMarkerTemplateRender();
    factory.registerTemplateRender("text", render);
    return render;
  }
}
