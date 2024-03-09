package com.nathangilbert.projecttasking.orm.dao.interfaces;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.Task;

public interface IUserTaskDAO {
    void assignUserToTask(long userId, long taskId);

    void unassignUserFromTask(long userId, long taskId);

    List<Task> getAllAssignedTasks(long userId);

    List<Task> getAllAssignedTasks(long userId, long projectId);
}
