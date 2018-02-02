package com.enuvid.proxyaggregator.utils;

import org.junit.Test;

import java.util.Map;

public class IpUtilsTests {
    @Test
    public void convert() {
        String entryIp = "255.255.255.255";
        Long ip = IpUtils.convert(entryIp);
        String outerIp = IpUtils.convert(ip);
        assert entryIp.equals(outerIp);
    }

    @Test
    public void ping() {
        assert IpUtils.ping("google.com") != -1;
    }

    @Test
    public void getLocation() {
        Map<String, String> location = IpUtils.getLocation("46.170.3.12");
        assert location != null;

        location.forEach((k, v) ->
                System.out.println(k + ":   " + v)
        );
    }
}
