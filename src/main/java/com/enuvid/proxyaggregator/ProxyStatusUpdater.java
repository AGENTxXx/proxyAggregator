package com.enuvid.proxyaggregator;

import com.enuvid.proxyaggregator.data.*;

import java.util.List;
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


            List<Update> updates = proxy.getLastUpdates();
            final int badStatusLimit = 5;
            final int maxIndexInList = updates.size() - 1;
            if (proxy.getNumUpdates() > 10) {
                boolean toBlockedList = true;
                for (int i = maxIndexInList; i > (maxIndexInList - badStatusLimit); i--) {
                    if (updates.get(i).getSpeed() != -1) {
                        toBlockedList = false;
                        break;
                    }
                }
                if (toBlockedList) {
                    logger.log(Level.INFO, "Delete proxy " + proxy.getIp() + ":" + proxy.getPort());

                    proxyRepo.delete(proxy);

                    blockedRepo.insert(
                            new BlockedProxy(proxy)
                    );
                }
            }


        } else logger.log(Level.INFO, "Good status proxy " + proxy.getIp() + ":" + proxy.getPort());

        proxyRepo.save(proxy);
    }
}
