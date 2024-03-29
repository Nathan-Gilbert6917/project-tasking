package com.nathangilbert.projecttasking.rest.errorresponses;

import java.sql.Timestamp;

public class ErrorResponse implements IErrorResponse {
    
    private Integer status;
    
    private String message;

    private Timestamp timestamp;

    public ErrorResponse(Integer status, String message) {
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
