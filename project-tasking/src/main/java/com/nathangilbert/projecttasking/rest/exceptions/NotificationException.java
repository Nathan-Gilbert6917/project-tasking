package com.nathangilbert.projecttasking.rest.exceptions;

public class NotificationException extends RuntimeException {
    public NotificationException(String errorMessage) {
        super(errorMessage);
    }
}
