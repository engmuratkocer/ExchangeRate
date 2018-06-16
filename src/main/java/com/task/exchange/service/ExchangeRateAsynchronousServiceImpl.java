package com.task.exchange.service;

import com.task.exchange.exception.ExchangeRateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;

import static com.task.exchange.exception.ExchangeRateExceptionCodes.UPDATE_CURRENCY_PROPERTY_NOT_SET;
import static com.task.exchange.exception.ExchangeRateExceptionCodes.UPDATE_PERIOD_ZERO;

@Service
public class ExchangeRateAsynchronousServiceImpl implements ExchangeRateAsynchronousService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateAsynchronousServiceImpl.class);
    private static final int DURATION_LENGTH = 1;

    @Value("${exchangeRate.readPeriod}")
    private int exchangeRateReadPeriod;

    @Value("${exchangeRate.updater.sourceCurrency}")
    private String sourceCurrency;

    @Value("${exchangeRate.updater.targetCurrency}")
    private String targetCurrency;

    @Autowired
    private ExchangeRateUpdaterImpl exchangeRateUpdater;

    private final TaskScheduler scheduler = new ConcurrentTaskScheduler();

    public void startExchangeRateProcessPeriodically() throws ExchangeRateException {

        LOGGER.info(" Exchange rate application started to save exchange rates with " + exchangeRateReadPeriod + " seconds period.");

        if (exchangeRateReadPeriod == 0) {
            throw new ExchangeRateException(UPDATE_PERIOD_ZERO);
        }

        if (StringUtils.isEmpty(sourceCurrency) || StringUtils.isEmpty(targetCurrency)) {
            throw new ExchangeRateException(UPDATE_CURRENCY_PROPERTY_NOT_SET);
        }

        exchangeRateUpdater.setRetrieveExchangeRate(new DummyExchangeRateImpl(sourceCurrency, targetCurrency));
        scheduler.scheduleAtFixedRate(exchangeRateUpdater, Duration.ofSeconds(DURATION_LENGTH));

    }

}
