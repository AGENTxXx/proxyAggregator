package com.enuvid.proxyaggregator.providers;

import com.enuvid.proxyaggregator.data.Proxy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public abstract class ProxyProvider {
    private ExecutorService pool = Executors.newFixedThreadPool(18);
    private List<Future<Proxy>> newProxies = new ArrayList<>();

    abstract void parse();

    public List<Proxy> find() {
        parse();
        return getFoundProxies();
    }

    public void addProxy(String ip, int host) {
        ProxyTask task = new ProxyTask().setContext(ip, host);

        newProxies.add(pool.submit(task));
    }

    private List<Proxy> getFoundProxies() {
        List<Proxy> proxies = new ArrayList<>();
        for (Future<Proxy> f : newProxies) {
            try {
                proxies.add(f.get());
            } catch (Exception ignored) {
            }
        }
        return proxies;
    }
}