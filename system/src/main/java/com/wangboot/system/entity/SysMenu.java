package com.wangboot.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.wangboot.model.entity.event.EnableOperationLog;
import com.wangboot.model.entity.impl.CommonTreeEntity;
import com.wangboot.system.listener.EntityChangeListener;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 实体类。
 *
 * @author wwtg99
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@EnableOperationLog
@Table(
    value = "wb_sys_menu",
    onInsert = EntityChangeListener.class,
    onUpdate = EntityChangeListener.class)
public class SysMenu extends CommonTreeEntity<String> {

  @Id(keyType = KeyType.Generator, value = KeyGenerators.uuid)
  private String id;

  @NotBlank(message = "message.name_not_blank")
  @Size(max = 90)
  private String name;

  @Size(max = 90)
  private String caption = "";

  @Size(max = 200)
  private String icon = "";

  @Size(max = 200)
  private String path = "";

  @Override
  public boolean hasChildren() {
    return true;
  }

  public SysMenu(
      String id,
      String name,
      String caption,
      String icon,
      String path,
      String parentId,
      Integer sort) {
    this(id, name, caption, icon, path);
    this.setParentId(parentId);
    this.setSort(sort);
  }
}
