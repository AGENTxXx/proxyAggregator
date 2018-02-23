package com.enuvid.proxyaggregator.api.metrics;

import com.enuvid.proxyaggregator.data.ProxyRepository;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Type {
    private final ProxyRepository proxyRepo;

    @Autowired
    public Type(ProxyRepository proxyRepo) {
        this.proxyRepo = proxyRepo;
    }

    @CrossOrigin
    @GetMapping(value = "/metrics/type", produces = "application/json")
    String proxiesByType() {
        int httpAmount = proxyRepo.getHttpProxiesAmount();
        int total = (int) proxyRepo.count();
        JsonObject response = new JsonObject();
        response.addProperty("total", total);
        response.addProperty("http", httpAmount);
        response.addProperty("socks", total - httpAmount);
        return response.toString();
    }
}
