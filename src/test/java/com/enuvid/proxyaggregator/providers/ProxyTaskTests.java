package com.enuvid.proxyaggregator.providers;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyTaskTests {
    @Test
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ProxyTask task = new ProxyTask().setContext("173.194.222.102", 80);

        executor.submit(task);
        executor.submit(task);

        while (executor.isShutdown()) ;
    }
}
