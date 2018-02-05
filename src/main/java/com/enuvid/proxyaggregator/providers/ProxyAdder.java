package com.enuvid.proxyaggregator.providers;

import com.enuvid.proxyaggregator.data.BlockedProxyRepository;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyAdder implements Runnable {
    @Autowired
    private ProxyRepository proxyRepo;
    @Autowired
    private BlockedProxyRepository blockedRepo;

    private int port;
    private String ip;

    public ProxyAdder setContext(String ip, int port) {
        this.port = port;
        this.ip = ip;

        return this;
    }

    public void run(){
        System.out.println("Thread: " + Thread.currentThread());
        System.out.println(ip + Integer.toString(port));
    }
}
