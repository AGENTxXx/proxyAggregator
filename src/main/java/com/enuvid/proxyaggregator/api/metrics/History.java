package com.enuvid.proxyaggregator.api.metrics;

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
    private volatile boolean fileBlocked = false;

    @Autowired
    public History(ProxyRepository proxyRepo) {
        this.proxyRepo = proxyRepo;
    }

    @CrossOrigin
    @GetMapping("/metrics/hourlyAmount")
    TreeMap proxyHourlyAmount() {
        return getTreeMapByCollectionName("proxyHourlyCounter");
    }

    @CrossOrigin
    @GetMapping("/metrics/blockedHourlyAmount")
    TreeMap blockedHourlyAmount() {
        return getTreeMapByCollectionName("blockedHourlyCounter");
    }

    @CrossOrigin
    @GetMapping("/metrics/dailyAmount")
    TreeMap proxyDailyAmount() {
        return getTreeMapByCollectionName("proxyDailyCounter");
    }

    private TreeMap<Date, Integer> getTreeMapByCollectionName(String name) {
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

        result.put(new Date(), (int) proxyRepo.count());
        return result;
    }
}
