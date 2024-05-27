package com.wangboot.system.attachment;

import com.wangboot.system.entity.SysBgTask;
import com.wangboot.system.entity.vo.AttachmentVo;
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
  IExporter getExporter();

  /** 获取后台任务服务 */
  SysBgTaskService getBgTaskService();

  /** 后台导出 */
  default SysBgTask bgExport(
      String name, @NonNull Class<T> entityClass, @NonNull Supplier<Collection<T>> supplier) {
    return this.getBgTaskService()
        .addBackgroundTask(
            name,
            TASK_TYPE,
            () -> {
              try {
                AttachmentVo attachmentVo =
                    this.getExporter()
                        .export(
                            String.format("export_%s.xlsx", name), entityClass, supplier, "export");
                return new BgTaskResult(
                    attachmentVo.getId(),
                    attachmentVo.getOriginalFilename(),
                    BgTaskStatus.COMPLETED);
              } catch (IOException e) {
                return new BgTaskResult("", e.getMessage(), BgTaskStatus.FAILED);
              }
            });
  }
}
