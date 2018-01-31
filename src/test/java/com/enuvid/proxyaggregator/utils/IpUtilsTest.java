package com.enuvid.proxyaggregator.utils;

import org.junit.Test;

public class IpUtilsTest {
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
}
