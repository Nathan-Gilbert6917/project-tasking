package com.nathangilbert.projecttasking.orm.dao;

import com.nathangilbert.projecttasking.orm.entity.Task;
import com.nathangilbert.projecttasking.orm.entity.UserTasks;
import com.nathangilbert.projecttasking.rest.exceptions.TaskNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig
@SpringBootTest
public class UserTaskDAOTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Long> longsQuery;

    @Mock
    private TypedQuery<Task> tasksQuery;

    @Mock
    private TypedQuery<UserTasks> userTaskQuery;

    @InjectMocks
    private UserTaskDAO userTaskDAO;

    private final long USER_ID = 1;
    private final long TASK_ID = 1;


    @Test
    public void testAssignUserToTask() {
        userTaskDAO.assignUserToTask(USER_ID, TASK_ID);

        verify(entityManager, times(1)).persist(any(UserTasks.class));
    }

    @Test
    public void testUnassignUserFromTask() {
        UserTasks userTask = new UserTasks(USER_ID, TASK_ID);
        when(entityManager.createQuery("FROM UserTasks WHERE userId = :userId AND taskId = :taskId", UserTasks.class)).thenReturn(
                userTaskQuery);
        when(userTaskQuery.getSingleResult()).thenReturn(userTask);

        userTaskDAO.unassignUserFromTask(USER_ID, TASK_ID);

        verify(entityManager, times(1)).remove(userTask);
    }

    @Test
    public void testGetAllAssignedTasks() {
        List<Long> assignedTaskIds = new ArrayList<>();
        assignedTaskIds.add(TASK_ID);
        when(entityManager.createQuery("SELECT taskId FROM UserTasks WHERE userId=:userId", Long.class)).thenReturn(
                longsQuery);

        when(longsQuery.getResultList()).thenReturn(assignedTaskIds);

        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setTaskId(TASK_ID);
        tasks.add(task);
        when(entityManager.createQuery("FROM Task WHERE taskId IN :taskIds", Task.class)).thenReturn(tasksQuery);

        when(tasksQuery.getResultList()).thenReturn(tasks);

        List<Task> foundTasks = userTaskDAO.getAllAssignedTasks(USER_ID);

        assertEquals(tasks, foundTasks);
    }

    @Test
    public void testGetAllAssignedTasks_NoTasksFound() {
        List<Long> assignedTaskIds = new ArrayList<>();
        when(entityManager.createQuery("SELECT taskId FROM UserTasks WHERE userId=:userId", Long.class)).thenReturn(
                longsQuery);

        when(longsQuery.getResultList()).thenReturn(assignedTaskIds);

        assertThrows(TaskNotFoundException.class, () -> {
            userTaskDAO.getAllAssignedTasks(USER_ID);
        });
    }

}
