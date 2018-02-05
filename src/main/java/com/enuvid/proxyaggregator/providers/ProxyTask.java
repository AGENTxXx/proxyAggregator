package com.enuvid.proxyaggregator.providers;

import com.enuvid.proxyaggregator.data.BlockedProxyRepository;
import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
public class ProxyTask implements Callable<Proxy> {
    @Autowired
    private ProxyRepository proxyRepo;
    @Autowired
    private BlockedProxyRepository blockedRepo;

    private int port;
    private String ip;

    public ProxyTask setContext(String ip, int port) {
        this.port = port;
        this.ip = ip;

        return this;
    }

    public Proxy call() {
        return new Proxy(ip, port);
    }
}
