package com.nathangilbert.projecttasking.orm.entity;

import java.sql.Timestamp;

import com.nathangilbert.projecttasking.orm.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_status_histories")
public class TaskStatusHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_status_history_id")
    private Long taskStausHistoryId;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "status_change_from", length = 20, nullable = false)
    private TaskStatus statusChangeFrom;

    @Column(name = "status_change_to", length = 20, nullable = false)
    private TaskStatus statusChangeTo;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;


    public TaskStatusHistory() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public TaskStatusHistory(Long taskId, TaskStatus statusChangeFrom, TaskStatus statusChangeTo) {
        this.taskId = taskId;
        this.statusChangeFrom = statusChangeFrom;
        this.statusChangeTo = statusChangeTo;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Long getTaskStausHistoryId() {
        return taskStausHistoryId;
    }

    public void setTaskStausHistoryId(Long taskStausHistoryId) {
        this.taskStausHistoryId = taskStausHistoryId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public TaskStatus getStatusChangeFrom() {
        return statusChangeFrom;
    }

    public void setStatusChangeFrom(TaskStatus statusChangeFrom) {
        this.statusChangeFrom = statusChangeFrom;
    }

    public TaskStatus getStatusChangeTo() {
        return statusChangeTo;
    }

    public void setStatusChangeTo(TaskStatus statusChangeTo) {
        this.statusChangeTo = statusChangeTo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
}
