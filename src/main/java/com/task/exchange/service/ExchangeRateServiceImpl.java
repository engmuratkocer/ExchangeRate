package com.task.exchange.service;

import com.task.exchange.dao.ExchangeRateRepository;
import com.task.exchange.exception.ExchangeRateException;
import com.task.exchange.model.ExchangeRate;
import com.task.exchange.model.ExchangeRateDTO;
import com.task.exchange.util.ExchangeRateDataTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static com.task.exchange.exception.ExchangeRateExceptionCodes.*;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateDTO retrieveCurrentExchangeRate(String sourceCurrency, String targetCurrency) throws ExchangeRateException {

        validateCurrencies(sourceCurrency, targetCurrency);

        ExchangeRate exchangeRate = exchangeRateRepository.findFirstBySourceCurrencyAndTargetCurrencyOrderBySaveTimeDesc(sourceCurrency, targetCurrency);
        return ExchangeRateDataTypeConverter.convertToDto(exchangeRate);
    }

    public List<ExchangeRateDTO> retrieveExchangeRatesBetweenSelectedDates(String sourceCurrency, String targetCurrency, LocalDateTime fromDate, LocalDateTime toDate) throws ExchangeRateException {

        validateCurrencies(sourceCurrency, targetCurrency);

        validateInputDates(fromDate, toDate);

        List<ExchangeRate> exchangeRates = exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyAndSaveTimeBetween(sourceCurrency, targetCurrency, java.util.Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant()), java.util.Date.from(toDate.atZone(ZoneId.systemDefault()).toInstant()));

        return exchangeRates.stream().map(ExchangeRateDataTypeConverter::convertToDto).collect(Collectors.toList());
    }

    private void validateCurrencies(String sourceCurrency, String targetCurrency) {
        if (StringUtils.isEmpty(sourceCurrency) || StringUtils.isEmpty(targetCurrency)) {
            throw new ExchangeRateException(INPUT_CURRENCIES_EMPTY);
        }
    }

    private void validateInputDates(LocalDateTime fromDate, LocalDateTime toDate) {
        if (fromDate == null || toDate == null) {
            throw new ExchangeRateException(DATES_CANT_BE_NULL);
        }

        if (toDate.compareTo(fromDate) < 1) {
            throw new ExchangeRateException(END_DATE_NOT_GREATER_THAN_STARTDATE);
        }
    }

}
