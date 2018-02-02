package com.enuvid.proxyaggregator.data;

import org.junit.Test;

public class ProxyTests {
    @Test
    public void create() {
        Proxy someProxy = new Proxy("185.136.177.192", 10, ProxyType.HTTP);
    }
}
