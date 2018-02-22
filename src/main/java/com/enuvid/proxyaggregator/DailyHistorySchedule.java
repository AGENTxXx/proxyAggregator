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
public class DailyHistorySchedule {
    private final ProxyRepository proxyRepo; //Data repos
    private final BlockedProxyRepository blockedRepo;
    private Logger logger = Logger.getLogger(this.getClass().getName()); //Logger for current classname

    @Autowired
    public DailyHistorySchedule(ProxyRepository proxyRepo, BlockedProxyRepository blockedRepo) {
        this.proxyRepo = proxyRepo;
        this.blockedRepo = blockedRepo;
    }

    @Scheduled(
            fixedRate = 1000 * 60 * 60,  //1 hour
            initialDelay = 1000 * 60 * 60
    )
    void addNextValue() {
        logger.log(Level.INFO, "Add new records to 'dailyMetrics'");

        DB db = DBMaker.fileDB("dailyHistory.db").make();
        BTreeMap proxiesDailyCounter = db.treeMap("proxyCounter").createOrOpen();
        BTreeMap blockedDailyCounter = db.treeMap("blockedCounter").createOrOpen();
        setFor24Hours(proxiesDailyCounter, (int) proxyRepo.count());
        setFor24Hours(blockedDailyCounter, (int) blockedRepo.count());
        db.close();
    }

    private void setFor24Hours(BTreeMap map, int pushValue) {
        if (map.size() == 24)
            map.remove(map.firstKey());

        map.put(new Date(), pushValue);
    }
}
