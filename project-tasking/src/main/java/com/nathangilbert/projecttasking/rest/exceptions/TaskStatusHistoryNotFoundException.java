package com.nathangilbert.projecttasking.rest.exceptions;

public class TaskStatusHistoryNotFoundException extends RuntimeException {
    public TaskStatusHistoryNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
