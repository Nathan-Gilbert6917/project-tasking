package com.nathangilbert.projecttasking.orm.enums;

public enum TaskStatus {
    OPEN("open"),
    IN_PROGRESS("inProgress"),
    READY_FOR_REVIEW("readyForReview"),
    IN_REVIEW("inReview"),
    BLOCKED("blocked"),
    COMPLETED("completed");
    
    private String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}