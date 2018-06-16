package com.task.exchange.service;

import com.task.exchange.dao.ExchangeRateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeRateUpdaterTest {

    @Autowired
    private ExchangeRateUpdater exchangeRateUpdater;

    @MockBean
    private ExchangeRateRepository exchangeRateRepository;

    @Test
    public void shouldUpdateExchangeRate() {

        when(exchangeRateRepository.save(any())).thenReturn(any());

        exchangeRateUpdater.setRetrieveExchangeRate(new DummyExchangeRateImpl("EUR", "USD"));
        exchangeRateUpdater.updateExchangeRate();

    }
}
