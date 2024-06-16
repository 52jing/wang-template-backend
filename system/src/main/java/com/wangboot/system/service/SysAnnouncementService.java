package com.wangboot.system.service;

import com.wangboot.model.flex.IFlexRestfulService;
import com.wangboot.system.attachment.IAttachmentRelatedService;
import com.wangboot.system.entity.SysAnnouncement;
import java.util.List;
import org.springframework.lang.NonNull;

public interface SysAnnouncementService
    extends IFlexRestfulService<String, SysAnnouncement>,
        IAttachmentRelatedService<String, SysAnnouncement> {

  /** 获取展示的公告 */
  @NonNull
  List<SysAnnouncement> getDisplayedAnnouncements(String type);
}
