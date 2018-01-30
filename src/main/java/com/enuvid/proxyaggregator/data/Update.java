package com.enuvid.proxyaggregator.data;

import java.util.Date;

public class Update {
    private Date date;
    private int ping;

    public Date getDate() {
        return date;
    }

    public int getPing() {
        return ping;
    }

    public Update(Date date, int ping) {
        this.date = date;
        this.ping = ping;
    }

    public Update(int ping) {
        this.ping = ping;
        this.date = new Date();
    }
}
