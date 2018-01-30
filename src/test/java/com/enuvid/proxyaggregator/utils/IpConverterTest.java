package com.enuvid.proxyaggregator.utils;

import org.junit.Test;

public class IpConverterTest {
    @Test
    public void convert() {
        String entryIp = "255.255.255.255";
        Long ip = IpConverter.convert(entryIp);
        String outerIp = IpConverter.convert(ip);
        assert entryIp.equals(outerIp);
    }
}
