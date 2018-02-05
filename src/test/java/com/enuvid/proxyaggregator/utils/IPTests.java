package com.enuvid.proxyaggregator.utils;

import org.junit.Test;

import java.util.Map;

public class IPTests {
    @Test
    public void convert() {
        String entryIp = "255.255.255.255";
        Long ip = IP.convert(entryIp);
        String outerIp = IP.convert(ip);
        assert entryIp.equals(outerIp);
    }

    @Test
    public void getLocation() {
        Map<String, String> location = IP.getLocation("46.170.3.12");
        assert location != null;

        location.forEach((k, v) ->
                System.out.println(k + ":   " + v)
        );

    }
}
