package com.wangboot.framework.command;

import cn.hutool.crypto.SecureUtil;
import com.wangboot.core.utils.StrUtils;
import java.security.KeyPair;
import java.util.Base64;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * 生成 RSA 密钥对<br>
 * 使用参数 --generate-rsa
 *
 * @author wwtg99
 */
@Component
public class GenerateRSAPair implements IBaseCommand {

  private static final int LINE_WIDTH = 80;

  @Override
  public void callCommand(ApplicationArguments args) {
    KeyPair pair = SecureUtil.generateKeyPair("RSA");
    String privateKeyStr = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
    this.println("=== Private Key: ===");
    StrUtils.splitStrByLineWidth(privateKeyStr, LINE_WIDTH).forEach(this::println);
    String publicKeyStr = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
    this.println("=== Public Key: ===");
    StrUtils.splitStrByLineWidth(publicKeyStr, LINE_WIDTH).forEach(this::println);
  }

  @Override
  public boolean whetherToRun(ApplicationArguments args) {
    return args.containsOption("generate-rsa");
  }

  @Override
  public boolean exitAfterCompletion() {
    return true;
  }
}
