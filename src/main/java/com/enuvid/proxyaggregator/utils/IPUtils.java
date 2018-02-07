package com.enuvid.proxyaggregator.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class IPUtils {
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
            if (!response.remove("status").getAsString().equals("success"))
                throw new Exception("Not \"success\" status of response");
            response.remove("query");

            Map<String, String> location = new HashMap<>();
            response.entrySet().forEach((k) ->
                    location.put(k.getKey(), k.getValue().getAsString())
            );
            return location;
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, String> getLocation(Long ip) {
        return getLocation(IPUtils.convert(ip));
    }
}
