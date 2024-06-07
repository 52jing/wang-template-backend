package com.wangboot.app.template.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.app.template.entity.TplExecutionResult;
import com.wangboot.app.template.entity.TplRenderExecution;
import com.wangboot.app.template.entity.table.TplExecutionResultTableDef;
import com.wangboot.app.template.mapper.TplRenderExecutionMapper;
import com.wangboot.app.template.service.TplExecutionResultService;
import com.wangboot.app.template.service.TplRenderExecutionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TplRenderExecutionServiceImpl
    extends ServiceImpl<TplRenderExecutionMapper, TplRenderExecution>
    implements TplRenderExecutionService {

  private final TplExecutionResultService executionResultService;

  @Override
  public List<TplExecutionResult> getResults(String id) {
    QueryWrapper wrapper =
        QueryWrapper.create()
            .where(TplExecutionResultTableDef.TPL_EXECUTION_RESULT.EXECUTION_ID.eq(id));
    return this.executionResultService.list(wrapper);
  }
}
