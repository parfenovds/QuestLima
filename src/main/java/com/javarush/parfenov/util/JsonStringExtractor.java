package com.javarush.parfenov.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;

@UtilityClass
public class JsonStringExtractor {
    public String getJsonParameter(HttpServletRequest request) {
        try (BufferedReader reader = request.getReader()) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
