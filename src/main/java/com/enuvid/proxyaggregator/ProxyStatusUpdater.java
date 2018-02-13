package com.enuvid.proxyaggregator;

import com.enuvid.proxyaggregator.data.BlockedProxy;
import com.enuvid.proxyaggregator.data.BlockedProxyRepository;
import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxyStatusUpdater implements Runnable {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final ProxyRepository proxyRepo;
    private final BlockedProxyRepository blockedRepo;
    private Proxy proxy;

    ProxyStatusUpdater(Proxy proxy, BlockedProxyRepository blockedRepo, ProxyRepository proxyRepo) {
        this.proxyRepo = proxyRepo;
        this.blockedRepo = blockedRepo;
        this.proxy = proxy;
    }

    @Override
    public void run() {
        if (!proxy.update()) {
            logger.log(Level.INFO, "Bad status proxy " + proxy.getIp() + ":" + proxy.getPort());
            if (proxy.toBlockedList()) {
                logger.log(Level.INFO, "Delete proxy " + proxy.getIp() + ":" + proxy.getPort());
                blockedRepo.insert(
                        new BlockedProxy(proxy)
                );

                proxyRepo.delete(proxy);
                return;
            }
        } else logger.log(Level.INFO, "Good status proxy " + proxy.getIp() + ":" + proxy.getPort());

        proxyRepo.save(proxy);
    }
}
