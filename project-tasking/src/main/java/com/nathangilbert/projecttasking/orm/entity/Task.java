package com.nathangilbert.projecttasking.orm.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.nathangilbert.projecttasking.orm.enums.TaskPriority;
import com.nathangilbert.projecttasking.orm.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @Column(name = "sprint_id")
    private Sprint sprint;

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

    @Column(name = "severity_value")
    private Integer severityValue;

    @Column(name = "is_bug")
    private Boolean isBug;

    @Column(name = "status", length = 20, nullable = false)
    private TaskStatus status;

    @Column(name = "priority")
    private TaskPriority priority;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    public Task() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Task(Long projectId, String taskName, String description,
            LocalDateTime dueDate, Integer effortValue, Integer riskValue, TaskPriority priority, Boolean isBug) {
        this.projectId = projectId;
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.effortValue = effortValue > 0 ? effortValue : -1;
        this.riskValue = riskValue > 0 ? riskValue : -1;
        this.priority = priority;
        this.isBug = isBug;
        this.status = TaskStatus.OPEN;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Task(Long projectId, Sprint sprint, String taskName, String description, 
            LocalDateTime dueDate, Integer effortValue, Integer riskValue, TaskPriority priority, Boolean isBug) {
        this.projectId = projectId;
        this.sprint = sprint;
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.effortValue = effortValue > 0 ? effortValue : -1;
        this.riskValue = riskValue > 0 ? riskValue : -1;
        this.priority = priority;
        this.isBug = isBug;
        this.status = TaskStatus.OPEN;
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

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
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

    public Boolean getIsBug() {
        return isBug;
    }

    public void setIsBug(Boolean isBug) {
        this.isBug = isBug;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}


