package com.wangboot.app.template.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.app.template.entity.TplTemplate;
import com.wangboot.app.template.mapper.TplTemplateMapper;
import com.wangboot.app.template.service.TplTemplateService;
import com.wangboot.core.web.response.ListBody;
import com.wangboot.model.attachment.IAttachmentService;
import com.wangboot.model.entity.request.FieldFilter;
import com.wangboot.model.entity.request.SortFilter;
import com.wangboot.system.service.SysAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TplTemplateServiceImpl extends ServiceImpl<TplTemplateMapper, TplTemplate>
    implements TplTemplateService {

  private final SysAttachmentService attachmentService;

  @Override
  @NonNull
  public ListBody<TplTemplate> listResourcesPage(
      @Nullable SortFilter[] sortFilters,
      @Nullable FieldFilter[] fieldFilters,
      @Nullable FieldFilter[] searchFilters,
      long page,
      long pageSize) {
    ListBody<TplTemplate> data =
        TplTemplateService.super.listResourcesPage(
            sortFilters, fieldFilters, searchFilters, page, pageSize);
    data.setData(this.getEntitiesWithAttachments(data.getData()));
    return data;
  }

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
