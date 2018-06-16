package com.task.exchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;
import java.util.ResourceBundle;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExchangeRateException extends RuntimeException {

    private static final Locale en_US = new Locale("en", "US");
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("ExchangeRateExceptionCodes", en_US);

    private final String errorCode;

    public ExchangeRateException(String errorCode) {
        super(errorCode + " : " + resourceBundle.getString(errorCode));
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}