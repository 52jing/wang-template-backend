package com.wangboot.system.event;

import com.wangboot.core.event.RequestEvent;
import javax.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;

public class BgTaskEvent extends RequestEvent {

  public BgTaskEvent(BgTaskObject taskObject, @Nullable HttpServletRequest request) {
    super(taskObject, request);
  }
}
