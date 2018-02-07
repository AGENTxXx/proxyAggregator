package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IPUtils;
import org.springframework.data.annotation.Id;

public class BlockedProxy {
    @Id
    private String id;

    private Long ip;
    private int port;

    BlockedProxy(Proxy proxy) {
        this.ip = IPUtils.convert(proxy.getIp());
        this.port = proxy.getPort();
    }

    public String getIp() {
        return IPUtils.convert(ip);
    }

    public int getPort() {
        return port;
    }
}
