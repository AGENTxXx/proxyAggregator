package com.enuvid.proxyaggregator.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
            if (System.getProperty("os.name").startsWith("Windows")) // If operating system is Windows
                cmd += " -n 1";
            else //If Unix(Linux, Mac OS)
                cmd += " -c 1";
            cmd += " " + host;

            Process pingProcess = Runtime.getRuntime().exec(cmd); // Run cmd
            BufferedReader input = new BufferedReader( // Get app output stream
                    new InputStreamReader(
                            pingProcess.getInputStream()));

            for (String cur; (cur = input.readLine()) != null; ) { //Read one line
                if (cur.contains(": ") && !cur.contains("): ")) { //If line contains ping value
                    int mark = System.getProperty("os.name").startsWith("Windows") ? 2 : 3; // If system is windows -> take second equal symbol
                    int startIndex = 0; // Else take third equal symbol as start point in ping number

                    for (int i = 0; i < mark; i++) { //Get start index of value in line
                        startIndex = cur.indexOf("=", startIndex + 1);
                    }

                    int endIndex;
                    endIndex = startIndex + 1;
                    while (Character.isDigit(cur.charAt(endIndex))) { //Get last index of value in line
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

    public static Map<String, String> getLocation(String host) {
        try {
            final String infoService = "http://ip-api.com/json/";
            URL url = new URL(infoService + host);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jsonParser = new JsonParser();
            JsonObject response = jsonParser.parse(new InputStreamReader(
                    (InputStream) request.getContent()
            )).getAsJsonObject();

            Map<String, String> location = new HashMap<>();
            location.put("city", response.get("city").getAsString());
            location.put("region", response.get("region").getAsString());
            location.put("country", response.get("country").getAsString());
            location.put("countryCode", response.get("countryCode").getAsString());
            location.put("lat", response.get("lat").getAsString());
            location.put("lon", response.get("lon").getAsString());
            location.put("zip", response.get("zip").getAsString());
            return location;
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, String> getLocation(Long ip) {
        return getLocation(IpUtils.convert(ip));
    }
}
