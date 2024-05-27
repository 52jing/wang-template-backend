package com.wangboot.task.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wangboot.task.entity.TaskQuickLink;
import com.wangboot.task.mapper.TaskQuickLinkMapper;
import com.wangboot.task.service.TaskQuickLinkService;
import org.springframework.stereotype.Service;

@Service
public class TaskQuickLinkServiceImpl extends ServiceImpl<TaskQuickLinkMapper, TaskQuickLink>
    implements TaskQuickLinkService {}
