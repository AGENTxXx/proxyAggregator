package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IP;
import org.springframework.data.annotation.Id;

public class BlockedProxy {
    @Id
    private String id;

    private Long ip;
    private int port;

    BlockedProxy(Proxy proxy) {
        this.ip = IP.convert(proxy.getIp());
        this.port = proxy.getPort();
    }

    public String getIp() {
        return IP.convert(ip);
    }

    public int getPort() {
        return port;
    }
}
