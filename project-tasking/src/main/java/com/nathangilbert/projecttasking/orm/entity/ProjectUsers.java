package com.nathangilbert.projecttasking.orm.entity;

import com.nathangilbert.projecttasking.orm.entity.id.ProjectUserId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "project_users")
public class ProjectUsers {

    @EmbeddedId
    private ProjectUserId id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private long userId;

    @Column(name = "project_id", insertable = false, updatable = false)
    private long projectId;

    public ProjectUsers() {}

    public ProjectUsers(long userId, long projectId) {
        this.id = new ProjectUserId(userId, projectId);
        this.userId = userId;
        this.projectId = projectId;
    }

    public long getUserId() {
        return userId;
    }

    public long getProjectId() {
        return projectId;
    }

    @Override
    public String toString() {
        return "ProjectUser {" +
                "projectId='" + projectId + '\'' +
                ", userId='" + userId + '\'' +
                "}";
    }
}
