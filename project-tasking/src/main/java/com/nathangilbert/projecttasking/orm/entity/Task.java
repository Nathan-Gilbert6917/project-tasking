package com.nathangilbert.projecttasking.orm.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "effort_value")
    private Integer effortValue;

    @Column(name = "risk_value")
    private Integer riskValue;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "open";

    @Column(name = "last_date_status_modified", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp lastDateStatusModified;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    public Task() {
        this.lastDateStatusModified = new Timestamp(System.currentTimeMillis());
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Task(Long projectId, String taskName, String description, 
            LocalDateTime dueDate, Integer effortValue, Integer riskValue) {
        this.projectId = projectId;
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.effortValue = effortValue > 0 ? effortValue : -1;
        this.riskValue = riskValue > 0 ? riskValue : -1;
        this.lastDateStatusModified = new Timestamp(System.currentTimeMillis());
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters and setters

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getEffortValue() {
        return effortValue;
    }

    public void setEffortValue(Integer effortValue) {
        this.effortValue = effortValue;
    }

    public Integer getRiskValue() {
        return riskValue;
    }

    public void setRiskValue(Integer riskValue) {
        this.riskValue = riskValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getLastDateStatusModified() {
        return lastDateStatusModified;
    }

    public void setLastDateStatusModified(Timestamp lastDateStatusModified) {
        this.lastDateStatusModified = lastDateStatusModified;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}


