package com.wangboot.system.event;

import com.wangboot.system.model.BgTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BgTaskResult {
  private String attachmentId;
  private String result;
  private BgTaskStatus status;
}
