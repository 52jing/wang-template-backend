package com.wangboot.system.model;

import com.wangboot.framework.validator.Telephone;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterBody {

  private String frontendId;

  @NotBlank(message = "message.username_not_blank")
  @Size(max = 100)
  private String username;

  @Size(min = 3, max = 100)
  private String nickname;

  @NotBlank(message = "message.pwd_not_blank")
  private String password;

  @Email(message = "message.invalid_email")
  @Size(max = 100)
  private String email;

  @Telephone
  @Size(max = 20)
  private String tel;
}
