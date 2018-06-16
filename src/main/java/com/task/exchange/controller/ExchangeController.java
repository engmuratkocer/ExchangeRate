package com.task.exchange.controller;

import com.task.exchange.exception.RateNotFoundException;
import com.task.exchange.exception.ExchangeRateException;
import com.task.exchange.model.ExchangeRateDTO;
import com.task.exchange.service.ExchangeRateService;
import com.task.exchange.util.InputDateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.task.exchange.exception.ExchangeRateExceptionCodes.END_DATE_NOT_GREATER_THAN_STARTDATE;

@RestController
public class ExchangeController {


    @Autowired
    private ExchangeRateService exchangeRateService;

    @RequestMapping("/getExchangeRate")
    public ResponseEntity getExchangeRate(@RequestParam(value = "sourceCurrency", defaultValue = "EUR") String sourceCurrency, @RequestParam(value = "targetCurrency", defaultValue = "USD") String targetCurrency) {

        ExchangeRateDTO exchangeRateDTO = exchangeRateService.retrieveCurrentExchangeRate(sourceCurrency, targetCurrency);

        if (exchangeRateDTO == null) {
            throw new RateNotFoundException(" Exchange Rate for " + sourceCurrency + " not found for " + targetCurrency);
        }

        return new ResponseEntity<>(exchangeRateDTO, HttpStatus.OK);
    }

    @RequestMapping("/getExchangeRateHistory")
    public List<ExchangeRateDTO> getExchangeRateHistory(@RequestParam(value = "sourceCurrency", defaultValue = "EUR") String sourceCurrency, @RequestParam(value = "targetCurrency", defaultValue = "USD") String targetCurrency, @RequestParam("from") String from, @RequestParam("to") String to) {

        LocalDateTime fromDate = InputDateParser.parse(from);
        LocalDateTime toDate = InputDateParser.parse(to);

        if (toDate.compareTo(fromDate) < 1) {
            throw new ExchangeRateException(END_DATE_NOT_GREATER_THAN_STARTDATE);
        }

        List<ExchangeRateDTO> exchangeRateDTOList = exchangeRateService.retrieveExchangeRatesBetweenSelectedDates(sourceCurrency, targetCurrency, fromDate, toDate);


        if (exchangeRateDTOList.isEmpty()) {
            throw new RateNotFoundException(" Exchange Rate History for " + sourceCurrency + " not found for " + targetCurrency + " between start date : " + fromDate + " and endDate : " + toDate + " dates.");
        }

        return exchangeRateDTOList;
    }

}
