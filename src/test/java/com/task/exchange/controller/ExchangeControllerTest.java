package com.task.exchange.controller;

import com.task.exchange.model.ExchangeRateDTO;
import com.task.exchange.service.ExchangeRateService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeControllerTest {

    private static final String CURRENCY_EUR = "EUR";
    private static final String CURRENCY_USD = "USD";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    public void shouldReturnClientErrorWhenExchangeRateNotAvailable() throws Exception {

        //Given
        ExchangeRateDTO exchangeRate = null;

        when(exchangeRateService.retrieveCurrentExchangeRate(CURRENCY_EUR, CURRENCY_USD)).thenReturn(exchangeRate);

        //When and Then
        mockMvc.perform(get("/getExchangeRate?sourceCurrency=EUR&targetCurrency=USD").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldRetrieveExchangeRateWithoutAnyParameters() throws Exception {

        //Given
        ExchangeRateDTO exchangeRate = createExchangeRateDTO(BigDecimal.valueOf(1.75));

        when(exchangeRateService.retrieveCurrentExchangeRate(CURRENCY_EUR, CURRENCY_USD)).thenReturn(exchangeRate);

        //When and Then
        mockMvc.perform(get("/getExchangeRate").contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk()).andExpect(jsonPath("$.sourceCurrency", equalTo(CURRENCY_EUR))).andExpect(jsonPath("$.targetCurrency", equalTo(CURRENCY_USD))).andExpect(jsonPath("$.targetCurrencyExchangeRate", equalTo(1.75)));
    }

    @Test
    public void shouldRetrieveExchangeRateWithAnyParameters() throws Exception {

        //Given
        ExchangeRateDTO exchangeRate = createExchangeRateDTO(BigDecimal.valueOf(1.75));

        when(exchangeRateService.retrieveCurrentExchangeRate(CURRENCY_EUR, CURRENCY_USD)).thenReturn(exchangeRate);

        //When and Then
        mockMvc.perform(get("/getExchangeRate?sourceCurrency=EUR&targetCurrency=USD").contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk()).andExpect(jsonPath("$.sourceCurrency", equalTo(CURRENCY_EUR))).andExpect(jsonPath("$.targetCurrency", equalTo(CURRENCY_USD))).andExpect(jsonPath("$.targetCurrencyExchangeRate", equalTo(1.75)));
    }


    @Test
    public void shouldRetrieveExchangeRateHistory() throws Exception {

        //Given
        ExchangeRateDTO firstExchangeRate = createExchangeRateDTO(BigDecimal.valueOf(1.75));
        ExchangeRateDTO secondExchangeRate = createExchangeRateDTO(BigDecimal.valueOf(1.64));

        List<ExchangeRateDTO> exchangeRates = new ArrayList<>();
        exchangeRates.add(firstExchangeRate);
        exchangeRates.add(secondExchangeRate);

        String startDate = "2018-06-15 12:15:32";
        String endDate = "2018-06-15 15:15:23";
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fromDate = LocalDateTime.parse(startDate, df);
        LocalDateTime toDate = LocalDateTime.parse(endDate, df);

        Mockito.when(exchangeRateService.retrieveExchangeRatesBetweenSelectedDates(CURRENCY_EUR, CURRENCY_USD, fromDate, toDate)).thenReturn(exchangeRates);


        //When and Then
        mockMvc.perform(get("/getExchangeRateHistory?from=" + startDate + "&to=" + endDate).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect((jsonPath("$", Matchers.hasSize(exchangeRates.size())))).andExpect(jsonPath("$[0].sourceCurrency", is(CURRENCY_EUR))).andExpect(jsonPath("$[0].targetCurrency", is(CURRENCY_USD))).andExpect(jsonPath("$[0].targetCurrencyExchangeRate", equalTo(1.75))).andExpect(jsonPath("$[1].sourceCurrency", is(CURRENCY_EUR))).andExpect(jsonPath("$[1].targetCurrency", is(CURRENCY_USD))).andExpect(jsonPath("$[1].targetCurrencyExchangeRate", equalTo(1.64)));

    }

    @Test
    public void shouldThrowExceptionWhenEndDateNotGreaterThanStartDate() throws Exception {

        //Given
        ExchangeRateDTO firstExchangeRate = createExchangeRateDTO(BigDecimal.valueOf(1.75));
        ExchangeRateDTO secondExchangeRate = createExchangeRateDTO(BigDecimal.valueOf(1.64));

        List<ExchangeRateDTO> exchangeRates = new ArrayList<>();
        exchangeRates.add(firstExchangeRate);
        exchangeRates.add(secondExchangeRate);

        String startDate = "2018-06-15 15:15:23";
        String endDate = "2018-06-15 12:15:32";

        //When and Then
        mockMvc.perform(get("/getExchangeRateHistory?from=" + startDate + "&to=" + endDate).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError());

    }

    @Test
    public void shouldThrowExceptionWhenDateFormatsAreNotCorrect() throws Exception {

        //Given
        BigDecimal exchangeRateValue = BigDecimal.valueOf(1.75);
        ExchangeRateDTO firstExchangeRate = createExchangeRateDTO(exchangeRateValue);

        ExchangeRateDTO secondExchangeRate = createExchangeRateDTO(BigDecimal.valueOf(1.64));

        List<ExchangeRateDTO> exchangeRates = new ArrayList<>();
        exchangeRates.add(firstExchangeRate);
        exchangeRates.add(secondExchangeRate);

        String startDate = "2018-06-15";
        String endDate = "12:15:32";

        //When and Then
        mockMvc.perform(get("/getExchangeRateHistory?from=" + startDate + "&to=" + endDate).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError());

    }


    private ExchangeRateDTO createExchangeRateDTO(BigDecimal exchangeRateValue) {
        ExchangeRateDTO exchangeRate = new ExchangeRateDTO();
        exchangeRate.setTargetCurrencyExchangeRate(exchangeRateValue);
        exchangeRate.setSourceCurrency(CURRENCY_EUR);
        exchangeRate.setTargetCurrency(CURRENCY_USD);
        return exchangeRate;
    }
}