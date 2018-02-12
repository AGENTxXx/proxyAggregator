package com.enuvid.proxyaggregator;

import com.enuvid.proxyaggregator.data.BlockedProxyRepository;
import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Updater {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final ProxyRepository proxyRepo;
    private final BlockedProxyRepository blockedRepo;
    private ExecutorService pool = Executors.newFixedThreadPool(40);

    @Autowired
    public Updater(ProxyRepository proxyRepo, BlockedProxyRepository blockedRepo) {
        this.proxyRepo = proxyRepo;
        this.blockedRepo = blockedRepo;
    }

    @Scheduled(fixedDelay = 1)
    public void update() {
        for (int i = 0; true; i++) {
            logger.log(Level.INFO, "Check new page");
            Page<Proxy> partOfProxies = proxyRepo.findAll(new PageRequest(i, 100));

            List<Future<?>> threads = new ArrayList<>();
            for (Proxy oneProxy : partOfProxies) //Add all proxy from result to thread pool
                threads.add(pool.submit(
                        new ProxyStatusUpdater(oneProxy, blockedRepo, proxyRepo)
                ));

            try { //Wait until all tasks was be completed
                for (Future<?> thread : threads)
                    thread.get();
            } catch (Exception ignored) {
            }

            if (partOfProxies.isLast()) {
                logger.log(Level.INFO, "End of proxy list!");
                logger.log(Level.INFO, "Records: " + partOfProxies.getTotalElements() + " Pages: " + (i + 1));

                break;
            }
        }
    }
}
