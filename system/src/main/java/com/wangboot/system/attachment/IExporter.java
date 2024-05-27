package com.wangboot.system.attachment;

import com.wangboot.system.entity.vo.AttachmentVo;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;
import org.springframework.lang.NonNull;

/**
 * 导出服务
 *
 * @author wwtg99
 */
public interface IExporter {

  String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

  /** 导出到附件 */
  @NonNull
  <T> AttachmentVo export(
      String filename,
      @NonNull Class<T> entityClass,
      @NonNull Supplier<Collection<T>> supplier,
      String sheetName)
      throws IOException;
}
