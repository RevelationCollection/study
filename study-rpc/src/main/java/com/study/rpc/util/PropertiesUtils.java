package com.study.rpc.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {

    private static Map<String, Properties> cache;

    private static String DEFAULT_NAME ;

    static {
        cache = new HashMap<>();
        DEFAULT_NAME = "/rpc.properties";
        try {
            Properties properties = new Properties();
            properties.load(PropertiesUtils.class.getResourceAsStream(DEFAULT_NAME));
            cache.put(DEFAULT_NAME,properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String key){
        return cache.get(DEFAULT_NAME).getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(getProperties("zk.address"));
    }
}
