package com.wangboot.system.attachment;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.model.entity.IdEntity;
import com.wangboot.model.flex.IFlexRestfulService;
import java.io.Serializable;
import java.util.*;
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
        I extends Serializable, T extends IAttachmentRelatedEntity<I>>
    extends IFlexRestfulService<I, T> {

  String OBJECT_TYPE_FIELD = "object_type";
  String OBJECT_ID_FIELD = "object_id";

  /** 获取附件服务 */
  IFlexRestfulService<String, ? extends IdEntity<String>> getAttachmentService();

  /** 附件对象类型 */
  default String getObjectType() {
    return this.getEntityClass().getName();
  }

  @Override
  @Nullable
  default T getDataById(@NonNull I id) {
    T data = this.getById(id);
    if (Objects.isNull(data)) {
      return null;
    }
    // 获取附件
    List<? extends IdEntity<String>> attachments = this.getAttachmentList(id);
    data.setAttachmentList(attachments);
    return data;
  }

  @Override
  @Transactional
  default boolean saveData(@NonNull T data) {
    boolean ret = this.save(data);
    if (ret) {
      // 保存附件
      ret = this.saveAttachmentRelations(data);
    }
    return ret;
  }

  @Override
  @Transactional
  default boolean updateData(@NonNull T data) {
    boolean ret = this.updateById(data, false);
    if (ret) {
      // 删除已有附件
      this.removeAttachmentRelations(data);
      // 保存附件
      ret = this.saveAttachmentRelations(data);
    }
    return ret;
  }

  @Override
  @Transactional
  default boolean deleteData(@NonNull T data) {
    boolean ret = this.removeById(data);
    if (ret) {
      // 删除附件
      this.removeAttachmentRelations(data);
    }
    return ret;
  }

  @Override
  @Transactional
  default boolean batchDeleteData(@NonNull Collection<T> data) {
    Set<I> ids = data.stream().map(IdEntity::getId).collect(Collectors.toSet());
    boolean ret = this.removeByIds(ids);
    if (ret) {
      // 删除附件
      this.batchRemoveAttachmentRelations(ids);
    }
    return ret;
  }

  /** 获取关联附件 */
  @NonNull
  default List<? extends IdEntity<String>> getAttachmentList(@NonNull I id) {
    // 获取附件
    QueryWrapper wrapper =
        this.getAttachmentService()
            .query()
            .eq(OBJECT_TYPE_FIELD, this.getObjectType())
            .eq(OBJECT_ID_FIELD, id);
    return this.getAttachmentService().list(wrapper);
  }

  /** 更新附件对象关联 */
  default boolean saveAttachmentRelations(@NonNull T data) {
    if (Objects.isNull(data.getAttachmentList()) || data.getAttachmentList().size() == 0) {
      return true;
    }
    List<String> ids =
        data.getAttachmentList().stream().map(IdEntity::getId).collect(Collectors.toList());
    UpdateChain<?> updateChain = this.getAttachmentService().updateChain();
    return updateChain
        .set(OBJECT_TYPE_FIELD, this.getObjectType())
        .set(OBJECT_ID_FIELD, data.getId())
        .in(FieldConstants.PRIMARY_KEY, ids)
        .update();
  }

  /** 删除附件关联记录 */
  default boolean removeAttachmentRelations(@NonNull T data) {
    UpdateChain<?> updateChain = this.getAttachmentService().updateChain();
    return updateChain
        .eq(OBJECT_TYPE_FIELD, this.getObjectType())
        .eq(OBJECT_ID_FIELD, data.getId())
        .set(OBJECT_TYPE_FIELD, "")
        .set(OBJECT_ID_FIELD, null)
        .update();
  }

  /** 批量删除附件关联记录 */
  default boolean batchRemoveAttachmentRelations(@NonNull Collection<I> ids) {
    UpdateChain<?> updateChain = this.getAttachmentService().updateChain();
    return updateChain
        .eq(OBJECT_TYPE_FIELD, this.getObjectType())
        .in(OBJECT_ID_FIELD, ids)
        .set(OBJECT_TYPE_FIELD, "")
        .set(OBJECT_ID_FIELD, null)
        .update();
  }
}
