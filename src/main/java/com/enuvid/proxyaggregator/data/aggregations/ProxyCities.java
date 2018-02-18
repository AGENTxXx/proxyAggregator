package com.enuvid.proxyaggregator.data.aggregations;

import org.springframework.data.annotation.Id;

public class ProxyCities {
    @Id
    private String region;
    private String country;
    private int value;

    protected ProxyCities() {
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public int getValue() {
        return value;
    }
}
