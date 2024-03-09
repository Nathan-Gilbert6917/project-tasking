package com.nathangilbert.projecttasking.orm.dao.interfaces;

import java.util.List;

import com.nathangilbert.projecttasking.orm.entity.TaskComment;

public interface ITaskCommentDAO {
    void createTaskComment(TaskComment taskComment);

    TaskComment findById(long taskCommentId);
    
    void updateTaskComment(long taskCommentId, TaskComment taskComment);

    void deleteTaskComment(long taskCommentId);

    List<TaskComment> getCommentsOnTask(long taskId);
}
