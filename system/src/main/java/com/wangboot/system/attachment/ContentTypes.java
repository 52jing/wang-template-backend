package com.wangboot.system.attachment;

import cn.hutool.core.util.ArrayUtil;

/**
 * 常见文档类型
 *
 * @author wwtg99
 */
public class ContentTypes {

  public static final String[] OFFICE_WORD_DOCUMENTS =
      new String[] {
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
      };

  public static final String[] OFFICE_EXCEL_DOCUMENTS =
      new String[] {
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      };

  public static final String[] OFFICE_PPT_DOCUMENTS =
      new String[] {
        "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation"
      };

  public static final String[] OFFICE_PDF_DOCUMENTS = new String[] {"application/pdf"};

  public static final String[] OFFICE_DOCUMENTS =
      ArrayUtil.addAll(
          OFFICE_WORD_DOCUMENTS,
          OFFICE_EXCEL_DOCUMENTS,
          OFFICE_PPT_DOCUMENTS,
          OFFICE_PDF_DOCUMENTS);

  public static final String[] ZIP_ARCHIVES =
      new String[] {"application/zip", "application/x-zip-compressed"};

  public static final String[] TEXT_FILE = new String[] {"text/plain"};

  public static final String[] IMAGES =
      new String[] {"image/jpeg", "image/png", "image/gif", "image/tiff"};
}
