package com.gn.tapp.service;

import com.gn.tapp.config.CurrencyProperties;
import com.gn.tapp.domain.rest.Rates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class CurrencyService {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final Logger log = LoggerFactory.getLogger(CurrencyService.class);
    @Autowired
    private CurrencyProperties currencyProperties;

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, String> getCurrencies() {
        final URI uri = getUrlWithKey(currencyProperties.getCurrenciesUrl());
        log.info("Calling uri: {}", uri);
        Map<String, String> currencies = restTemplate.getForObject(uri, Map.class);
        return currencies;
    }

    public Rates getLatestRates() {
        final URI uri = getUrlWithKey(currencyProperties.getLatestUrl());
        log.info("Calling uri: {}", uri);
        Rates latestRates = restTemplate.getForObject(uri, Rates.class);
        return latestRates;
    }

    public Rates getHistoricalRates(final Date date) {
        final URI baseUri = getUrlWithKey(currencyProperties.getHistoricalUrl());
        String dateUrlPart = SIMPLE_DATE_FORMAT.format(date).concat(".json");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(baseUri).path("/").path(dateUrlPart);
        final URI uri = builder.build().toUri();
        log.info("Calling uri: {}", uri);
        Rates historicalRates = restTemplate.getForObject(uri, Rates.class);
        return historicalRates;
    }

    private URI getUrlWithKey(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam(CurrencyProperties.KEY_PARAMETER, currencyProperties.getKey());
        return builder.build().toUri();
    }
}
