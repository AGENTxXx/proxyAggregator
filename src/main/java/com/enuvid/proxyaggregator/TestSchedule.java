package com.enuvid.proxyaggregator;

import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import com.enuvid.proxyaggregator.data.ProxyType;
import com.enuvid.proxyaggregator.data.Update;
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
        Proxy testProxy = new Proxy("127.0.0.1", 2000, ProxyType.SOCKS5);
        proxyRepo.insert(testProxy);
        logger.log(Level.INFO, "Add new proxy at " + new Date());
    }
}
