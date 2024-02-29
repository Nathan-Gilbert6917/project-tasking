package com.nathangilbert.projecttasking.orm.id;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProjectUserId implements Serializable {

    @Column(name = "user_id")
    private long userId;

    @Column(name = "project_id")
    private long projectId;

    public ProjectUserId() {}

    public ProjectUserId(long userId, long projectId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public long getProjectId() {
        return projectId;
    }
}
