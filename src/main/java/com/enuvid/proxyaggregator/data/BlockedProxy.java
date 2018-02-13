package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IPUtils;
import com.enuvid.proxyaggregator.utils.ProxyUtils;
import org.springframework.data.annotation.Id;

public class BlockedProxy {
    @Id
    private String id;

    private Long ip;
    private int port;
    private int successfulUpdates = 0;
    private int updates = 0;
    private java.net.Proxy.Type type;

    public BlockedProxy(Proxy proxy) {
        this.ip = IPUtils.convert(proxy.getIp());
        this.port = proxy.getPort();
        this.type = proxy.getType();
    }

    public int getSuccessfulUpdates() {
        return successfulUpdates;
    }

    public int getUpdates() {

        return updates;
    }

    public java.net.Proxy.Type getType() {

        return type;
    }

    public String getIp() {
        return IPUtils.convert(ip);
    }

    public int getPort() {
        return port;
    }

    public boolean check() {
        updates++;
        try {
            if (ProxyUtils.checkSpeed(ip, port, type) != -1) {
                successfulUpdates++;
                return true;
            }
            else
                successfulUpdates = 0;
        } catch (Exception e) {
            successfulUpdates = 0;
        }

        return false;
    }

    public boolean toRestore() {
        return successfulUpdates >= 7; // Successful updates needed to restore proxy from blocked list
    }

    public boolean toDelete() {
        return updates >= 50; // Bad updates needed to delete proxy form bocked list
    }

    public Proxy restore() {
        try {
            return new Proxy(this.getIp(), port, type);
        } catch (Exception e) {
            return null;
        }
    }
}
