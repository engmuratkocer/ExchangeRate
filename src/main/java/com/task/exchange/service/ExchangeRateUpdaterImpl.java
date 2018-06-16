package com.task.exchange.service;

import com.task.exchange.dao.ExchangeRateRepository;
import com.task.exchange.model.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExchangeRateUpdaterImpl implements ExchangeRateUpdater {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    RetrieveExchangeRate retrieveExchangeRate;

    @Override
    public void run() {

        updateExchangeRate();

    }

    public void updateExchangeRate() {
        String sourceCurrency = retrieveExchangeRate.getSourceCurrency();
        String targetCurrency = retrieveExchangeRate.getTargetCurrency();

        LOGGER.info(String.format("Requesting exchange rate from %s  to %s currency.", sourceCurrency, targetCurrency));

        BigDecimal exchangeRateValue = retrieveExchangeRate.retrieveExchangeRate();

        ExchangeRate exchangeRateEntity = new ExchangeRate(sourceCurrency, targetCurrency, exchangeRateValue, new Date());
        exchangeRateRepository.save(exchangeRateEntity);

        LOGGER.info(String.format("ExchangeRate saved 1 %s = %.5f %s", sourceCurrency, exchangeRateValue, targetCurrency));
    }

    public void setRetrieveExchangeRate(RetrieveExchangeRate retrieveExchangeRate) {
        this.retrieveExchangeRate = retrieveExchangeRate;
    }
}