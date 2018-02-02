package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IpUtils;

import java.util.Date;

public class Update {
    private Date date;
    private int ping;

    Update(String host) {
        this.date = new Date();
        this.ping = IpUtils.ping(host);
    }

    Update(Long ip) {
        this.ping = IpUtils.ping(ip);
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public int getPing() {
        return ping;
    }
}
