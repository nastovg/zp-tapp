package com.gn.tapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gn.tapp.domain.rest.Rates;
import com.gn.tapp.security.AuthoritiesConstants;
import com.gn.tapp.service.CurrencyService;
import com.gn.tapp.web.rest.dto.CurrencyDTO;
import com.gn.tapp.web.rest.dto.RatesDTO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/currency", produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrencyResource {

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(value = "/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public List<CurrencyDTO> getCurrencies() {
        Map<String, String> currencies = currencyService.getCurrencies();
        if (currencies == null) {
            return null;
        }
        List<CurrencyDTO> currencyDTOList = Lists.newArrayList();
        for (String key : currencies.keySet()) {
            currencyDTOList.add(new CurrencyDTO(key, currencies.get(key)));
        }
        Collections.sort(currencyDTOList);
        return currencyDTOList;
    }

    @RequestMapping(value = "/latest",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public RatesDTO getLatestRates() {
        Rates rates = currencyService.getLatestRates();
        if (rates == null) {
            return null;
        }
        return new RatesDTO(rates.getTimestemp(), rates.getBase(), rates.getRates());
    }

    @RequestMapping(value = "/historical",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public RatesDTO getLatestRates(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") Date date) {
        Rates rates = currencyService.getHistoricalRates(date);
        if (rates == null) {
            return null;
        }
        return new RatesDTO(rates.getTimestemp(), rates.getBase(), rates.getRates());
    }
}
