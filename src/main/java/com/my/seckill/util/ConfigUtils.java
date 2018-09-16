package com.my.seckill.util;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {

    private static final String FILE_NAME = "/config.properties";

    private static Properties properties = new Properties();
    static {
        try {
            properties.load(ConfigUtils.class.getResourceAsStream(FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getKey(String key){
        return properties.getProperty(key);
    }
}
