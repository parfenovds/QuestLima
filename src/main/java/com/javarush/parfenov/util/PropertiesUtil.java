package com.javarush.parfenov.util;

import com.javarush.parfenov.exception.QException;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream resourceAsStream = PropertiesUtil
                .class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new QException(e);
        }
    }
    public String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
