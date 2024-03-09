package com.nathangilbert.projecttasking.services;

import org.springframework.stereotype.Service;

import com.nathangilbert.projecttasking.orm.dao.TaskDAO;
import com.nathangilbert.projecttasking.orm.dao.UserTaskDAO;
import com.nathangilbert.projecttasking.orm.entity.Task;
import com.nathangilbert.projecttasking.services.interfaces.ITaskService;

import jakarta.transaction.Transactional;

@Service
public class TaskService implements ITaskService {

    private TaskDAO taskDAO;
    private UserTaskDAO userTaskDAO;

    public TaskService(TaskDAO taskDAO, UserTaskDAO userTaskDAO) {
        this.taskDAO = taskDAO;
        this.userTaskDAO = userTaskDAO;
    }

    @Override
    @Transactional
    public void createTask(Task task) {
        this.taskDAO.createTask(task);
    }

    @Override
    public Task findById(long taskId) {
        return this.taskDAO.findById(taskId);
    }

    @Override
    @Transactional
    public void updateTask(long taskId, Task task) {
        this.taskDAO.updateTask(taskId, task);
    }

    @Override
    @Transactional
    public void deleteTask(long taskId) {
        this.taskDAO.deleteTask(taskId);
    }

    @Override
    @Transactional
    public void assignTask(long userId, long taskId) {
        this.userTaskDAO.assignUserToTask(userId, taskId);
    }

    @Override
    @Transactional
    public void unassignTask(long userId, long taskId) {
        this.userTaskDAO.unassignUserFromTask(userId, taskId);
    }
}
