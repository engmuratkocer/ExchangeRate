package com.task.exchange.service;

import java.math.BigDecimal;

public interface RetrieveExchangeRate {

    BigDecimal retrieveExchangeRate();

    String getSourceCurrency();

    String getTargetCurrency();

}
