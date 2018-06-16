package com.task.exchange.service;

public interface ExchangeRateUpdater extends Runnable {

    void setRetrieveExchangeRate(RetrieveExchangeRate retrieveExchangeRate);

    void updateExchangeRate();

}
