package com.nathangilbert.projecttasking.rest.exceptions;

public class SprintNotFoundException extends RuntimeException {
    public SprintNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
