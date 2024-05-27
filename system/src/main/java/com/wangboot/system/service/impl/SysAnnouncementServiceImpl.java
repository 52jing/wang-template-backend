package com.wangboot.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.flex.IFlexRestfulService;
import com.wangboot.system.entity.SysAnnouncement;
import com.wangboot.system.entity.table.SysAnnouncementTableDef;
import com.wangboot.system.mapper.SysAnnouncementMapper;
import com.wangboot.system.model.AnnouncementType;
import com.wangboot.system.service.SysAnnouncementService;
import com.wangboot.system.service.SysAttachmentService;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class SysAnnouncementServiceImpl extends ServiceImpl<SysAnnouncementMapper, SysAnnouncement>
    implements SysAnnouncementService {

  private final SysAttachmentService attachmentService;

  @Override
  @NonNull
  public List<SysAnnouncement> getDisplayedAnnouncements(String type) {
    if (!StringUtils.hasText(type)) {
      return Collections.emptyList();
    }
    AnnouncementType announcementType = AnnouncementType.resolve(type);
    if (Objects.isNull(announcementType)) {
      return Collections.emptyList();
    }
    QueryWrapper wrapper =
        this.query()
            .where(SysAnnouncementTableDef.SYS_ANNOUNCEMENT.TYPE.eq(type))
            .and(SysAnnouncementTableDef.SYS_ANNOUNCEMENT.DISPLAY.eq(true))
            .orderBy(SysAnnouncementTableDef.SYS_ANNOUNCEMENT.SORT, true);
    return this.list(wrapper);
  }

  @Override
  public IFlexRestfulService<String, ? extends IdEntity<String>> getAttachmentService() {
    return this.attachmentService;
  }
}
