package com.enuvid.proxyaggregator.providers;

import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class ProxyProvider {
    @Autowired
    private ProxyRepository proxyRepol;
    private List<Proxy> result = new ArrayList<>();

    public abstract List<Proxy> find();

    public void addProxy(String ip, int host) {

    }
}
