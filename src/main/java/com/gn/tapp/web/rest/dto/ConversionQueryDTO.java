package com.gn.tapp.web.rest.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the ConversionQuery entity.
 */
public class ConversionQueryDTO implements Serializable {

    private static final long serialVersionUID = -2354454660193393830L;

    @NotNull
    @Min(value = 1)
    @Max(value = 1000000000)
    private Integer amount;

    @NotNull
    @Size(min = 3, max = 3)
    private String fromCurrency;

    @NotNull
    @Size(min = 3, max = 3)
    private String toCurrency;

    @NotNull
    private ZonedDateTime conversionDate;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public ZonedDateTime getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(ZonedDateTime conversionDate) {
        this.conversionDate = conversionDate;
    }

    @Override
    public String toString() {
        return "ConversionQueryDTO{" +
                "amount='" + amount + "'" +
                ", fromCurrency='" + fromCurrency + "'" +
                ", toCurrency='" + toCurrency + "'" +
                ", conversionDate='" + conversionDate + "'" +
                '}';
    }
}
