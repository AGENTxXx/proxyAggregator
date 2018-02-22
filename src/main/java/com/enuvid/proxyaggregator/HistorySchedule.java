package com.enuvid.proxyaggregator;

import com.enuvid.proxyaggregator.data.BlockedProxyRepository;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HistorySchedule {
    private final ProxyRepository proxyRepo; //Data repos
    private final BlockedProxyRepository blockedRepo;
    private Logger logger = Logger.getLogger(this.getClass().getName()); //Logger for current classname

    @Autowired
    public HistorySchedule(ProxyRepository proxyRepo, BlockedProxyRepository blockedRepo) {
        this.proxyRepo = proxyRepo;
        this.blockedRepo = blockedRepo;
    }

    @Scheduled(
            fixedRate = 1000 * 60 * 60,  //1 hour
            initialDelay = 1000 * 60 * 60
    )
    void addNextHourlyValue() {
        logger.log(Level.INFO, "Add new records to 'hourlyMetrics'");

        DB db = DBMaker.fileDB("history.db").make();
        BTreeMap proxiesDailyCounter = db.treeMap("proxyHourlyCounter").createOrOpen();
        BTreeMap blockedDailyCounter = db.treeMap("blockedHourlyCounter").createOrOpen();
        addRecord(proxiesDailyCounter, (int) proxyRepo.count(), 24);
        addRecord(blockedDailyCounter, (int) blockedRepo.count(), 24);
        db.close();
    }

    @Scheduled(
            fixedRate = 1000 * 60 * 60 * 24,  //24 hour
            initialDelay = 1000 * 60 * 60 * 24
    )
    void addNextDailyValue() {
        logger.log(Level.INFO, "Add new records to 'dailyMetrics'");

        BTreeMap proxiesHourlyCounter = DBMaker.fileDB("history.db")
                .make().treeMap("proxyDailyCounter").createOrOpen();
        addRecord(proxiesHourlyCounter, (int) proxyRepo.count(), 30);
    }

    private void addRecord(BTreeMap map, int pushValue, int maxRecords) {
        if (map.size() == maxRecords)
            map.remove(map.firstKey());

        //noinspection unchecked
        map.put(new Date(), pushValue);
    }
}
