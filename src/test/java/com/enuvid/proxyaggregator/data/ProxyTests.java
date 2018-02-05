package com.enuvid.proxyaggregator.data;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;

public class ProxyTests {
    @Test
    public void create() {
        Proxy someProxy = new Proxy("185.136.177.192", 10, new ArrayList<ProxyType>() {{
            add(ProxyType.HTTP);
        }});


        System.out.println(new Gson().toJson(someProxy));
    }
}
