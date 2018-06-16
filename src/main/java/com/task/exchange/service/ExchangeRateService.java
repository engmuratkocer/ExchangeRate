package com.task.exchange.service;

import com.task.exchange.model.ExchangeRateDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ExchangeRateService {

    ExchangeRateDTO retrieveCurrentExchangeRate(String sourceCurrency, String targetCurrency);

    List<ExchangeRateDTO> retrieveExchangeRatesBetweenSelectedDates(String sourceCurrency, String targetCurrency, LocalDateTime fromDate, LocalDateTime toDate);

}