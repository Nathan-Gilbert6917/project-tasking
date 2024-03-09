package com.nathangilbert.projecttasking.rest.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
