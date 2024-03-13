package com.nathangilbert.projecttasking.orm.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sprints")
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sprint_id")
    private Long sprintId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "sprint_name", nullable = false)
    private String sprintName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "retrospective_notes", columnDefinition = "TEXT")
    private String retrospectiveNotes;

    @Column(name = "duration_days")
    private Integer durationDays;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;


    public Sprint() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Sprint(Long projectId, String sprintName, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.projectId = projectId;
        this.sprintName = sprintName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.retrospectiveNotes = "";
        this.durationDays = (int) ChronoUnit.DAYS.between(startDate, endDate);
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters and setters

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getRetrospectiveNotes() {
        return retrospectiveNotes;
    }

    public void setRetrospectiveNotes(String retrospectiveNotes) {
        this.retrospectiveNotes = retrospectiveNotes;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
