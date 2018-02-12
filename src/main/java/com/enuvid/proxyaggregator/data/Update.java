package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IPUtils;
import com.enuvid.proxyaggregator.utils.ProxyUtils;

import java.net.Proxy;
import java.util.Date;

public class Update {
    private Date date;
    private int speed;

    protected Update() {
    } //JPA constructor

    Update(String host, int port, Proxy.Type type) {
        this.date = new Date();
        this.speed = ProxyUtils.checkSpeed(host, port, type);
    }

    Update(Long ip, int port, Proxy.Type type) {
        this(IPUtils.convert(ip), port, type);
    }

    public Date getDate() {
        return date;
    }

    public int getSpeed() {
        return speed;
    }
}
