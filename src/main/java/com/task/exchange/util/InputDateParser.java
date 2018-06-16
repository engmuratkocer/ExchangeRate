package com.task.exchange.util;

import com.task.exchange.exception.ExchangeRateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.task.exchange.exception.ExchangeRateExceptionCodes.WRONG_DATE_FORMAT;

public class InputDateParser {

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parse(String dateParam) {

        try {
            return LocalDateTime.parse(dateParam, df);
        } catch (DateTimeParseException e) {
            throw new ExchangeRateException(WRONG_DATE_FORMAT);
        }
    }
}
