package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IpUtils;
import org.springframework.data.annotation.Id;

public class BlockedProxy {
    @Id
    private String id;

    private Long ip;
    private int port;

    BlockedProxy(Proxy proxy) {
        this.ip = IpUtils.convert(proxy.getIp());
        this.port = proxy.getPort();
    }

    public String getIp() {
        return IpUtils.convert(ip);
    }

    public int getPort() {
        return port;
    }
}
