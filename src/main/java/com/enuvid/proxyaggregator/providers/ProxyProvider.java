package com.enuvid.proxyaggregator.providers;

import com.enuvid.proxyaggregator.data.BlockedProxyRepository;
import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class ProxyProvider {
    private final ProxyRepository proxyRepos;
    private final BlockedProxyRepository blockedRepos;
    private List<Proxy> result = new ArrayList<>();
    Runnable adder = () -> {

    };

    @Autowired
    public ProxyProvider(ProxyRepository proxyRepol, BlockedProxyRepository blockedRepos) {
        this.proxyRepos = proxyRepol;
        this.blockedRepos = blockedRepos;
    }


    public abstract List<Proxy> find();
    public void addProxy(String ip, int host) {

    }
}
