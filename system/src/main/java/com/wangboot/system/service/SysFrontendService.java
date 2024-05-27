package com.wangboot.system.service;

import com.wangboot.core.auth.frontend.IFrontendService;
import com.wangboot.model.flex.IFlexRestfulService;
import com.wangboot.system.entity.SysFrontend;

/**
 * 服务层。
 *
 * @author wwtg99
 */
public interface SysFrontendService
    extends IFlexRestfulService<String, SysFrontend>, IFrontendService {}
