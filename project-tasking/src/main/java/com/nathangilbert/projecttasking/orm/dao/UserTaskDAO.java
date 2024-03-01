package com.nathangilbert.projecttasking.orm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.nathangilbert.projecttasking.orm.dao.interfaces.IUserTaskDAO;
import com.nathangilbert.projecttasking.orm.entity.Task;
import com.nathangilbert.projecttasking.orm.entity.UserTasks;
import com.nathangilbert.projecttasking.rest.exceptions.ProjectNotFoundException;
import com.nathangilbert.projecttasking.rest.exceptions.TaskNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@Repository
public class UserTaskDAO implements IUserTaskDAO {
    
    private EntityManager entityManager;

    private final String USER_TASK_NOT_FOUND_MESSAGE = "User task association not found for "; 

    public UserTaskDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void assignUserToTask(long userId, long taskId) {
        UserTasks userTaskAssociation = new UserTasks(userId, taskId);
        entityManager.persist(userTaskAssociation);
    }

    @Override
    public void unassignUserFromTask(long userId, long taskId) {
        TypedQuery<UserTasks> userTasksQuery = entityManager.createQuery("FROM UserTasks WHERE userId = :userId AND taskId = :taskId",UserTasks.class);
        userTasksQuery.setParameter("userId", userId);
        userTasksQuery.setParameter("taskId", taskId);

        UserTasks userTask;
        try {
            userTask = userTasksQuery.getSingleResult();
        } catch (NoResultException exception) {
            throw new ProjectNotFoundException(
                    USER_TASK_NOT_FOUND_MESSAGE + "UserId:" + userId + " TaskId:" + taskId);
        }
        entityManager.remove(userTask);
    }
    
    @Override
    public List<Task> getAllAssignedTasks(long userId) {
        // Query for task ids assigned to user userId
        TypedQuery<Long> userTaskQuery = entityManager.createQuery("SELECT taskId FROM UserTasks WHERE userId=:userId", 
                Long.class);
        userTaskQuery.setParameter("userId", userId);

        List<Long> assignedTaskIds = userTaskQuery.getResultList();
        if (assignedTaskIds == null || assignedTaskIds.size() == 0) {
            throw new TaskNotFoundException("No tasks found for userId:" + userId);
        }

        // Query tasks that have ids in task ids 
        TypedQuery<Task> taskQuery = entityManager.createQuery("FROM Task WHERE taskId IN :taskIds", Task.class);

        taskQuery.setParameter("taskIds", assignedTaskIds);
        List<Task> assignedTasks = taskQuery.getResultList();
        if (assignedTaskIds == null || assignedTaskIds.size() == 0) {
            throw new TaskNotFoundException("No tasks found for userId:" + userId);
        }

        return assignedTasks;
    }

    @Override
    public List<Task> getAllAssignedTasks(long userId, long projectId) {
        // Gather all tasks assigned to the user with userId
        List<Task> projectTasks = getAllAssignedTasks(userId);

        if (projectTasks == null || projectTasks.size() == 0) {
            throw new TaskNotFoundException("No tasks found for userId:" + userId);
        }
        
        // Create a list a assigned task ids
        List<Long> assignedTaskIds = new ArrayList<>();
        for (Task task : projectTasks) {
            assignedTaskIds.add(task.getTaskId());
        }

        // Query for tasks that are from project with projectId using the list of task ids
        TypedQuery<Task> userTaskQuery = entityManager.createQuery("FROM Task WHERE projectId=:projectId AND taskId IN :taskIds",
                Task.class);
        userTaskQuery.setParameter("projectId", projectId);
        userTaskQuery.setParameter("taskIds", assignedTaskIds);
        
        List<Task> usersTasksFromProject = userTaskQuery.getResultList();

        if (usersTasksFromProject == null || usersTasksFromProject.size() == 0) {
            throw new TaskNotFoundException("No tasks found for userId:" + userId + " for projectId:" + projectId);
        }

        return usersTasksFromProject;
    }
    
}
