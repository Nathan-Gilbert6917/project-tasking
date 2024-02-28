package com.nathangilbert.projecttasking.orm.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private long projectId;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "description")
    private String description;

    @Column(name = "aging_task_days")
    private int agingTaskDays;

    @Column(name = "owner_id", nullable = false)
    private long ownerId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public Project(){
        this.agingTaskDays = -1;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Project(String projectName, long ownerId, String description, int agingTaskDays) {
        this.projectName = projectName;
        this.ownerId = ownerId;
        this.agingTaskDays = agingTaskDays;
        this.description = description;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Project(String projectName, User ownerUser, String description, int agingTaskDays) {
        this.projectName = projectName;
        this.ownerId = ownerUser.getUserId();
        this.agingTaskDays = agingTaskDays;
        this.description = description;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public long getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAgingTaskDays() {
        return agingTaskDays;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAgingTaskDays(Integer agingTaskDays) {
        this.agingTaskDays = agingTaskDays;
    }

    @Override
    public String toString() {
        return "Project {" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", description='" + description + '\'' +
                "}";
    }

}
