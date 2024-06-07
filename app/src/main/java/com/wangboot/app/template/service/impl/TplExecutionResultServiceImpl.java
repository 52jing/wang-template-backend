package com.wangboot.app.template.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.app.template.entity.TplExecutionResult;
import com.wangboot.app.template.mapper.TplExecutionResultMapper;
import com.wangboot.app.template.service.TplExecutionResultService;
import com.wangboot.model.attachment.IAttachmentService;
import com.wangboot.system.service.SysAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TplExecutionResultServiceImpl
    extends ServiceImpl<TplExecutionResultMapper, TplExecutionResult>
    implements TplExecutionResultService {

  private final SysAttachmentService attachmentService;

  @Override
  @NonNull
  public IAttachmentService getAttachmentService() {
    return this.attachmentService;
  }

  @Override
  public String getObjectType() {
    return this.getEntityClass().getName();
  }
}
