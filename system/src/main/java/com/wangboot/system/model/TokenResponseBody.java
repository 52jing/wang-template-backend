package com.wangboot.system.model;

import com.wangboot.core.web.response.IStatusBody;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseBody implements IStatusBody {
  private String accessToken;
  private String refreshToken;
  private int status;
}
