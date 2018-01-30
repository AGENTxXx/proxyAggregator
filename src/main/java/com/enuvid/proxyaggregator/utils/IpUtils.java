package com.enuvid.proxyaggregator.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IpUtils {
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
            result.insert(0, Long.toString(ip & 0xff));

            if (i < 3)
                result.insert(0, '.');

            ip = ip >> 8;
        }
        return result.toString();
    }

    public static int ping(String host) {
        try {
            String cmd = "ping";
            if (System.getProperty("os.name").startsWith("Windows")) // Windows
                cmd += " -n 1";
            else //Unix
                cmd += "-c 1";
            cmd += " " + host;

            Process pingProcess = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            pingProcess.getInputStream()));
            for (String cur; (cur = br.readLine()) != null; ) {
                if (cur.contains(": ")) { //If line contains ping value
                    int mark = System.getProperty("os.name").startsWith("Windows") ? 2 : 3; // If system is windows -> take second equal symbol
                    int startIndex = 0; // Else take third equal symbol as start point in ping number

                    for (int i = 0; i < mark; i++) { //Get start index of value in line
                        startIndex = cur.indexOf("=", startIndex + 1);
                    }

                    int endIndex;
                    endIndex = startIndex + 1;
                    while (Character.isDigit(cur.charAt(endIndex))) {
                        endIndex++;
                    }

                    return Integer.parseInt(cur.substring(startIndex + 1, endIndex)); // Get substring and convert to int
                }
            }

            return -1;
        } catch (Exception e) {

            e.printStackTrace();
            return -1;
        }
    }

    public static int ping(Long ip) {
        return ping(IpUtils.convert(ip));
    }
}
