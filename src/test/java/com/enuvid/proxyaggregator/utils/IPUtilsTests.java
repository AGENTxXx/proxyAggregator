package com.enuvid.proxyaggregator.utils;

import org.junit.Test;

import java.util.Map;

public class IPUtilsTests {
    @Test
    public void convert() {
        String entryIp = "255.255.255.255";
        Long ip = IPUtils.convert(entryIp);
        String outerIp = IPUtils.convert(ip);
        assert entryIp.equals(outerIp);
    }

    @Test
    public void getLocation() {
        Map<String, String> location = IPUtils.getLocation("46.170.3.12");
        assert location != null;

        location.forEach((k, v) ->
                System.out.println(k + ":   " + v)
        );
    }

    @Test
    public void ip2long() {
        System.out.println(IPUtils.convert("95.110.186.48"));
    }
}
