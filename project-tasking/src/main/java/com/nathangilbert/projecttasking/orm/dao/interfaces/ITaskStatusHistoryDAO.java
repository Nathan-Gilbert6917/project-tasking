package com.nathangilbert.projecttasking.orm.dao.interfaces;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.TaskStatusHistory;

public interface ITaskStatusHistoryDAO {
    void addTaskStatusHistory(TaskStatusHistory taskStatusHistory);

    TaskStatusHistory getLatestTaskStatusHistory(Long taskId);

    List<TaskStatusHistory> getAllTaskStatusHistory(Long taskId);
}
