package com.wangboot.system.listener;

import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import com.wangboot.core.auth.utils.AuthUtils;
import com.wangboot.model.entity.IAppendOnlyEntity;
import com.wangboot.model.entity.ICommonEntity;
import java.time.OffsetDateTime;

/**
 * 实体变更监听器
 *
 * @author wwtg99
 */
public class EntityChangeListener implements InsertListener, UpdateListener {
  @Override
  public void onInsert(Object o) {
    if (o instanceof ICommonEntity) {
      ((ICommonEntity) o).setCreatedBy(AuthUtils.getUserId());
      ((ICommonEntity) o).setCreatedTime(OffsetDateTime.now());
      ((ICommonEntity) o).setUpdatedBy(AuthUtils.getUserId());
      ((ICommonEntity) o).setUpdatedTime(OffsetDateTime.now());
    } else if (o instanceof IAppendOnlyEntity) {
      ((IAppendOnlyEntity) o).setCreatedBy(AuthUtils.getUserId());
      ((IAppendOnlyEntity) o).setCreatedTime(OffsetDateTime.now());
    }
  }

  @Override
  public void onUpdate(Object o) {
    if (o instanceof ICommonEntity) {
      ((ICommonEntity) o).setUpdatedBy(AuthUtils.getUserId());
      ((ICommonEntity) o).setUpdatedTime(OffsetDateTime.now());
    }
  }
}
