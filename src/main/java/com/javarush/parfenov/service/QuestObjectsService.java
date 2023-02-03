package com.javarush.parfenov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.entity.Answer;
import com.javarush.parfenov.entity.AnswerType;
import com.javarush.parfenov.entity.Question;
import com.javarush.parfenov.entity.QuestionType;
import com.javarush.parfenov.repository.AnswerRepository;
import com.javarush.parfenov.repository.QuestionRepository;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.Iterator;

public enum QuestObjectsService {
    INSTANCE;
    private static final QuestionRepository questionRepository = QuestionRepository.INSTANCE;
    private static final AnswerRepository answerRepository = AnswerRepository.INSTANCE;
    private static final Long QUEST_ID = 1L;

    @SneakyThrows
    public void jsonParse(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        treeConverter(root);
    }

    private void putQuestion(JsonNode node) {
        Question question = Question.builder()
                .shortName(node.get("name").asText())
                .text(node.get("text").asText())
                .questId(QUEST_ID)
                .type(QuestionType.valueOf(node.get("type").asText().toUpperCase()))
                .build();
        questionRepository.create(question);
    }

    private void putAnswer(JsonNode node, String questionId, String nextQuestionId) {
        Answer answer = Answer.builder()
                .shortName(node.get("name").asText())
                .text(node.get("text").asText())
                .questionId(Long.valueOf(questionId))
                .questId(QUEST_ID)
                .type(AnswerType.valueOf(node.get("type").asText().toUpperCase()))
                .nextQuestionId(Long.valueOf(nextQuestionId))
                .build();
        System.out.println(answer);
        answerRepository.create(answer);
//        System.out.println("Answer node put: ");
//        System.out.println(node);
//        System.out.printf("Bind to %s question id; next question id is %s%n", questionId, nextQuestionId);
    }

    private void putQToALink(Long parentId, Long childId) {

    }

    private void putAToQLink(Long parentId, Long childId) {

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
        if(node.get("type").asText().equals("question")) {
            putQuestion(node);
        } else {
            putAnswer(node, node.get("node_parent").asText(), node.get("lonely_child").asText());
        }
        if (node.has("children")) {
            lowerConverter(node);
        }
    }
}
