package com.wangboot.system.attachment;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.wangboot.system.entity.vo.AttachmentVo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 基于内存的简单导出服务
 *
 * @author wwtg99
 */
@Component
@RequiredArgsConstructor
public class EasyBytesExporter implements IExporter {

  private final FileStorageService fileStorageService;

  @Override
  @NonNull
  public <T> AttachmentVo export(
      String filename,
      @NonNull Class<T> entityClass,
      @NonNull Supplier<Collection<T>> supplier,
      String sheetName)
      throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    EasyExcel.write(bos, entityClass).sheet(sheetName).doWrite(supplier.get());
    bos.flush();
    bos.close();
    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
    FileInfo fileInfo =
        this.fileStorageService
            .of(bis)
            .setOriginalFilename(filename)
            .setContentType(IExporter.XLSX_CONTENT_TYPE)
            .setObjectType(entityClass.getName())
            .setPath(DateUtil.today() + "/exports/")
            .setHashCalculatorMd5()
            .upload();
    return BeanUtil.copyProperties(fileInfo, AttachmentVo.class);
  }
}
