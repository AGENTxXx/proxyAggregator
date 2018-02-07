package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IPUtils;
import com.enuvid.proxyaggregator.utils.ProxyUtils;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Proxy {
    @Id
    private String id;

    private long ip;
    private int port;
    private java.net.Proxy.Type type;
    private int numUpdates;
    private int numSuccessfulUpdates;
    private String city;
    private String countryCode;
    private int StillUpdate;
    private List<Update> lastUpdates;

    public Proxy(String ip, int port) throws Exception {
        this.ip = IPUtils.convert(ip);
        this.port = port;
        this.type = ProxyUtils.getType(ip, port);
        this.numSuccessfulUpdates = 1;
        this.numUpdates = 1;

        this.lastUpdates = new ArrayList<>();
//        this.lastUpdates.add(new Update(ip, type.get(0)));

        Map location = IPUtils.getLocation(ip);
        if (location != null) {
            this.city = location.get("city").toString();
            this.countryCode = location.get("countryCode").toString();
        }
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getId() {
        return id;
    }

    public String getIp() {
        return IPUtils.convert(ip);
    }

    public int getPort() {
        return port;
    }

    public java.net.Proxy.Type getType() {
        return type;
    }

    public Date getLastUpdate() {
        return lastUpdates.get(lastUpdates.size()).getDate();
    }

    public int getNumUpdates() {
        return numUpdates;
    }

    public void setNumUpdates(int numUpdates) {
        this.numUpdates = numUpdates;
    }

    public int getNumSuccessfulUpdates() {
        return numSuccessfulUpdates;
    }

    public void setNumSuccessfulUpdates(int numSuccessfulUpdates) {
        this.numSuccessfulUpdates = numSuccessfulUpdates;
    }

    public String getCity() {
        return city;
    }

    public int getStillUpdate() {
        return StillUpdate;
    }

    public void setStillUpdate(int stillUpdate) {
        StillUpdate = stillUpdate;
    }

    public List<Update> getLastUpdates() {
        return lastUpdates;
    }

    public void setLastUpdates(List<Update> lastUpdates) {
        this.lastUpdates = lastUpdates;
    }
}
