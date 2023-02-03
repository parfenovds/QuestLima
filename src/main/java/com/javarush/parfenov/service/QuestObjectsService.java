package com.javarush.parfenov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.entity.*;
import com.javarush.parfenov.repository.AnswerRepository;
import com.javarush.parfenov.repository.ParentToChildRepository;
import com.javarush.parfenov.repository.QuestionRepository;
import lombok.SneakyThrows;

import java.util.Iterator;

public enum QuestObjectsService {
    INSTANCE;
    private static final QuestionRepository QUESTION_REPOSITORY = QuestionRepository.INSTANCE;
    private static final AnswerRepository ANSWER_REPOSITORY = AnswerRepository.INSTANCE;
    private static final ParentToChildRepository PARENT_TO_CHILD_REPOSITORY = ParentToChildRepository.INSTANCE;
    private static final Long QUEST_ID = 1L;

    @SneakyThrows
    public void jsonParse(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        treeConverter(root);
    }

    private void putQuestion(JsonNode node) {
        Question question = Question.builder()
                .nodeId(node.get("node_id").asLong())
                .shortName(node.get("name").asText())
                .text(node.get("text").asText())
                .questId(QUEST_ID)
                .type(QuestionType.valueOf(node.get("type").asText().toUpperCase()))
                .build();
        QUESTION_REPOSITORY.create(question);

    }

    private void addAdditionalLinks(JsonNode node) {
        if(node.has("additional_linked_nodes")) {
            long nodeId = node.get("node_id").asLong();
            JsonNode additionalLinkedNodes = node.get("additional_linked_nodes");
            if(!additionalLinkedNodes.isEmpty()) {
                for (JsonNode linkedNode : additionalLinkedNodes) {
                    putParentToChild(nodeId, linkedNode);
                }
            }
        }
    }

    private void putParentToChild(long nodeId, JsonNode linkedNode) {
        ParentToChild parentToChild = ParentToChild.builder()
                .questId(QUEST_ID)
                .parentNodeId(nodeId)
                .childNodeId(linkedNode.get("node_id").asLong())
                .build();
        System.out.println(parentToChild);
        PARENT_TO_CHILD_REPOSITORY.create(parentToChild);
    }

    private void putAnswer(JsonNode node) {
        Answer answer = Answer.builder()
                .nodeId(node.get("node_id").asLong())
                .shortName(node.get("name").asText())
                .text(node.get("text").asText())
                .questionId(node.get("node_parent").asLong())
                .questId(QUEST_ID)
                .type(AnswerType.valueOf(node.get("type").asText().toUpperCase()))
                .nextQuestionId(node.get("lonely_child").asLong())
                .build();
        ANSWER_REPOSITORY.create(answer);
    }

    private void treeConverter(JsonNode root) {
        Iterator<JsonNode> iterator = root.iterator();
        putQuestion(root);
        while (iterator.hasNext()) {
            upperConverter(iterator);
        }
    }

    private void upperConverter(Iterator<JsonNode> iterator) {
        JsonNode next = iterator.next();
        if (next.isArray()) {
            for (JsonNode node : next) {
                extracted(node);
            }
        }
    }

    private void lowerConverter(JsonNode node) {
        for (JsonNode children : node.get("children")) {
            extracted(children);
        }
    }

    private void extracted(JsonNode node) {
        addAdditionalLinks(node);
        if(node.get("type").asText().equals("question")) {
            putQuestion(node);
        } else {
            putAnswer(node);
        }
        if (node.has("children")) {
            lowerConverter(node);
        }
    }
}
