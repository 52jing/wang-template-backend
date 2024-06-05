package com.wangboot.system.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangboot.core.web.task.IBackgroundTask;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BgTaskObject implements IBackgroundTask<BgTaskResult> {
  private String id;
  private String name;
  private String group;
  @JsonIgnore private Supplier<BgTaskResult> resultSupplier;
}
