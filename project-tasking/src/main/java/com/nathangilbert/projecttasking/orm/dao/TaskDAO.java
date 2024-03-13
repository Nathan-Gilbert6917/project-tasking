package com.nathangilbert.projecttasking.orm.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nathangilbert.projecttasking.orm.dao.interfaces.ITaskDAO;
import com.nathangilbert.projecttasking.orm.entity.Sprint;
import com.nathangilbert.projecttasking.orm.entity.Task;
import com.nathangilbert.projecttasking.orm.entity.TaskStatusHistory;
import com.nathangilbert.projecttasking.orm.enums.TaskPriority;
import com.nathangilbert.projecttasking.orm.enums.TaskStatus;
import com.nathangilbert.projecttasking.rest.exceptions.TaskNotFoundException;
import com.nathangilbert.projecttasking.services.TaskStatusHistoryService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class TaskDAO implements ITaskDAO {

    private EntityManager entityManager;

    private TaskStatusHistoryService taskStatusHistoryService;

    private final String TASK_NOT_FOUND_MESSAGE = "Task not found for ID:";

    @Autowired
    public TaskDAO(EntityManager entityManager, TaskStatusHistoryService taskStatusHistoryService) {
        this.entityManager = entityManager;
        this.taskStatusHistoryService = taskStatusHistoryService;
    }

    @Override
    public void createTask(Task task) {
        entityManager.persist(task);
    }

    @Override
    public Task findById(long taskId) {
        Task task = entityManager.find(Task.class, taskId);
        if (task == null) {
            throw new TaskNotFoundException(TASK_NOT_FOUND_MESSAGE + taskId);
        }
        return task;
    }

    @Override
    public void updateTask(long taskId, Task updatedTask) {
       Task task = entityManager.find(Task.class, taskId);
        if (task == null) {
            throw new TaskNotFoundException(TASK_NOT_FOUND_MESSAGE + taskId);
        }

        String updatedTaskname = updatedTask.getTaskName();
        String updatedDescription = updatedTask.getDescription();
        LocalDateTime updatedDueDate = updatedTask.getDueDate();
        Integer updatedEffortValue = updatedTask.getEffortValue();
        Integer updatedRiskValue = updatedTask.getRiskValue();
        Sprint updatedSprintValue = updatedTask.getSprint();
        TaskPriority updatedPriority = updatedTask.getPriority();
        TaskStatus updatedStatus = updatedTask.getStatus();

        if (updatedTaskname != null) {
            task.setTaskName(updatedTaskname);
        }
        if (updatedDescription != null) {
            task.setDescription(updatedDescription);
        }
        if (updatedDueDate != null) {
            task.setDueDate(updatedDueDate);
        }
        if (updatedEffortValue != null) {
            task.setEffortValue(updatedEffortValue);
        }
        if (updatedRiskValue != null) {
            task.setRiskValue(updatedRiskValue);
        }
        if (updatedSprintValue != null) {
            task.setSprint(updatedSprintValue);
        }
        if (updatedPriority != null) {
            task.setPriority(updatedPriority);
        }
        if (updatedStatus != null) {
            TaskStatus oldStatus = task.getStatus();
            task.setStatus(updatedStatus);
            TaskStatusHistory taskStatusHistory = new TaskStatusHistory(taskId, oldStatus, updatedStatus);
            taskStatusHistoryService.addTaskStatusHistory(taskStatusHistory);
        }

        entityManager.merge(task);
    }

    @Override
    public void deleteTask(long taskId) {
        Task task = entityManager.find(Task.class, taskId);
        if (task == null) {
            throw new TaskNotFoundException(TASK_NOT_FOUND_MESSAGE + taskId);
        }
        entityManager.remove(task);
    }

    @Override
    public List<Task> getProjectTasks(long projectId) {
        TypedQuery<Task> assignedTasksQuery = entityManager.createQuery("FROM Task WHERE projectId=:projectId", Task.class);
        assignedTasksQuery.setParameter("projectId", projectId);

        List<Task> tasks = assignedTasksQuery.getResultList();

        if (tasks == null || tasks.size() == 0) {
            throw new TaskNotFoundException("0 Tasks were found for projectId:" + projectId);
        }

        return tasks;
    }
    
}
