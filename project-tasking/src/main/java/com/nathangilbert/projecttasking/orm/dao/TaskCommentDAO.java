package com.nathangilbert.projecttasking.orm.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nathangilbert.projecttasking.orm.dao.interfaces.ITaskCommentDAO;
import com.nathangilbert.projecttasking.orm.entity.TaskComment;
import com.nathangilbert.projecttasking.rest.exceptions.TaskCommentNotFoundException;
import com.nathangilbert.projecttasking.rest.exceptions.TaskNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class TaskCommentDAO implements ITaskCommentDAO {


    private EntityManager entityManager;

    private final String TASK_COMMENT_NOT_FOUND_MESSAGE = "Task comment not found for ID:";

    @Autowired
    public TaskCommentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void createTaskComment(TaskComment taskComment) {
        entityManager.persist(taskComment);
    }

    @Override
    public TaskComment findById(long taskCommentId) {
        TaskComment taskComment = entityManager.find(TaskComment.class, taskCommentId);
        if (taskComment == null) {
            throw new TaskNotFoundException(TASK_COMMENT_NOT_FOUND_MESSAGE + taskCommentId);
        }
        return taskComment;
    }

    @Override
    public void updateTaskComment(long taskCommentId, TaskComment updatedTaskComment) {
        TaskComment taskComment = entityManager.find(TaskComment.class, taskCommentId);
        if (taskComment == null) {
            throw new TaskNotFoundException(TASK_COMMENT_NOT_FOUND_MESSAGE + taskCommentId);
        }

        String updatedComment = updatedTaskComment.getComment();

        if (updatedComment != null) {
            taskComment.setComment(updatedComment);
        }

        taskComment.setDateModified(new Timestamp(System.currentTimeMillis()));
        entityManager.merge(taskComment);
    }

    @Override
    public void deleteTaskComment(long taskCommentId) {
        TaskComment taskComment = entityManager.find(TaskComment.class, taskCommentId);
        if (taskComment == null) {
            throw new TaskCommentNotFoundException(TASK_COMMENT_NOT_FOUND_MESSAGE + taskCommentId);
        }
        entityManager.remove(taskComment);
    }

    @Override
    public List<TaskComment> getCommentsOnTask(long taskId) {
        TypedQuery<TaskComment> commentsOnTaskQuery = entityManager.createQuery("FROM TaskComment WHERE taskId=:taskId", TaskComment.class);
        commentsOnTaskQuery.setParameter("taskId", taskId);

        List<TaskComment> commentsOnTask = commentsOnTaskQuery.getResultList();

        if (commentsOnTask == null || commentsOnTask.size() == 0) {
            throw new TaskCommentNotFoundException("0 Tasks were found for taskId:" + taskId);
        }

        return commentsOnTask;
    }
    
}
