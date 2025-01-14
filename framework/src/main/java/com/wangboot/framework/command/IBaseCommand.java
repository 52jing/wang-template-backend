package com.wangboot.framework.command;

import cn.hutool.core.lang.Console;
import com.wangboot.core.auth.AuthFlow;
import com.wangboot.core.auth.context.*;
import com.wangboot.core.auth.user.IUserModel;
import com.wangboot.core.auth.user.impl.MockUser;
import java.util.Objects;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.lang.Nullable;

/**
 * 命令接口
 *
 * @author wwtg99
 */
public interface IBaseCommand extends ApplicationRunner {

  IUserModel SYSTEM_USER = new MockUser("0", "System", "", true, true, true, false, null);

  ILoginUser SYSTEM = new LoginUser(SYSTEM_USER, AuthFlow.UNKNOWN_FRONTEND, AuthFlow.ALLOW_ALL);

  /** 执行命令 */
  void callCommand(ApplicationArguments args);

  /** 运行条件 */
  boolean whetherToRun(ApplicationArguments args);

  @Nullable
  default IAuthContext getAuthContext() {
    return new LocalAuthContext(SYSTEM);
  }

  default boolean exitAfterCompletion() {
    return false;
  }

  default void run(ApplicationArguments args) {
    if (this.whetherToRun(args)) {
      IAuthContext original = AuthContextHolder.getContext();
      IAuthContext authContext = this.getAuthContext();
      if (Objects.nonNull(authContext)) {
        AuthContextHolder.setContext(authContext);
      }
      this.callCommand(args);
      if (this.exitAfterCompletion()) {
        System.exit(0);
      }
      AuthContextHolder.setContext(original);
    }
  }

  default void println(String template, Object... values) {
    Console.log(template, values);
  }

  default void print(String msg) {
    Console.log(msg);
  }
}
