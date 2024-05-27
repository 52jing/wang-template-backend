package com.wangboot.app.commands;

import com.wangboot.framework.IBaseCommand;
import com.wangboot.model.entity.exception.CreateFailedException;
import com.wangboot.system.entity.SysUser;
import com.wangboot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 创建用户<br>
 * 执行命令: --create-user --username=<username> --password=<password> --superuser --staff
 *
 * @author wwtg99
 */
@Component
@RequiredArgsConstructor
public class CreateUser implements IBaseCommand {

  private final PasswordEncoder passwordEncoder;

  private final SysUserService userService;

  @Override
  public void callCommand(ApplicationArguments args) {
    if (!args.containsOption("username")) {
      throw new IllegalArgumentException("No username provided!");
    }
    if (!args.containsOption("password")) {
      throw new IllegalArgumentException("No password provided!");
    }
    String username = args.getOptionValues("username").get(0);
    String password = args.getOptionValues("password").get(0);
    boolean superuser = args.containsOption("superuser");
    boolean staff = args.containsOption("staff");
    SysUser user = new SysUser();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setSuperuser(superuser);
    user.setStaff(staff);
    user.setNickname(username);
    user.setActive(true);
    boolean ret = userService.createResource(user);
    if (!ret) {
      throw new CreateFailedException();
    } else {
      this.println("Create user {}", username);
    }
  }

  @Override
  public boolean whetherToRun(ApplicationArguments args) {
    return args.containsOption("create-user");
  }

  @Override
  public boolean exitAfterCompletion() {
    return true;
  }
}
