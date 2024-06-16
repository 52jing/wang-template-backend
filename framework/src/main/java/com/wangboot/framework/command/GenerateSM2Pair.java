package com.wangboot.framework.command;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SecureUtil;
import com.wangboot.core.utils.StrUtils;
import java.security.KeyPair;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * 生成 SM2 密钥对<br>
 * 使用参数 --generate-sm2
 *
 * @author wwtg99
 */
@Component
public class GenerateSM2Pair implements IBaseCommand {

  private static final int LINE_WIDTH = 80;

  @Override
  public void callCommand(ApplicationArguments args) {
    KeyPair pair = SecureUtil.generateKeyPair("SM2");
    String privateKeyStr = HexUtil.encodeHexStr(BCUtil.encodeECPrivateKey(pair.getPrivate()));
    this.println("=== Private Key: ===");
    StrUtils.splitStrByLineWidth(privateKeyStr, LINE_WIDTH).forEach(this::println);
    String publicKeyStr =
        HexUtil.encodeHexStr(((BCECPublicKey) pair.getPublic()).getQ().getEncoded(false));
    this.println("=== Public Key: ===");
    StrUtils.splitStrByLineWidth(publicKeyStr, LINE_WIDTH).forEach(this::println);
  }

  @Override
  public boolean whetherToRun(ApplicationArguments args) {
    return args.containsOption("generate-sm2");
  }

  @Override
  public boolean exitAfterCompletion() {
    return true;
  }
}
