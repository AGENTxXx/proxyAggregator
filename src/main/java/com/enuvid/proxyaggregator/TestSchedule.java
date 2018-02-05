package com.enuvid.proxyaggregator;

import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class TestSchedule {
    private final Logger logger = Logger.getLogger(TestSchedule.class.getName());
    private final ProxyRepository proxyRepo;

    @Autowired
    public TestSchedule(ProxyRepository proxyRepo) {
        this.proxyRepo = proxyRepo;
    }

    @Scheduled(fixedRate = 60000 * 60)
    void addRecordToTestCollection() {
        Proxy testProxy = new Proxy("185.136.177.192", 2000);
        proxyRepo.insert(testProxy);
        logger.log(Level.INFO, "Add new proxy at " + new Date());
    }
}
