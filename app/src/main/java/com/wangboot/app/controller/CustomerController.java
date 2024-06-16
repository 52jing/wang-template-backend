package com.wangboot.app.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.wangboot.core.auth.annotation.RequireAuthority;
import com.wangboot.core.auth.event.LogStatus;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.errorcode.HttpErrorCode;
import com.wangboot.core.utils.ISystemDict;
import com.wangboot.core.web.crypto.ExcludeEncryption;
import com.wangboot.core.web.response.DetailBody;
import com.wangboot.core.web.response.ListBody;
import com.wangboot.core.web.utils.ResponseUtils;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.model.entity.exception.NotFoundException;
import com.wangboot.starter.autoconfiguration.WbProperties;
import com.wangboot.system.entity.SysAnnouncement;
import com.wangboot.system.entity.SysAttachment;
import com.wangboot.system.entity.SysFrontend;
import com.wangboot.system.entity.SysUserDict;
import com.wangboot.system.entity.table.SysUserDictTableDef;
import com.wangboot.system.entity.vo.AttachmentVo;
import com.wangboot.system.model.AnnouncementType;
import com.wangboot.system.model.ClientType;
import com.wangboot.system.model.ParamType;
import com.wangboot.system.service.SysAnnouncementService;
import com.wangboot.system.service.SysAttachmentService;
import com.wangboot.system.service.SysFrontendService;
import com.wangboot.system.service.SysUserDictService;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
