package com.wangboot.system.attachment;

import com.wangboot.core.web.task.IBackgroundTask;
import com.wangboot.model.attachment.IAttachmentModel;
import com.wangboot.model.attachment.IExcelExporter;
import com.wangboot.system.event.BgTaskResult;
import com.wangboot.system.model.BgTaskStatus;
import com.wangboot.system.service.SysBgTaskService;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;
import org.springframework.lang.NonNull;

/**
 * 后台导出接口
 *
 * @author wwtg99
 * @param <T> 对象类型
 */
public interface IBgExportApi<T> {

  String TASK_TYPE = "export";

  /** 获取导出服务 */
  IExcelExporter getExporter();

  /** 获取后台任务服务 */
  SysBgTaskService getBgTaskService();

  /** 后台导出 */
  default IBackgroundTask<BgTaskResult> bgExport(
      String name, @NonNull Class<T> entityClass, @NonNull Supplier<Collection<T>> supplier) {
    return this.getBgTaskService()
        .addTask(
            name,
            TASK_TYPE,
            () -> {
              try {
                IAttachmentModel attachmentModel =
                    this.getExporter()
                        .export(
                            String.format("export_%s.xlsx", name), entityClass, supplier, "export");
                return new BgTaskResult(
                    attachmentModel.getId(),
                    attachmentModel.getOriginalFilename(),
                    BgTaskStatus.COMPLETED);
              } catch (IOException e) {
                return new BgTaskResult("", e.getMessage(), BgTaskStatus.FAILED);
              }
            });
  }
}
