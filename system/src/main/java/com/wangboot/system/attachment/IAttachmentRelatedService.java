package com.wangboot.system.attachment;

import com.wangboot.model.attachment.IAttachmentListRelatedModel;
import com.wangboot.model.attachment.IAttachmentListRelatedService;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.entity.exception.CreateFailedException;
import com.wangboot.model.entity.exception.UpdateFailedException;
import com.wangboot.model.flex.IFlexRestfulService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

/**
 * 关联附件的服务接口
 *
 * @param <I> 主键类型
 * @param <T> 实体类型
 * @author wwtg99
 */
public interface IAttachmentRelatedService<
        I extends Serializable, T extends IAttachmentListRelatedModel<I>>
    extends IAttachmentListRelatedService<I, T>, IFlexRestfulService<I, T> {

  @Override
  @Nullable
  default T viewResource(I id) {
    T obj = IFlexRestfulService.super.viewResource(id);
    // 添加附件集合
    return this.getEntityWithAttachments(obj);
  }

  @Override
  @Transactional
  default boolean createResource(@NonNull T entity) {
    boolean ret = IFlexRestfulService.super.createResource(entity);
    if (ret) {
      // 保存附件
      ret = this.addAttachmentsRelations(entity);
      if (!ret) {
        throw new CreateFailedException();
      }
    }
    return ret;
  }

  @Override
  @Transactional
  default boolean updateResource(@NonNull T entity) {
    boolean ret = IFlexRestfulService.super.updateResource(entity);
    if (ret) {
      ret = this.updateAttachmentsRelations(entity);
      if (!ret) {
        throw new UpdateFailedException(entity.getId());
      }
    }
    return ret;
  }

  @Override
  @Transactional
  default boolean deleteResource(@NonNull T entity) {
    boolean ret = IFlexRestfulService.super.deleteResource(entity);
    if (ret) {
      this.removeAttachmentsRelations(entity.getId());
    }
    return ret;
  }

  @Override
  @Transactional
  default boolean batchDeleteResources(@NonNull Collection<T> entities) {
    boolean ret = IFlexRestfulService.super.batchDeleteResources(entities);
    if (ret) {
      List<I> ids = entities.stream().map(IdEntity::getId).collect(Collectors.toList());
      this.batchRemoveAttachmentsRelations(ids);
    }
    return ret;
  }
}
