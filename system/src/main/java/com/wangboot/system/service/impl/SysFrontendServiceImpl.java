package com.wangboot.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.core.auth.frontend.IFrontendModel;
import com.wangboot.core.cache.CacheUtil;
import com.wangboot.system.entity.SysFrontend;
import com.wangboot.system.entity.table.SysFrontendTableDef;
import com.wangboot.system.mapper.SysFrontendMapper;
import com.wangboot.system.service.SysFrontendService;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author wwtg99
 */
@Service
public class SysFrontendServiceImpl extends ServiceImpl<SysFrontendMapper, SysFrontend>
    implements SysFrontendService {

  private static final String CACHE_PREFIX = "frontend_cache_";

  /** 缓存10秒 */
  private static final long CACHE_TTL = 10000;

  @Override
  @Nullable
  public IFrontendModel getFrontendModelById(String id) {
    return CacheUtil.getOrSet(
        CACHE_PREFIX + id, IFrontendModel.class, CACHE_TTL, () -> getById(id));
  }

  @Override
  @Nullable
  public IFrontendModel getFrontendModelByName(String name) {
    return CacheUtil.getOrSet(
        CACHE_PREFIX + name,
        IFrontendModel.class,
        CACHE_TTL,
        () -> getOne(query().where(SysFrontendTableDef.SYS_FRONTEND.NAME.eq(name))));
  }

  @Override
  @NonNull
  @SuppressWarnings("unchecked")
  public List<? extends IFrontendModel> getFrontendModelsByType(String type) {
    List<SysFrontend> lists =
        CacheUtil.getOrSet(
            CACHE_PREFIX + type,
            List.class,
            CACHE_TTL,
            () -> list(query().where(SysFrontendTableDef.SYS_FRONTEND.CLIENT_TYPE.eq(type))));
    if (Objects.nonNull(lists)) {
      return lists;
    }
    return Collections.emptyList();
  }
}
