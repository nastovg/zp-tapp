package com.gn.tapp.web.rest.dto;

import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.Map;

public class RatesDTO {
    private Long timestemp;

    private String base;

    private Map<String, BigDecimal> exchangeMap = Maps.newHashMap();

    public RatesDTO() {
    }

    public RatesDTO(final Long timestemp, final String base, final Map<String, BigDecimal> exchangeMap) {
        this.timestemp = timestemp;
        this.base = base;
        this.exchangeMap = exchangeMap;
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

    public Map<String, BigDecimal> getExchangeMap() {
        return exchangeMap;
    }

    public void setExchangeMap(final Map<String, BigDecimal> exchangeMap) {
        this.exchangeMap = exchangeMap;
    }
}
