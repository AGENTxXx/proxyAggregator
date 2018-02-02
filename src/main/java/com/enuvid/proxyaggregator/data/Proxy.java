package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IpUtils;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Proxy {
    @Id
    private String id;

    private long ip;
    private long port;
    private ProxyType type;
    private int numUpdates;
    private int numSuccessfulUpdates;
    private String city;
    private String countryCode;
    private int StillUpdate;
    private List<Update> lastUpdates;

    public String getCountryCode() {
        return countryCode;
    }

    public void setNumUpdates(int numUpdates) {
        this.numUpdates = numUpdates;
    }

    public void setNumSuccessfulUpdates(int numSuccessfulUpdates) {
        this.numSuccessfulUpdates = numSuccessfulUpdates;
    }

    public void setStillUpdate(int stillUpdate) {
        StillUpdate = stillUpdate;
    }

    public void setLastUpdates(List<Update> lastUpdates) {
        this.lastUpdates = lastUpdates;
    }

    public String getId() {
        return id;
    }

    public String getIp() {
        return IpUtils.convert(ip);
    }

    public long getPort() {
        return port;
    }

    public ProxyType getType() {
        return type;
    }

    public Date getLastUpdate() {
        return lastUpdates.get(lastUpdates.size()).getDate();
    }

    public int getNumUpdates() {
        return numUpdates;
    }

    public int getNumSuccessfulUpdates() {
        return numSuccessfulUpdates;
    }

    public String getCity() {
        return city;
    }

    public int getStillUpdate() {
        return StillUpdate;
    }

    public List<Update> getLastUpdates() {
        return lastUpdates;
    }

    public Proxy(String ip, long port, ProxyType type) {
        this.ip = IpUtils.convert(ip);
        this.port = port;
        this.type = type;
        this.numSuccessfulUpdates = 1;
        this.numUpdates = 1;

        this.lastUpdates = new ArrayList<>();
        this.lastUpdates.add(new Update(ip));

        Map location = IpUtils.getLocation(ip);
        if (location != null) {
            this.city = location.get("city").toString();
            this.countryCode = location.get("countryCode").toString();
        }
    }
}
