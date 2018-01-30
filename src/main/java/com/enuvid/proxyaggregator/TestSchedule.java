package com.enuvid.proxyaggregator;

import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import com.enuvid.proxyaggregator.data.ProxyType;
import com.enuvid.proxyaggregator.data.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestSchedule {
    private final ProxyRepository proxyRepo;

    @Autowired
    public TestSchedule(ProxyRepository proxyRepo) {
        this.proxyRepo = proxyRepo;
    }

    @Scheduled(fixedRate = 5000)
    void addRecordToTestCollection() {
        Proxy testProxy = new Proxy("127.0.0.1", 2000, ProxyType.SOCKS5, new Update(120));
        proxyRepo.insert(testProxy);
    }
}
