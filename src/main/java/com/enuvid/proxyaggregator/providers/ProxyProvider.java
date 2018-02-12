package com.enuvid.proxyaggregator.providers;

import com.enuvid.proxyaggregator.data.BlockedProxyRepository;
import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ProxyProvider {
    final Logger logger = Logger.getLogger(this.getClass().getName());
    private ExecutorService pool = Executors.newFixedThreadPool(24);
    private ProxyRepository proxyRepo;
    private BlockedProxyRepository blockedRepo;
    private List<Future<Proxy>> newProxies = new ArrayList<>();

    public ProxyProvider setThreadPool(ExecutorService pool) {
        this.pool = pool;
        return this;
    }

    abstract void parse(WebDriver driver);

    public List<Proxy> find(WebDriver driver, ProxyRepository proxyRepo, BlockedProxyRepository blockedRepo) {
        this.proxyRepo = proxyRepo;
        this.blockedRepo = blockedRepo;

        parse(driver);
        return getFoundProxies();
    }

    void addProxy(String ip, int host) {
        ProxyTask task = new ProxyTask(proxyRepo, blockedRepo).setContext(ip, host);

        newProxies.add(pool.submit(task));
    }

    private List<Proxy> getFoundProxies() {
        List<Proxy> proxies = new ArrayList<>();
        for (Future<Proxy> f : newProxies) {
            try {
                Proxy p = f.get();
                if (p != null) {
                    logger.log(Level.INFO, "New proxy to add: " + p.getIp() + ":" + p.getPort());
                    proxies.add(p);
                }
            } catch (Exception ignored) {
            }
        }
        return proxies;
    }
}