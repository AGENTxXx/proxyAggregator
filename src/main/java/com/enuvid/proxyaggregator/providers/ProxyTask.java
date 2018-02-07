package com.enuvid.proxyaggregator.providers;

import com.enuvid.proxyaggregator.data.BlockedProxyRepository;
import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxyTask implements Callable<Proxy> {
    private final ProxyRepository proxyRepo;
    private final BlockedProxyRepository blockedRepo;

    private final Logger logger = Logger.getLogger(ProxyTask.class.getName());

    private int port;
    private String ip;

    ProxyTask(ProxyRepository proxyRepo, BlockedProxyRepository blockedRepo) {
        this.proxyRepo = proxyRepo;
        this.blockedRepo = blockedRepo;
    }

    public ProxyTask setContext(String ip, int port) {
        this.port = port;
        this.ip = ip;

        return this;
    }

    public Proxy call() {
        try {
            if (proxyRepo.existsByIpAndPort(ip, port)) {
                logger.log(Level.INFO, "Proxy " + ip + ":" + port + " already exist in database");
                return null;
            } else {
                if (!blockedRepo.existsByIpAndPort(ip, port)) {
                    logger.log(Level.INFO, "Check proxy " + ip + ":" + port);
                    return new Proxy(ip, port);
                } else {
                    logger.log(Level.INFO, "Proxy " + ip + ":" + port + " is in the blocked list");
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }
}
