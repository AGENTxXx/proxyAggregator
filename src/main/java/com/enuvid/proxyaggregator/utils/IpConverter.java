package com.enuvid.proxyaggregator.utils;

public class IpConverter {
    public static Long convert(String ipAddress) {
        String[] ipAddressInArray = ipAddress.split("\\.");

        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);
        }

        return result;
    }

    public static String convert(Long ip) {
        StringBuilder result = new StringBuilder(15);

        for (int i = 0; i < 4; i++) {
            result.insert(0,Long.toString(ip & 0xff));

            if (i < 3)
                result.insert(0,'.');

            ip = ip >> 8;
        }
        return result.toString();
    }
}
