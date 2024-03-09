package com.nathangilbert.projecttasking.services.interfaces;

import com.nathangilbert.projecttasking.orm.entity.Task;

public interface ITaskService {
    void createTask(Task task);

    Task findById(long taskId);
    
    void updateTask(long taskId, Task task);

    void deleteTask(long taskId);

    void assignTask(long userId, long taskId);

    void unassignTask(long userId, long taskId);
    
}
