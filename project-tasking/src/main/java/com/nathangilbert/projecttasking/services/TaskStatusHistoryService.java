package com.nathangilbert.projecttasking.services;

import java.util.List;

import com.nathangilbert.projecttasking.orm.dao.TaskStatusHistoryDAO;
import com.nathangilbert.projecttasking.orm.entity.TaskStatusHistory;
import com.nathangilbert.projecttasking.services.interfaces.ITaskStatusHistoryService;

import jakarta.transaction.Transactional;

public class TaskStatusHistoryService implements ITaskStatusHistoryService {

    private final TaskStatusHistoryDAO taskStatusHistoryDAO;

    public TaskStatusHistoryService(TaskStatusHistoryDAO taskStatusHistoryDAO) {
        this.taskStatusHistoryDAO = taskStatusHistoryDAO;
    }

    @Override
    @Transactional
    public void addTaskStatusHistory(TaskStatusHistory taskStatusHistory) {
        this.taskStatusHistoryDAO.addTaskStatusHistory(taskStatusHistory);
    }

    @Override
    public TaskStatusHistory getLatestTaskStatusHistory(Long taskId) {
        return this.taskStatusHistoryDAO.getLatestTaskStatusHistory(taskId);
    }

    @Override
    public List<TaskStatusHistory> getAllTaskStatusHistory(Long taskId) {
        return this.taskStatusHistoryDAO.getAllTaskStatusHistory(taskId);
    }

}
