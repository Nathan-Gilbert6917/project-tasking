package com.nathangilbert.projecttasking.services.interfaces;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.TaskStatusHistory;

public interface ITaskStatusHistoryService {
    void addTaskStatusHistory(TaskStatusHistory taskStatusHistory);

    TaskStatusHistory getLatestTaskStatusHistory(Long taskId);

    List<TaskStatusHistory> getAllTaskStatusHistory(Long taskId);
}
