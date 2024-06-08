package com.wangboot.app.commands;

import com.mybatisflex.core.query.QueryWrapper;
import com.wangboot.app.execution.datasource.DatasourceProcessor;
import com.wangboot.app.template.entity.TplDatasource;
import com.wangboot.app.template.entity.table.TplDatasourceTableDef;
import com.wangboot.app.template.service.TplDatasourceService;
import com.wangboot.framework.IBaseCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动后准备数据源
 * @author wwtg99
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PrepareDatasource implements IBaseCommand {

  private final TplDatasourceService datasourceService;

  private final DatasourceProcessor datasourceProcessor;

  @Override
  public void callCommand(ApplicationArguments args) {
    QueryWrapper wrapper = this.datasourceService.query().where(TplDatasourceTableDef.TPL_DATASOURCE.CONNECTED.eq(true));
    List<TplDatasource> datasourceList = this.datasourceService.list(wrapper);
    datasourceList.forEach(ds -> {
      log.info("Prepare datasource {}.", ds.getName());
      this.datasourceProcessor.prepareDatasource(ds.getId(), ds.getName(), ds.getType(), ds.getConfig());
    });
  }

  @Override
  public boolean whetherToRun(ApplicationArguments args) {
    return true;
  }
}
