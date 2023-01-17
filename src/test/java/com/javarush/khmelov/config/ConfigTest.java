package com.javarush.khmelov.config;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigTest {
    @Test
    void checkWebInf(){
        String fullPath = Objects.requireNonNull(ConfigTest.class.getResource("/")).toString();
        assertTrue(fullPath.contains(Config.WEB_INF.toString()));
    }
}