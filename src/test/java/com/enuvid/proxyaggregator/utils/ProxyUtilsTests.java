package com.enuvid.proxyaggregator.utils;

import org.junit.Test;

public class ProxyUtilsTests {
    @Test
    public void checkProxySpeed() {
        System.out.println(ProxyUtils.checkSpeed(
                "37.191.205.105", 10200,
                java.net.Proxy.Type.SOCKS
        ));
    }

    @Test
    public void getProxyType() {
        try {
            System.out.println(ProxyUtils.getType("37.191.205.105", 10200));
        } catch (Exception ignored) {
        }
    }
}
