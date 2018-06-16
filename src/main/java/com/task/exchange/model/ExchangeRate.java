package com.task.exchange.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
public class ExchangeRate {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String sourceCurrency;

    @NotNull
    private String targetCurrency;

    @NotNull
    private BigDecimal targetCurrencyExchangeRate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date saveTime;

    public ExchangeRate() {
    }

    public ExchangeRate(String sourceCurrency, String targetCurrency, BigDecimal targetCurrencyExchangeRate, Date saveTime) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.targetCurrencyExchangeRate = targetCurrencyExchangeRate;
        this.saveTime = saveTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
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
        if (o == null || getClass() != o.getClass())
            return false;
        ExchangeRate that = (ExchangeRate) o;
        return Objects.equals(id, that.id) && Objects.equals(sourceCurrency, that.sourceCurrency) && Objects.equals(targetCurrency, that.targetCurrency) && Objects.equals(targetCurrencyExchangeRate, that.targetCurrencyExchangeRate) && Objects.equals(saveTime, that.saveTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, sourceCurrency, targetCurrency, targetCurrencyExchangeRate, saveTime);
    }

    @Override
    public String toString() {
        return "ExchangeRate{" + "id=" + id + ", sourceCurrency='" + sourceCurrency + '\'' + ", targetCurrency='" + targetCurrency + '\'' + ", targetCurrencyExchangeRate=" + targetCurrencyExchangeRate + ", saveTime=" + saveTime + '}';
    }
}
