package com.enuvid.proxyaggregator.api.metrics;

import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.TreeMap;

@RestController
public class History {
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
        DB db = DBMaker
                .fileDB("history.db")
                .make();
        BTreeMap map = db.treeMap(name).createOrOpen();
        TreeMap<Date, Integer> result = new TreeMap<>();
        //noinspection unchecked
        map.forEach((k, v) -> result.put((Date) k, (Integer) v));
        db.close();
        return result;
    }
}
