package com.wangboot.system.attachment;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.wangboot.core.web.task.IBackgroundTask;
import com.wangboot.model.attachment.exporter.IExporter;
import com.wangboot.system.entity.vo.AttachmentVo;
import com.wangboot.system.event.BgTaskResult;
import com.wangboot.system.model.BgTaskStatus;
import com.wangboot.system.service.SysBgTaskService;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
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

  FileStorageService getFileStorageService();

  default String getExportFilename() {
    return "export.xlsx";
  }

  default String getExportContentType() {
    return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  }

  /** 后台导出 */
  default IBackgroundTask<BgTaskResult> bgExport(
      String name, @NonNull Class<T> entityClass, @NonNull Supplier<Collection<T>> supplier) {
    return this.getBgTaskService()
        .addTask(
            name,
            TASK_TYPE,
            () -> {
              try {
                byte[] bytes = this.getExporter().export(entityClass, supplier);
                //                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                FileInfo fileInfo =
                    this.getFileStorageService()
                        .of(bytes)
                        .setOriginalFilename(this.getExportFilename())
                        .setContentType(this.getExportContentType())
                        .setObjectType(entityClass.getName())
                        .setPath(DateUtil.today() + "/" + TASK_TYPE + "/")
                        .setHashCalculatorMd5()
                        .upload();
                AttachmentVo attachmentVo = BeanUtil.copyProperties(fileInfo, AttachmentVo.class);
                //                IAttachmentModel attachmentModel =
                //                    this.getExporter()
                //                        .export(
                //                            String.format("export_%s.xlsx", name), entityClass,
                // supplier, "export");
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
