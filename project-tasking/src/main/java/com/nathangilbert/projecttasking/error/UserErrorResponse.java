package com.nathangilbert.projecttasking.error;

import java.sql.Timestamp;

public class UserErrorResponse {
    
    private int status;
    
    private String message;

    private Timestamp timestamp;

    public UserErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

}
