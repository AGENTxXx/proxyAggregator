package com.enuvid.proxyaggregator.data;

import java.net.Proxy;
import java.util.Date;

public class SimpleProxy {
    private long ip;
    private int port;
    private java.net.Proxy.Type type;
    private int avgSpeed;
    private Date lastUpdateDate;
    private int avgAvailable;

    private ShortLocation location;

    public long getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Proxy.Type getType() {
        return type;
    }

    public int getAvgSpeed() {
        return avgSpeed;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public int getAvgAvailable() {
        return avgAvailable;
    }

    public ShortLocation getLocation() {
        return location;
    }

    public SimpleProxy() {
    }
}
