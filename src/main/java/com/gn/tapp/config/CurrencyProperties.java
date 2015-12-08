package com.gn.tapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "currency", ignoreUnknownFields = false)
public class CurrencyProperties {

    public static final String KEY_PARAMETER = "app_id";

    private String key;

    private String url;

    private Services services = new Services();

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(final Services services) {
        this.services = services;
    }

    public String getCurrenciesUrl() {
        return url + services.getCurrencies();
    }

    public String getLatestUrl() {
        return url + services.getLatest();
    }

    public String getHistoricalUrl() {
        return url + services.getHistorical();
    }

    public class Services {

        private String latest;

        private String currencies;

        private String historical;

        public String getLatest() {
            return latest;
        }

        public void setLatest(final String latest) {
            this.latest = latest;
        }

        public String getCurrencies() {
            return currencies;
        }

        public void setCurrencies(final String currencies) {
            this.currencies = currencies;
        }

        public String getHistorical() {
            return historical;
        }

        public void setHistorical(final String historical) {
            this.historical = historical;
        }
    }
}
