package com.task.exchange.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class DummyExchangeRateImpl implements RetrieveExchangeRate {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyExchangeRateImpl.class);

    private static final int SCALE = 5;

    private final String sourceCurrency;

    private final String targetCurrency;

    public DummyExchangeRateImpl(String sourceCurrency, String targetCurrency) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal retrieveExchangeRate() {

        BigDecimal minRate = BigDecimal.valueOf(100000, SCALE);
        BigDecimal maxRate = BigDecimal.valueOf(200000, SCALE);

        BigDecimal randomExchangeRate = minRate.add(new BigDecimal(Math.random()).multiply(maxRate.subtract(minRate)));

        LOGGER.info(String.format("DummyService exchange rate value 1 %s = %.5f %s", sourceCurrency, randomExchangeRate, targetCurrency));

        return randomExchangeRate.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }
}
