package com.nathangilbert.projecttasking.orm.dao;

import com.nathangilbert.projecttasking.orm.entity.Task;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig
@SpringBootTest
public class TaskDAOTest {

    @Mock
    private EntityManager entityManager;


    @Mock
    private TypedQuery<Task> tasksQuery;


    @InjectMocks
    private TaskDAO taskDAO;

    private final long TASK_ID = 1;
    private final long PROJECT_ID = 1;

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setTaskId(TASK_ID);

        taskDAO.createTask(task);

        verify(entityManager, times(1)).persist(task);
    }

    @Test
    public void testFindById_TaskFound() {
        Task task = new Task();
        task.setTaskId(TASK_ID);
        when(entityManager.find(Task.class, TASK_ID)).thenReturn(task);

        Task foundTask = taskDAO.findById(TASK_ID);

        assertNotNull(foundTask);
        assertEquals(task, foundTask);
    }

    @Test
    public void testFindById_TaskNotFound() {
        when(entityManager.find(Task.class, TASK_ID)).thenReturn(null);

        assertThrows(TaskNotFoundException.class, () -> {
            taskDAO.findById(TASK_ID);
        });
    }

    @Test
    public void testUpdateTask_TaskFound() {
        LocalDateTime dueDate =  LocalDateTime.of(2024, 4, 15, 12, 0);
        LocalDateTime newDueDate = LocalDateTime.of(2024, 3, 10, 12, 0);

        Task task = new Task();
        task.setTaskId(TASK_ID);
        task.setTaskName("Task1");
        task.setDescription("Task 1 Description");
        task.setDueDate(dueDate);
        task.setEffortValue(3);
        task.setRiskValue(2);

        Task updatedTask = new Task();
        updatedTask.setTaskId(TASK_ID);
        updatedTask.setTaskName("Task Updated");
        updatedTask.setDescription("New Task Description");
        updatedTask.setDueDate(newDueDate);
        updatedTask.setEffortValue(5);
        updatedTask.setRiskValue(5);

        when(entityManager.find(Task.class, TASK_ID)).thenReturn(task);

        taskDAO.updateTask(TASK_ID, updatedTask);

        assertEquals(updatedTask.getTaskName(), task.getTaskName());
        assertEquals(updatedTask.getDescription(), task.getDescription());
        assertEquals(updatedTask.getDueDate(), task.getDueDate());
        assertEquals(updatedTask.getEffortValue(), task.getEffortValue());
        assertEquals(updatedTask.getRiskValue(), task.getRiskValue());
    }

    @Test
    public void testUpdateTask_TaskNotFound() {
        LocalDateTime dueDate = LocalDateTime.of(2024, 4, 15, 12, 0);
        LocalDateTime newDueDate = LocalDateTime.of(2024, 3, 10, 12, 0);

        Task task = new Task();
        task.setTaskId(TASK_ID);
        task.setTaskName("Task1");
        task.setDescription("Task 1 Description");
        task.setDueDate(dueDate);
        task.setEffortValue(3);
        task.setRiskValue(2);

        Task updatedTask = new Task();
        updatedTask.setTaskId(TASK_ID);
        updatedTask.setTaskName("Task Updated");
        updatedTask.setDescription("New Task Description");
        updatedTask.setDueDate(newDueDate);
        updatedTask.setEffortValue(5);
        updatedTask.setRiskValue(5);

        when(entityManager.find(Task.class, TASK_ID)).thenReturn(null);

        assertThrows(TaskNotFoundException.class, () -> taskDAO.updateTask(TASK_ID, updatedTask));
    }

    @Test
    public void testDeleteTask_TaskFound() {
        Task task = new Task();
        task.setTaskId(TASK_ID);
        when(entityManager.find(Task.class, TASK_ID)).thenReturn(task);

        taskDAO.deleteTask(TASK_ID);

        verify(entityManager, times(1)).remove(task);
    }

    @Test
    public void testDeleteTask_TaskNotFound() {
        Task task = new Task();
        task.setTaskId(TASK_ID);
        when(entityManager.find(Task.class, TASK_ID)).thenReturn(null);

        assertThrows(TaskNotFoundException.class, () -> taskDAO.deleteTask(TASK_ID));
    
        verify(entityManager, times(0)).remove(task);
    }

    @Test
    public void testGetProjectTasks() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();

        task1.setTaskId(TASK_ID);
        tasks.add(task1);
        when(entityManager.createQuery("FROM Task WHERE projectId=:projectId", Task.class)).thenReturn(tasksQuery);
        when(tasksQuery.getResultList()).thenReturn(tasks);

        List<Task> foundTasks = taskDAO.getProjectTasks(PROJECT_ID);

        assertEquals(tasks, foundTasks);
    }

    @Test
    public void testGetProjectTasks_NoTasksFound() {
        List<Task> tasks = new ArrayList<>();
        when(entityManager.createQuery("FROM Task WHERE projectId=:projectId", Task.class)).thenReturn(tasksQuery);
        when(tasksQuery.getResultList()).thenReturn(tasks);

        assertThrows(TaskNotFoundException.class, () -> {
            taskDAO.getProjectTasks(PROJECT_ID);
        });
    }
}
