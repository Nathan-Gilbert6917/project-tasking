package com.nathangilbert.projecttasking.orm.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nathangilbert.projecttasking.orm.dao.interfaces.ITaskStatusHistoryDAO;
import com.nathangilbert.projecttasking.orm.entity.TaskStatusHistory;
import com.nathangilbert.projecttasking.rest.exceptions.TaskStatusHistoryNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class TaskStatusHistoryDAO implements ITaskStatusHistoryDAO {


    private EntityManager entityManager;

    private final String TASK_STATUS_HISTORY_NOT_FOUND_MESSAGE = "Task status history not found for ID:";

    @Autowired
    public TaskStatusHistoryDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addTaskStatusHistory(TaskStatusHistory taskStatusHistory) {
        this.entityManager.persist(taskStatusHistory);
    }

    @Override
    public TaskStatusHistory getLatestTaskStatusHistory(Long taskId) {
        String jpql = "FROM TaskStatusHistory WHERE taskId=:taskId ORDER BY created_at DESC";
        TypedQuery<TaskStatusHistory> taskStatusHistoryQuery = this.entityManager.createQuery(jpql, 
                TaskStatusHistory.class);
        taskStatusHistoryQuery.setParameter("taskId", taskId);

        TaskStatusHistory taskStatusHistory = taskStatusHistoryQuery.getSingleResult();

        if (taskStatusHistory == null) {
            throw new TaskStatusHistoryNotFoundException(TASK_STATUS_HISTORY_NOT_FOUND_MESSAGE + taskId);
        }
        return taskStatusHistory;
    }

    @Override
    public List<TaskStatusHistory> getAllTaskStatusHistory(Long taskId) {
        String jpql = "FROM TaskStatusHistory WHERE taskId=:taskId ORDER BY created_at DESC";
        TypedQuery<TaskStatusHistory> taskStatusHistoryQuery = this.entityManager.createQuery(jpql,
                TaskStatusHistory.class);
        taskStatusHistoryQuery.setParameter("taskId", taskId);

        List<TaskStatusHistory> taskStatusHistories = taskStatusHistoryQuery.getResultList();

        if (taskStatusHistories == null) {
            throw new TaskStatusHistoryNotFoundException(TASK_STATUS_HISTORY_NOT_FOUND_MESSAGE + taskId);
        }
        return taskStatusHistories;    
    }

}
