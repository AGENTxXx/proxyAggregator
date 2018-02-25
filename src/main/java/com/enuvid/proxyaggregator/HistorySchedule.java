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
    private volatile boolean fileBlocked = false;

    @Autowired
    public HistorySchedule(ProxyRepository proxyRepo, BlockedProxyRepository blockedRepo) {
        this.proxyRepo = proxyRepo;
        this.blockedRepo = blockedRepo;
    }

    @Scheduled(
            fixedRate = 1000 * 10,  //1 hour
            initialDelay = 1000
    )
    void addNextHourlyValue() {
        while (true) {
            if (!(fileBlocked)) break;
        }
        fileBlocked = true;
        logger.log(Level.INFO, "Add new records to 'hourlyMetrics'");

        DB db = DBMaker.fileDB("history.db").make();
        BTreeMap proxiesHourlyCounter = db.treeMap("proxyHourlyCounter").createOrOpen();
        BTreeMap blockedHourlyCounter = db.treeMap("blockedHourlyCounter").createOrOpen();
        addRecord(proxiesHourlyCounter, (int) proxyRepo.count(), 24);
        addRecord(blockedHourlyCounter, (int) blockedRepo.count(), 24);
        db.close();
        fileBlocked = false;
    }

    @Scheduled(
            fixedRate = 1000 * 10,  //24 hour
            initialDelay = 1000
    )
    void addNextDailyValue() {
        while (true) {
            if (!(fileBlocked)) break;
        }
        fileBlocked = true;
        logger.log(Level.INFO, "Add new records to 'dailyMetrics'");

        DB db = DBMaker.fileDB("history.db").make();
        BTreeMap proxiesDailyCounter = db.treeMap("proxyDailyCounter").createOrOpen();
        addRecord(proxiesDailyCounter, (int) proxyRepo.count(), 30);
        db.close();

        fileBlocked = false;
    }

    private void addRecord(BTreeMap map, int pushValue, int maxRecords) {
        if (map.size() == maxRecords)
            map.remove(map.firstKey());

        //noinspection unchecked
        map.put(new Date(), pushValue);
    }
}
