package com.enuvid.proxyaggregator.api;

import com.enuvid.proxyaggregator.data.ProxyRepository;
import com.enuvid.proxyaggregator.data.SimpleProxy;
import com.enuvid.proxyaggregator.utils.IPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
public class GetProxies {
    private final ProxyRepository proxyRepo;

    @Autowired
    public GetProxies(ProxyRepository proxyRepo) {
        this.proxyRepo = proxyRepo;
    }

    @CrossOrigin
    @GetMapping(value = "get", produces = "application/json")
    String getProxies(
            @RequestParam(value = "limit", defaultValue = "50") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "country", defaultValue = "") String country,
            @RequestParam(value = "available", defaultValue = "1") Integer maxAvailable,
            @RequestParam(value = "timeout", defaultValue = "20000") Integer maxSpeed,
            @RequestParam(value = "type", required = false) String protocolType
    ) {
        Page<SimpleProxy> proxies = proxyRepo.filter(
                country,
                maxAvailable,
                maxSpeed,
                new PageRequest(page - 1, limit), protocolType);

        JsonObject result = new JsonObject();
        result.addProperty("totalResults", proxies.getTotalElements());
        result.addProperty("currentPage", page);
        result.addProperty("itemsOnPage", proxies.getContent().size());
        result.add("results",
                new Gson().toJsonTree(proxies.getContent())
        );


        result.get("results").getAsJsonArray().forEach(proxy -> {
            //Change ip format(Long -> String)
            long ip = proxy.getAsJsonObject().get("ip").getAsLong();
            proxy.getAsJsonObject().addProperty("ip", IPUtils.convert(ip));

            //Change field with last update date to difference between current and update date(in milliseconds)
            long diff = 0;
            try {
                SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy h:mm:ss aa", Locale.ENGLISH);
                Date updDate = format.parse(proxy.getAsJsonObject().get("lastUpdateDate").getAsString());
                diff = new Date().getTime() - updDate.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            proxy.getAsJsonObject().addProperty("lastUpdateDate", diff);
        });

        return result.toString();
    }
}
