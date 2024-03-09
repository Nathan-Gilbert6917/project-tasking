package com.nathangilbert.projecttasking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.nathangilbert.projecttasking.orm.dao.TaskDAO;
import com.nathangilbert.projecttasking.orm.dao.UserTaskDAO;
import com.nathangilbert.projecttasking.orm.entity.Task;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig
@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskDAO taskDAO;

    @Mock
    private UserTaskDAO userTaskDAO;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testCreateTask() {
        Task task = new Task();

        taskService.createTask(task);

        verify(taskDAO, times(1)).createTask(task);
    }

    @Test
    public void testFindById() {
        long taskId = 1;
        Task task = new Task();
        task.setTaskId(taskId);
        when(taskDAO.findById(taskId)).thenReturn(task);

        Task foundTask = taskService.findById(taskId);

        assertEquals(task, foundTask);
    }

    @Test
    public void testUpdateTask() {
        long taskId = 1;
        Task task = new Task();
        task.setTaskId(taskId);

        taskService.updateTask(taskId, task);

        verify(taskDAO, times(1)).updateTask(taskId, task);
    }

    @Test
    public void testDeleteTask() {
        long taskId = 1;

        taskService.deleteTask(taskId);

        verify(taskDAO, times(1)).deleteTask(taskId);
    }

    @Test
    public void testAssignTask() {
        long userId = 1;
        long taskId = 1;

        taskService.assignTask(userId, taskId);

        verify(userTaskDAO, times(1)).assignUserToTask(userId, taskId);
    }

    @Test
    public void testUnassignTask() {
        long userId = 1;
        long taskId = 1;

        taskService.unassignTask(userId, taskId);

        verify(userTaskDAO, times(1)).unassignUserFromTask(userId, taskId);
    }
}
