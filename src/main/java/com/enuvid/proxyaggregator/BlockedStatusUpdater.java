package com.enuvid.proxyaggregator;

import com.enuvid.proxyaggregator.data.BlockedProxy;
import com.enuvid.proxyaggregator.data.BlockedProxyRepository;
import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockedStatusUpdater implements Runnable {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final ProxyRepository proxyRepo;
    private final BlockedProxyRepository blockedRepo;
    private BlockedProxy blockedProxy;

    BlockedStatusUpdater(BlockedProxy blockedProxy, BlockedProxyRepository blockedRepo, ProxyRepository proxyRepo) {
        this.proxyRepo = proxyRepo;
        this.blockedRepo = blockedRepo;
        this.blockedProxy = blockedProxy;
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "Check blocked proxy " + blockedProxy.getIp() + ":" + blockedProxy.getIp());

        if (blockedProxy.check())
            if (blockedProxy.toRestore()) {
                Proxy proxy = blockedProxy.restore();
                if (proxy != null) {
                    logger.log(Level.INFO, "Restore proxy " + proxy.getIp() + ":" + proxy.getPort());
                    proxyRepo.insert(proxy);

//                        blockedRepo.save(blockedProxy);
                    blockedRepo.delete(blockedProxy);
                    return;
                }
            }
        if (blockedProxy.toDelete()) {
            logger.log(Level.INFO, "Delete blocked proxy " + blockedProxy.getIp() + ":" + blockedProxy.getPort());
            blockedRepo.delete(blockedProxy);
            return;
        }
        blockedRepo.save(blockedProxy);
    }
}
