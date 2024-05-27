package com.wangboot.system.event;

import com.wangboot.core.web.event.BaseRequestEvent;
import javax.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;

public class BgTaskEvent extends BaseRequestEvent<BgTaskObject> {

  public BgTaskEvent(BgTaskObject taskObject, @Nullable HttpServletRequest request) {
    super(taskObject, request);
  }
}
