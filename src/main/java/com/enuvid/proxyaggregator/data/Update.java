package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IpUtils;

import java.util.Date;

public class Update {
    private Date date;
    private int speed;

    Update(String host) {
        this.date = new Date();
        this.speed = IpUtils.ping(host);
    }

    Update(Long ip) {
        this.speed = IpUtils.ping(ip);
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public int getSpeed() {
        return speed;
    }
}
