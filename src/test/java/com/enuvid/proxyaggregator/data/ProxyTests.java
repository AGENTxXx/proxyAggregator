package com.enuvid.proxyaggregator.data;

import com.google.gson.Gson;
import org.junit.Test;

public class ProxyTests {
    @Test
    public void create() {
        Proxy someProxy = new Proxy("185.136.177.192", 10);

        System.out.println(new Gson().toJson(someProxy));
    }
}
