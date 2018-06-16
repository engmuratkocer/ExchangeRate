package com.task.exchange;

import com.task.exchange.service.ExchangeRateAsynchronousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExchangeApplication implements CommandLineRunner {

    @Autowired
    private ExchangeRateAsynchronousService exchangeRateAsynchronousService;

    public static void main(String[] args) {
        SpringApplication.run(ExchangeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        exchangeRateAsynchronousService.startExchangeRateProcessPeriodically();
    }

}
