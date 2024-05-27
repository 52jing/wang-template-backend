package com.wangboot.system.event;

import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BgTaskObject {
  private String id;
  private String name;
  private String type;
  private Supplier<BgTaskResult> supplier;
}
