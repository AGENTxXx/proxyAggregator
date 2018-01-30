package com.enuvid.proxyaggregator;

import com.enuvid.proxyaggregator.data.Test;
import com.enuvid.proxyaggregator.data.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TestSchedule {
    private final TestRepository testRepo;

    @Autowired
    public TestSchedule(TestRepository testRepo) {
        this.testRepo = testRepo;
    }

    @Scheduled(fixedRate = 5000)
    void addRecordToTestCollection() {
        Test t = new Test();
        t.date = new Date().toString();
        testRepo.insert(t);
        System.out.println("Added! " + t.date);
    }
}
