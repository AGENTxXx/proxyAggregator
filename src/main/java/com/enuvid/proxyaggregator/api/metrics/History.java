package com.enuvid.proxyaggregator.api.metrics;

import com.enuvid.proxyaggregator.data.BlockedProxyRepository;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.TreeMap;

@RestController
public class History {
    private final ProxyRepository proxyRepo;
    private final BlockedProxyRepository blockedRepo;
    private volatile boolean fileBlocked = false;

    @Autowired
    public History(ProxyRepository proxyRepo, BlockedProxyRepository blockedRepo) {
        this.proxyRepo = proxyRepo;
        this.blockedRepo = blockedRepo;
    }

    @CrossOrigin
    @GetMapping("/metrics/hourlyAmount")
    TreeMap proxyHourlyAmount() {
        return getTreeMapByCollectionName("proxyHourlyCounter", (int) proxyRepo.count());
    }

    @CrossOrigin
    @GetMapping("/metrics/blockedHourlyAmount")
    TreeMap blockedHourlyAmount() {
        return getTreeMapByCollectionName("blockedHourlyCounter", (int) blockedRepo.count());
    }

    @CrossOrigin
    @GetMapping("/metrics/dailyAmount")
    TreeMap proxyDailyAmount() {
        return getTreeMapByCollectionName("proxyDailyCounter", (int) proxyRepo.count());
    }

    private TreeMap<Date, Integer> getTreeMapByCollectionName(String name, int currentTime) {
        while (true) {
            if (!(fileBlocked)) break;
        }
        fileBlocked = true;
        DB db = DBMaker
                .fileDB("history.db")
                .make();
        BTreeMap map = db.treeMap(name).createOrOpen();
        TreeMap<Date, Integer> result = new TreeMap<>();
        //noinspection unchecked
        map.forEach((k, v) -> result.put((Date) k, (Integer) v));
        db.close();
        fileBlocked = false;

        result.put(new Date(), currentTime);
        return result;
    }
}
