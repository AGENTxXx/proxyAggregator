package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IpConverter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Proxy {
    @Id
    private String id;

    private long ip;
    private long port;
    private ProxyType type;
    private int numUpdates;
    private int numSuccessfulUpdates;
    private String location;
    private int StillUpdate;
    private List<Update> lastUpdates;

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
        return IpConverter.convert(ip);
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

    public String getLocation() {
        return location;
    }

    public int getStillUpdate() {
        return StillUpdate;
    }

    public List<Update> getLastUpdates() {
        return lastUpdates;
    }

    public Proxy(String ip, long port, ProxyType type, Update update) {
        this.ip = IpConverter.convert(ip);
        this.port = port;
        this.type = type;

        this.lastUpdates = new ArrayList<>();
        this.lastUpdates.add(update);

        this.numSuccessfulUpdates = 1;
        this.numUpdates = 1;
//        TODO: Check location(expml: http://ip-api.com/json/208.80.152.100)
        this.location = "default";
    }
}
