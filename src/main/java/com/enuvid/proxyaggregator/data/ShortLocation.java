package com.enuvid.proxyaggregator.data;

import java.util.Map;

public class ShortLocation {
    private String city;
    private String country;
    private String countryCode;

    public ShortLocation(Map<String, String> location) {
        if (location != null) {
            this.city = location.get("city");
            this.countryCode = location.get("countryCode");
            this.country = location.get("country");
        }
    }

    public ShortLocation(String city, String country, String countryCode) {
        this.city = city;
        this.country = country;
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
