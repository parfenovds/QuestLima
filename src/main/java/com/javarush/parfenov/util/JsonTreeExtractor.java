package com.javarush.parfenov.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
@UtilityClass
public class JsonTreeExtractor {
    public static int counter = 0;
    public static void main(String[] args) throws IOException {
        URL resource = JsonTreeExtractor.class.getClassLoader().getResource("j.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(resource);
        treeConverter(root);
        System.out.println(counter);
    }

    private static void putQuestion(JsonNode node) {
        System.out.println("Question node put: ");
        System.out.println(node);
        counter++;
    }

    private static void putAnswer(JsonNode node, String questionId, String nextQuestionId) {
        System.out.println("Answer node put: ");
        System.out.println(node);
        System.out.printf("Bind to %s question id; next question id is %s%n", questionId, nextQuestionId);
        counter++;
    }

    private static void treeConverter(JsonNode root) {
        Iterator<JsonNode> iterator = root.iterator();
        putQuestion(root);
        while (iterator.hasNext()) {
            upperConverter(iterator);
        }
    }

    private static void upperConverter(Iterator<JsonNode> iterator) {
        JsonNode next = iterator.next();
        if (next.isArray()) {
            for (JsonNode node : next) {
                extracted(node);
            }
        }
    }

    private static void lowerConverter(JsonNode node) {
        for (JsonNode children : node.get("children")) {
            extracted(children);
        }
    }

    private static void extracted(JsonNode node) {
        if(node.get("type").toString().equals("question")) {
            putQuestion(node);
        } else {
            putAnswer(node, node.get("node_parent").asText(), node.get("lonely_child").asText());
        }
        if (node.has("children")) {
            lowerConverter(node);
        }
    }
}
