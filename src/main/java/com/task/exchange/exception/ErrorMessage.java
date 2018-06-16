package com.task.exchange.exception;

import java.util.Date;

public class ErrorMessage {

    private final Date errorDate;
    private final String errorName;
    private final String errorDescription;

    public ErrorMessage(Date errorDate, String errorName, String errorDescription) {
        this.errorDate = errorDate;
        this.errorName = errorName;
        this.errorDescription = errorDescription;
    }
}
