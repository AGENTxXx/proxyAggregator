package com.enuvid.proxyaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProxyAggregatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProxyAggregatorApplication.class, args);
    }
}
