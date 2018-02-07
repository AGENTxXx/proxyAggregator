package com.enuvid.proxyaggregator.data;

import com.google.gson.Gson;
import org.junit.Test;

public class ProxyTests {
    @Test
    public void create() {
        try {
            Proxy someProxy = new Proxy("37.191.205.105", 10200);

            System.out.println(new Gson().toJson(someProxy));
        } catch (Exception ignored) {
        }
    }
}
