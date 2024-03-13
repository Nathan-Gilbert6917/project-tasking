package com.nathangilbert.projecttasking.orm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "project_current_sprint")
public class ProjectCurrentSprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @OneToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    public ProjectCurrentSprint() {}

    public ProjectCurrentSprint(Long projectId) {
        this.projectId = projectId;
    }

    public ProjectCurrentSprint(Long projectId, Sprint sprint) {
        this.projectId = projectId;
        this.sprint = sprint;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
