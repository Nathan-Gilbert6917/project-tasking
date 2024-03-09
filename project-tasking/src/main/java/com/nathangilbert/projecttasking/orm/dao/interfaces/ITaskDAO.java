package com.nathangilbert.projecttasking.orm.dao.interfaces;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.Task;

public interface ITaskDAO {
    void createTask(Task task);

    Task findById(long taskId);
    
    void updateTask(long taskId, Task task);

    void deleteTask(long taskId);

    List<Task> getProjectTasks(long projectId);

}
