package com.task.exchange.model;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class ExchangeRateDTO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private String sourceCurrency;

    private String targetCurrency;

    private BigDecimal targetCurrencyExchangeRate;

    private String saveTime;

    public void setSaveDate(Date date) {
        dateFormat.setTimeZone(TimeZone.getDefault());
        this.saveTime = dateFormat.format(date);
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public BigDecimal getTargetCurrencyExchangeRate() {
        return targetCurrencyExchangeRate;
    }

    public void setTargetCurrencyExchangeRate(BigDecimal targetCurrencyExchangeRate) {
        this.targetCurrencyExchangeRate = targetCurrencyExchangeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ExchangeRateDTO))
            return false;
        ExchangeRateDTO that = (ExchangeRateDTO) o;
        return Objects.equals(getSourceCurrency(), that.getSourceCurrency()) && Objects.equals(getTargetCurrency(), that.getTargetCurrency()) && Objects.equals(getTargetCurrencyExchangeRate(), that.getTargetCurrencyExchangeRate()) && Objects.equals(getSaveTime(), that.getSaveTime());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSourceCurrency(), getTargetCurrency(), getTargetCurrencyExchangeRate(), getSaveTime());
    }

    @Override
    public String toString() {
        return "ExchangeRateDTO{" + "sourceCurrency='" + sourceCurrency + '\'' + ", targetCurrency='" + targetCurrency + '\'' + ", targetCurrencyExchangeRate=" + targetCurrencyExchangeRate + ", saveTime='" + saveTime + '\'' + '}';
    }
}
