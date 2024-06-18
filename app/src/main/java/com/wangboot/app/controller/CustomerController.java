package com.wangboot.app.controller;

import cn.hutool.core.map.MapUtil;
import com.wangboot.core.auth.event.LogStatus;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.errorcode.HttpErrorCode;
import com.wangboot.core.utils.ISystemDict;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.system.model.AnnouncementType;
import com.wangboot.system.model.ClientType;
import com.wangboot.system.model.ParamType;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {

  /**
   * 定义系统字典接口需要输出的类型，供 systemDict 接口调用<br>
   * 键为 name 参数值，值为类 Class（推荐使用枚举类） 如果枚举类实现了 ISystemDict<br>
   * 接口，可自动转换为对象，否则调用 toString 方法转换为字符串
   */
  private static final Map<String, Class<?>> SYSTEM_DICT = buildSystemDict();

  private static Map<String, Class<?>> buildSystemDict() {
    Map<String, Class<?>> map = new HashMap<>();
    map.put("param_type", ParamType.class);
    map.put("client_type", ClientType.class);
    map.put("log_status", LogStatus.class);
    map.put("announcement_type", AnnouncementType.class);
    return map;
  }

  /**
   * 系统字典数据接口<br>
   * 输出 SYSTEM_DICT 定义的字典数据<br>
   * 枚举类自动转换为数组
   */
  @GetMapping("/system_dict/{name}")
  public ResponseEntity<?> systemDict(@PathVariable String name) {
    if (SYSTEM_DICT.containsKey(name)) {
      if (ISystemDict.class.isAssignableFrom(SYSTEM_DICT.get(name))) {
        return ResponseUtils.success(
            DetailBody.ok(
                Arrays.stream(SYSTEM_DICT.get(name).getEnumConstants())
                    .map(
                        d -> {
                          ISystemDict sd = (ISystemDict) d;
                          return MapUtil.of(
                              new String[][] {
                                {"code", sd.getCode()},
                                {"name", sd.getName()}
                              });
                        })
                    .collect(Collectors.toList())));
      } else {
        return ResponseUtils.success(
            DetailBody.ok(
                Arrays.stream(SYSTEM_DICT.get(name).getEnumConstants())
                    .map(
                        d ->
                            MapUtil.of(
                                new String[][] {{"code", d.toString()}, {"name", d.toString()}}))
                    .collect(Collectors.toList())));
      }
    } else {
      throw new ErrorCodeException(HttpErrorCode.BAD_REQUEST);
    }
  }
}
