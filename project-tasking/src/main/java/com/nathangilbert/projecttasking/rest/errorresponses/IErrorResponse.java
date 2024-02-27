package com.nathangilbert.projecttasking.rest.errorresponses;

import java.sql.Timestamp;

public interface IErrorResponse {

    public int getStatus();

    public String getMessage();

    public Timestamp getTimestamp();
}
