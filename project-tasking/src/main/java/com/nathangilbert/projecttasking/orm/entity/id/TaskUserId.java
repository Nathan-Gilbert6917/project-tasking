package com.nathangilbert.projecttasking.orm.entity.id;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TaskUserId implements Serializable {

    @Column(name = "user_id")
    private long userId;

    @Column(name = "task_id")
    private long taskId;

    public TaskUserId() {
    }

    public TaskUserId(long userId, long taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }

    public long getUserId() {
        return userId;
    }

    public long getTaskId() {
        return taskId;
    }
}
