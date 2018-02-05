package com.enuvid.proxyaggregator.data;

import java.net.Proxy;
import java.util.Date;

public class Update {
    private Date date;
    private int speed;

    Update(String host, Proxy.Type type) {
        this.date = new Date();
        this.speed = 0;
    }

    Update(Long ip, Proxy.Type type) {
        this.speed = 0;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public int getSpeed() {
        return speed;
    }
}
