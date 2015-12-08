package com.gn.tapp.domain.rest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Rates {

    private Long timestemp;

    private String base;

    private Map<String, BigDecimal> rates = new HashMap<>();

    public Rates() {
    }

    public Long getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(final Long timestemp) {
        this.timestemp = timestemp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(final String base) {
        this.base = base;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(final Map<String, BigDecimal> rates) {
        this.rates = rates;
    }
}
