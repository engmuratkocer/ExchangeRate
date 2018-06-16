package com.task.exchange.service;

import com.task.exchange.dao.ExchangeRateRepository;
import com.task.exchange.exception.ExchangeRateException;
import com.task.exchange.model.ExchangeRate;
import com.task.exchange.model.ExchangeRateDTO;
import com.task.exchange.util.ExchangeRateDataTypeConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeRateServiceImplTest {

    public static final int SCALE = 5;
    public static final String SOURCE_CURRENCY = "EUR";
    public static final String TARGET_CURRENCY = "USD";

    @Autowired
    private ExchangeRateService service;

    @MockBean
    private ExchangeRateRepository exchangeRateRepository;

    @Test(expected = ExchangeRateException.class)
    public void shouldThrowExceptionWhenInputCurrenciesAreNull() {

        //Given

        //When
        service.retrieveCurrentExchangeRate(null, null);

        //Then
        //Exception thrown
    }

    @Test(expected = ExchangeRateException.class)
    public void shouldThrowExceptionWhenInputCurrenciesAreEmpty() {

        //Given

        //When
        service.retrieveCurrentExchangeRate("", "");

        //Then
        //Exception thrown
    }

    @Test
    public void shouldReturnNullWhenNoDataExists() {

        //Given
        when(exchangeRateRepository.findFirstBySourceCurrencyAndTargetCurrencyOrderBySaveTimeDesc(SOURCE_CURRENCY, TARGET_CURRENCY)).thenReturn(null);

        //When
        ExchangeRateDTO rateDTO = service.retrieveCurrentExchangeRate(SOURCE_CURRENCY, TARGET_CURRENCY);

        //Then
        assertNull(rateDTO);


    }

    @Test
    public void shouldRetrieveCurrentExchangeRate() {

        //Given
        ExchangeRate exchangeRate = createExchangeRate();

        when(exchangeRateRepository.findFirstBySourceCurrencyAndTargetCurrencyOrderBySaveTimeDesc(SOURCE_CURRENCY, TARGET_CURRENCY)).thenReturn(exchangeRate);

        //When
        ExchangeRateDTO rateDTO = service.retrieveCurrentExchangeRate(SOURCE_CURRENCY, TARGET_CURRENCY);

        //Then
        ExchangeRateDTO expectedResult = ExchangeRateDataTypeConverter.convertToDto(exchangeRate);
        assertForGivenIndex(expectedResult, rateDTO);

    }


    @Test(expected = ExchangeRateException.class)
    public void shouldThrowExceptionWhenInputCurrenciesAreNullForList() {

        //Given
        LocalDateTime startDate = LocalDateTime.of(2018, 06, 15, 10, 15, 23);
        LocalDateTime endDate = LocalDateTime.of(2018, 06, 18, 10, 15, 23);

        //When
        service.retrieveExchangeRatesBetweenSelectedDates(null, null, startDate, endDate);

        //Then
        //Exception thrown
    }


    @Test(expected = ExchangeRateException.class)
    public void shouldThrowExceptionWhenInputCurrenciesAreEmptyForList() {

        //Given
        LocalDateTime startDate = LocalDateTime.of(2018, 06, 15, 10, 15, 23);
        LocalDateTime endDate = LocalDateTime.of(2018, 06, 18, 10, 15, 23);

        //When
        service.retrieveExchangeRatesBetweenSelectedDates("", "", startDate, endDate);

        //Then
        //Exception thrown
    }


    @Test(expected = ExchangeRateException.class)
    public void shouldThrowExceptionWhenInputDatesAreNullForList() {

        //Given
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        //When
        service.retrieveExchangeRatesBetweenSelectedDates("", "", startDate, endDate);

        //Then
        //Exception thrown
    }

    @Test(expected = ExchangeRateException.class)
    public void shouldThrowExceptionWhenStartDateGreaterThanEndDate() {

        //Given
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        //When
        service.retrieveExchangeRatesBetweenSelectedDates("", "", startDate, endDate);

        //Then
        //Exception thrown
    }

    @Test
    public void shouldReturnExchangeRates() {
        //Given
        ExchangeRate exchangeRate = createExchangeRate();
        ExchangeRate exchangeRate2 = createExchangeRate();
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        exchangeRates.add(exchangeRate);
        exchangeRates.add(exchangeRate2);

        LocalDateTime startDate = LocalDateTime.of(2018, 06, 15, 10, 15, 23);
        LocalDateTime endDate = LocalDateTime.of(2018, 06, 18, 10, 15, 23);

        Date fromDate = java.util.Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = java.util.Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

        when(exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyAndSaveTimeBetween(SOURCE_CURRENCY, TARGET_CURRENCY, fromDate, toDate)).thenReturn(exchangeRates);

        //When
        List<ExchangeRateDTO> rateDTOList = service.retrieveExchangeRatesBetweenSelectedDates(SOURCE_CURRENCY, TARGET_CURRENCY, startDate, endDate);


        //Then
        List<ExchangeRateDTO> expectedResults = exchangeRates.stream().map(ExchangeRateDataTypeConverter::convertToDto).collect(Collectors.toList());
        assertEquals(expectedResults.size(), rateDTOList.size());
        assertForGivenIndex(expectedResults.get(0), rateDTOList.get(0));
        assertForGivenIndex(expectedResults.get(1), rateDTOList.get(1));

    }

    private void assertForGivenIndex(ExchangeRateDTO exchangeRateDTO, ExchangeRateDTO exchangeRateDTO2) {
        assertEquals(exchangeRateDTO.getSourceCurrency(), exchangeRateDTO2.getSourceCurrency());
        assertEquals(exchangeRateDTO.getTargetCurrency(), exchangeRateDTO2.getTargetCurrency());
        assertEquals(exchangeRateDTO.getTargetCurrencyExchangeRate(), exchangeRateDTO2.getTargetCurrencyExchangeRate());
        assertEquals(exchangeRateDTO.getSaveTime(), exchangeRateDTO2.getSaveTime());
    }

    private ExchangeRate createExchangeRate() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId((new Random()).nextLong());
        exchangeRate.setSaveTime(new Date());
        exchangeRate.setSourceCurrency(SOURCE_CURRENCY);
        exchangeRate.setTargetCurrency(TARGET_CURRENCY);
        BigDecimal minRate = BigDecimal.valueOf(100000, SCALE);
        BigDecimal maxRate = BigDecimal.valueOf(200000, SCALE);
        BigDecimal randomExchangeRate = minRate.add(new BigDecimal(Math.random()).multiply(maxRate.subtract(minRate)));
        exchangeRate.setTargetCurrencyExchangeRate(randomExchangeRate);
        return exchangeRate;
    }

}