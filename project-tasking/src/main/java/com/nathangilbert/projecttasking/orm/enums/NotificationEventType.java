package com.nathangilbert.projecttasking.orm.enums;

public enum NotificationEventType {
    TASK_COMMENT_UPDATE("onTaskCommentUpdate"),
    TASK_COMMENT_NEW("onTaskCommentNew"),
    TASK_STATUS_UPDATE("onTaskStatusUpdate"),
    TASK_PASTP_DUE("onTaskPastDue"),
    TASK_UPDATE("onTaskUpdate"),
    TASK_ASSIGNED("onTaskAssigned"),
    PROJECT_INVITE("onProjectInvite"),
    PROJECT_JOIN("onProjectJoin");

    private String eventName;

    NotificationEventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}