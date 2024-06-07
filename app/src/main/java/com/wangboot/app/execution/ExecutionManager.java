package com.wangboot.app.execution;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangboot.app.execution.datasource.DatasourceParamHolder;
import com.wangboot.app.execution.datasource.DatasourceProcessor;
import com.wangboot.app.execution.datasource.IDatasource;
import com.wangboot.app.execution.render.ITemplateRender;
import com.wangboot.app.execution.render.RenderContext;
import com.wangboot.app.execution.render.TemplateRenderFactory;
import com.wangboot.app.template.entity.*;
import com.wangboot.app.template.entity.table.TplRenderExecutionTableDef;
import com.wangboot.app.template.service.*;
import com.wangboot.core.errorcode.ErrorCodeException;
import com.wangboot.core.web.event.IEventPublisher;
import com.wangboot.framework.exception.ErrorCode;
import com.wangboot.model.attachment.IAttachmentModel;
import com.wangboot.model.entity.FieldConstants;
import com.wangboot.system.entity.SysAttachment;
import com.wangboot.system.entity.vo.AttachmentVo;
import com.wangboot.system.service.SysAttachmentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 渲染执行管理器
 *
 * @author wwtg99
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ExecutionManager implements IEventPublisher {

  private final static String SUCCESS_MESSAGE = "SUCCESS";

  private final TplDatasourceService datasourceService;

  private final TplTemplateService templateService;

  private final TplRenderExecutionService renderExecutionService;

  private final DatasourceProcessor datasourceProcessor;

  private final ObjectMapper objectMapper;

  private final TemplateRenderFactory templateRenderFactory;

  private final SysAttachmentService attachmentService;

  private final TplExecutionResultService executionResultService;

  private final FileStorageService fileStorageService;

  @Getter
  @Setter
  private ApplicationEventPublisher applicationEventPublisher;

  /**
   * 启动渲染执行
   *
   * @param datasourceId 数据源ID
   * @param templateId 模板ID
   * @param params 执行参数
   * @return 渲染执行
   */
  @NonNull
  public TplRenderExecution startRenderExecution(String datasourceId, String templateId, String filename, @Nullable DatasourceParamHolder params) {
    // 输入参数检查
    if (!StringUtils.hasText(datasourceId)) {
      throw new ErrorCodeException(ErrorCode.INVALID_DATASOURCE);
    }
    TplDatasource datasource = this.datasourceService.getDataById(datasourceId);
    if (Objects.isNull(datasource)) {
      throw new ErrorCodeException(ErrorCode.INVALID_DATASOURCE);
    }
    if (!StringUtils.hasText(templateId)) {
      throw new ErrorCodeException(ErrorCode.INVALID_TEMPLATE);
    }
    TplTemplate template = this.templateService.getDataById(templateId);
    if (Objects.isNull(template)) {
      throw new ErrorCodeException(ErrorCode.INVALID_TEMPLATE);
    }
    if (Objects.isNull(params)) {
      params = new DatasourceParamHolder();
    }
    // 数据源参数检查
    this.checkDatasourceParams(datasourceId, params);
    // 创建渲染执行任务
    try {
      TplRenderExecution renderExecution = new TplRenderExecution();
      renderExecution.setDatasourceId(datasourceId);
      renderExecution.setTemplateId(templateId);
      renderExecution.setParams(this.objectMapper.writeValueAsString(params));
      renderExecution.setTemplateName(template.getName());
      renderExecution.setTemplateType(template.getType());
      renderExecution.setFilename(StringUtils.hasText(filename) ? filename : template.getDefFilename());
      renderExecution.setDatasourceName(datasource.getName());
      renderExecution.setDatasourceType(datasource.getType());
      boolean ret = this.renderExecutionService.save(renderExecution);
      if (ret) {
        // 发送渲染事件
        this.publishEvent(new RenderExecutionEvent(renderExecution, params));
        return renderExecution;
      } else {
        log.error("Failed to save render execution!");
        throw new ErrorCodeException(ErrorCode.RENDER_FAILED);
      }
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
      throw new ErrorCodeException(ErrorCode.RENDER_FAILED);
    }
  }

  /**
   * 监听渲染执行事件
   *
   * @param event 渲染执行事件
   */
  @EventListener
  @Async
  public void listenRenderExecutionListener(RenderExecutionEvent event) {
    TplRenderExecution renderExecution = event.getRenderExecution();
    // 更新执行状态
    this.renderExecutionService.updateChain().eq(FieldConstants.PRIMARY_KEY, renderExecution.getId()).set(TplRenderExecutionTableDef.TPL_RENDER_EXECUTION.STATUS, ExecutionStatus.PENDING).update();
    // 创建结果
    TplExecutionResult result = new TplExecutionResult();
    result.setExecutionId(renderExecution.getId());
    try {
      // 从数据源获取数据模型
      Object data = this.retrieveData(event.getRenderExecution().getDatasourceId(), event.getParams());
      if (Objects.isNull(data)) {
        throw new ErrorCodeException(ErrorCode.RETRIEVE_EMPTY_DATA);
      }
      // 执行渲染
      ITemplateRender templateRender = this.getTemplateRender(renderExecution.getTemplateType());
      List<? extends IAttachmentModel> atts = templateService.getAttachmentList(renderExecution.getTemplateId());
      if (atts.size() > 0) {
        String templateAttachmentId = atts.get(0).getId();
        RenderContext context = this.buildContext(renderExecution, data);
        AttachmentVo attachmentVo = this.renderAndUpload(templateRender, templateAttachmentId, renderExecution.getTemplateName(), renderExecution.getFilename(), context);
        // 保存结果
        result.setMessage(SUCCESS_MESSAGE);
        result.setAttachments(Collections.singletonList(attachmentVo));
        result.setStatus(ExecutionStatus.COMPLETED);
        boolean ret = this.executionResultService.save(result);
        // 更新执行状态
        if (ret) {
          this.renderExecutionService.updateChain().eq(FieldConstants.PRIMARY_KEY, renderExecution.getId()).set(TplRenderExecutionTableDef.TPL_RENDER_EXECUTION.STATUS, ExecutionStatus.COMPLETED).update();
        } else {
          this.renderExecutionService.updateChain().eq(FieldConstants.PRIMARY_KEY, renderExecution.getId()).set(TplRenderExecutionTableDef.TPL_RENDER_EXECUTION.STATUS, ExecutionStatus.FAILED).update();
        }
      } else {
        log.error("No template attachment found for {}", renderExecution.getTemplateId());
      }
    } catch (Exception e) {
      result.setMessage(e.getMessage());
      result.setStatus(ExecutionStatus.FAILED);
      this.executionResultService.save(result);
      // 更新执行状态
      this.renderExecutionService.updateChain().eq(FieldConstants.PRIMARY_KEY, renderExecution.getId()).set(TplRenderExecutionTableDef.TPL_RENDER_EXECUTION.STATUS, ExecutionStatus.FAILED).update();
    }
  }

  /**
   * 检查数据源参数
   *
   * @param datasourceId 数据源ID
   * @param params 数据源参数
   */
  private void checkDatasourceParams(String datasourceId, @NonNull DatasourceParamHolder params) {
    List<TplDatasourceParam> datasourceParamList = this.datasourceService.getDatasourceParams(datasourceId);
    datasourceParamList.forEach(dp -> {
      // 检查必填
      if (dp.getRequired() && !params.containsKey(dp.getName())) {
        throw new ErrorCodeException(ErrorCode.PARAM_IS_REQUIRED, new String[]{dp.getName()});
      }
      // 填充默认值
      if (!params.containsKey(dp.getName())) {
        params.put(dp.getName(), dp.getDefVal());
      }
    });
  }

  /**
   * 获取数据模型
   *
   * @param datasourceId 数据源ID
   * @param params 执行参数
   * @return 数据模型
   */
  @Nullable
  private Object retrieveData(String datasourceId, DatasourceParamHolder params) {
    IDatasource datasource = this.datasourceProcessor.getDatasource(datasourceId);
    if (Objects.isNull(datasource)) {
      throw new ErrorCodeException(ErrorCode.INVALID_DATASOURCE);
    }
    return datasource.retrieveData(params);
  }

  /**
   * 载入模板附件
   *
   * @param attachmentId 附件ID
   * @return 附件字节数组
   * @throws IOException IO异常
   */
  @NonNull
  private byte[] loadTemplateAttachment(String attachmentId) throws IOException {
    SysAttachment attachment = attachmentService.getDataById(attachmentId);
    if (Objects.isNull(attachment)) {
      throw new ErrorCodeException(ErrorCode.INVALID_TEMPLATE);
    }
    try (ByteArrayOutputStream bao = new ByteArrayOutputStream()) {
      fileStorageService
        .download(attachmentService.toFileInfo(attachment))
        .outputStream(bao);
      return bao.toByteArray();
    }
  }

  /**
   * 获取模版渲染器
   *
   * @param type 模版类型
   * @return 模版渲染器
   */
  @NonNull
  private ITemplateRender getTemplateRender(String type) {
    ITemplateRender templateRender = this.templateRenderFactory.getTemplateRender(type);
    if (Objects.isNull(templateRender)) {
      throw new ErrorCodeException(ErrorCode.INVALID_TEMPLATE);
    }
    return templateRender;
  }

  /**
   * 获取结果上传路径
   *
   * @return 路径
   */
  private String getResultUploadPath() {
    return "render_results/";
  }

  /**
   * 根据模板渲染文件
   *
   * @param templateRender 模板渲染器
   * @param templateAttachmentId 模板附件ID
   * @param templateName 模板名称
   * @param filename 文件名
   * @param context 上下文
   * @return 生成文件附件
   */
  @NonNull
  private AttachmentVo renderAndUpload(@NonNull ITemplateRender templateRender, String templateAttachmentId, String templateName, String filename, @NonNull RenderContext context) {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      // 获取模版文件
      byte[] bytes = this.loadTemplateAttachment(templateAttachmentId);
      // 生成文件
      templateRender.setTemplate(templateName, bytes).renderAndOutput(context, outputStream);
      if (!StringUtils.hasText(filename)) {
        filename = templateName;
      }
      // 上传结果附件
      FileInfo fileInfo = this.fileStorageService.of(outputStream.toByteArray(), filename, templateRender.getContentType()).setPath(this.getResultUploadPath()).upload();
      if (Objects.nonNull(fileInfo)) {
        return BeanUtil.copyProperties(fileInfo, AttachmentVo.class);
      } else {
        throw new ErrorCodeException(ErrorCode.UPLOAD_FAILED);
      }
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  @NonNull
  private RenderContext buildContext(@NonNull TplRenderExecution execution, Object dataModel) throws JsonProcessingException {
    Map<String, Object> envs = new HashMap<>();
    LocalDateTime now = LocalDateTime.now();
    envs.put("year", now.getYear());
    envs.put("month", now.getMonth());
    envs.put("day", now.getDayOfMonth());
    envs.put("dayOfWeek", now.getDayOfWeek());
    envs.put("hour", now.getHour());
    envs.put("minute", now.getMinute());
    envs.put("second", now.getSecond());
    DatasourceParamHolder params = this.objectMapper.readValue(execution.getParams(), DatasourceParamHolder.class);
    return new RenderContext(execution.getTemplateName(), execution.getDatasourceName(), params, envs, dataModel);
  }
}
