package com.enuvid.proxyaggregator.data;

import java.util.Date;

public class SimpleProxy {
    private long ip;
    private int port;
    private java.net.Proxy.Type type;
    private int avgSpeed;
    private Date lastUpdateDate;
    private int avgAvailable;

    private ShortLocation location;

    public SimpleProxy() {
    }
}
