package com.enuvid.proxyaggregator.utils;

import com.enuvid.proxyaggregator.data.Proxy;

import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProxyUtils {
    public static java.net.Proxy.Type getType(String ip, int port) throws Exception {
        for (java.net.Proxy.Type type : Arrays.asList(java.net.Proxy.Type.HTTP, java.net.Proxy.Type.SOCKS))
            if (checkSpeed(ip, port, type) != -1)
                return type;

        throw new Exception("Can't ident proxy type");
    }

    public static int checkSpeed(String ip, int port, java.net.Proxy.Type type) {
        try {
            long startTime = System.currentTimeMillis();

            URL testHost = new URL("http://ip-api.com/json");
            java.net.Proxy proxy = new java.net.Proxy(type, new InetSocketAddress(ip, port));

            URLConnection connection = testHost.openConnection(proxy);

            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);

            connection.getContent();
            return (int) (System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            return -1;
        }
    }

    public static List<Proxy> removeDuplicates(List<Proxy> proxyList) {
        List<Proxy> tmp = new ArrayList<>(proxyList);
        int len = proxyList.size();
        for (Proxy proxy : tmp)
            proxyList.removeIf(it ->
                    proxy.getIp().equals(it.getIp()) &&
                            proxy.getPort() == it.getPort() &&
                            !proxy.getLastUpdates().equals(it.getLastUpdates())
            );

        System.out.println("Deleted duplicates: " + (len - proxyList.size()));
        return proxyList;
    }
}
