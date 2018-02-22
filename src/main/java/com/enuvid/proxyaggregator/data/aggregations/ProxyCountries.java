package com.enuvid.proxyaggregator.data.aggregations;

import org.springframework.data.annotation.Id;

public class ProxyCountries {
    @Id
    private String country;
    private int value;

    protected ProxyCountries() {
    }

    public String getCountry() {
        return country;
    }

    public int getValue() {
        return value;
    }
}
