package com.wangboot.system.model;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordBody {
  private String oldPwd = "";

  @NotBlank(message = "message.pwd_not_blank")
  private String newPwd = "";
}
