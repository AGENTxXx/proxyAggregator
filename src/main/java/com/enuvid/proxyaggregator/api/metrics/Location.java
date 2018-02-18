package com.enuvid.proxyaggregator.api.metrics;

import com.enuvid.proxyaggregator.data.aggregations.ProxyCities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Location {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public Location(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/metrics/cities")
    List<ProxyCities> regions() {
        GroupOperation groupByCities = Aggregation
                .group("location.city")
                .count()
                .as("value")
                .first("location.country").as("country");
        SortOperation sortByASC = Aggregation.sort(new Sort(Sort.Direction.DESC, "value"));

        Aggregation agr = Aggregation.newAggregation(groupByCities, sortByASC);
        AggregationResults<ProxyCities> cities = mongoTemplate.aggregate(agr, "proxy", ProxyCities.class);
        return cities.getMappedResults();
    }
}
