package com.enuvid.proxyaggregator.utils;

import org.junit.Test;

import java.net.Proxy;

public class ProxyUtilsTests {
    @Test
    public void checkProxySpeed() {
        System.out.println(ProxyUtils.checkSpeed(
                2319773914L, 1080,
                Proxy.Type.SOCKS
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
