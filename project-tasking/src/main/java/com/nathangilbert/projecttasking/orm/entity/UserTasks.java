package com.nathangilbert.projecttasking.orm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.nathangilbert.projecttasking.orm.entity.id.TaskUserId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;

@Entity
@Table(name = "user_tasks")
public class UserTasks {
    @EmbeddedId
    private TaskUserId id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private long userId;

    @Column(name = "task_id", insertable = false, updatable = false)
    private long taskId;

    public UserTasks() {}

    public UserTasks(long userId, long taskId) {
        this.id = new TaskUserId(userId, taskId);
        this.userId = userId;
        this.taskId = taskId;
    }

    public long getUserId() {
        return userId;
    }

    public long getTaskId() {
        return taskId;
    }

    @Override
    public String toString() {
        return "UserTasks {" +
                "userId='" + userId + '\'' +
                ", taskId='" + taskId + '\'' +
                "}";
    }
}
