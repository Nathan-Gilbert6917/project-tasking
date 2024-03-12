package com.nathangilbert.projecttasking.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nathangilbert.projecttasking.orm.dao.TaskCommentDAO;
import com.nathangilbert.projecttasking.orm.entity.TaskComment;
import com.nathangilbert.projecttasking.services.interfaces.ITaskCommentService;

import jakarta.transaction.Transactional;

@Service
public class TaskCommentService implements ITaskCommentService {

    private TaskCommentDAO taskCommentDAO;

    public TaskCommentService(TaskCommentDAO taskCommentDAO) {
        this.taskCommentDAO = taskCommentDAO;
    }

    @Override
    @Transactional
    public void createTaskComment(TaskComment taskComment) {
       this.taskCommentDAO.createTaskComment(taskComment);
    }

    @Override
    public TaskComment findById(long taskCommentId) {
        return this.taskCommentDAO.findById(taskCommentId);
    }

    @Override
    @Transactional
    public void updateTaskComment(long taskCommentId, TaskComment updatedTaskComment) {
       this.taskCommentDAO.updateTaskComment(taskCommentId, updatedTaskComment);
    }

    @Override
    @Transactional
    public void deleteTaskComment(long taskCommentId) {
        this.taskCommentDAO.deleteTaskComment(taskCommentId);
    }

    @Override
    public List<TaskComment> getCommentsOnTask(long taskId) {
       return this.taskCommentDAO.getCommentsOnTask(taskId);
    }
    
}
