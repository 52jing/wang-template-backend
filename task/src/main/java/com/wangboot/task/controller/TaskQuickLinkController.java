package com.wangboot.task.controller;

import com.wangboot.core.auth.annotation.RestPermissionPrefix;
import com.wangboot.model.entity.controller.ControllerApiGroup;
import com.wangboot.model.entity.controller.EnableApi;
import com.wangboot.model.entity.controller.RestfulApiController;
import com.wangboot.model.entity.request.SearchStrategy;
import com.wangboot.model.entity.request.SortFilter;
import com.wangboot.task.entity.TaskQuickLink;
import com.wangboot.task.entity.table.TaskQuickLinkTableDef;
import com.wangboot.task.service.TaskQuickLinkService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 快捷菜单接口
 *
 * @author wwtg99
 */
@RestController
@RestPermissionPrefix(group = "task", name = "quick_link")
@RequestMapping("/task/quick_link")
@EnableApi(ControllerApiGroup.FULL)
public class TaskQuickLinkController
    extends RestfulApiController<String, TaskQuickLink, TaskQuickLinkService> {
  @Override
  public SortFilter[] configDefaultSort() {
    return new SortFilter[] {
      new SortFilter(TaskQuickLinkTableDef.TASK_QUICK_LINK.SORT.getName(), true)
    };
  }

  @Override
  public String[] configSearchableFields() {
    return new String[] {TaskQuickLinkTableDef.TASK_QUICK_LINK.NAME.getName()};
  }

  @NonNull
  @Override
  public SearchStrategy configSearchStrategy() {
    return SearchStrategy.BOTH_LIKE;
  }
}
