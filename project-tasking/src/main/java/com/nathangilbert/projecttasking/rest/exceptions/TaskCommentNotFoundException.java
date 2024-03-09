package com.nathangilbert.projecttasking.rest.exceptions;

public class TaskCommentNotFoundException extends RuntimeException {
    public TaskCommentNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
