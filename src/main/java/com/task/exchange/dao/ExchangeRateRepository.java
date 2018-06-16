package com.task.exchange.dao;

import com.task.exchange.model.ExchangeRate;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {

    ExchangeRate findFirstBySourceCurrencyAndTargetCurrencyOrderBySaveTimeDesc(String sourceCurrency, String targetCurrency);

    List<ExchangeRate> findBySourceCurrencyAndTargetCurrencyAndSaveTimeBetween(String sourceCurrency, String targetCurrency, Date fromDate, Date toDate);
}