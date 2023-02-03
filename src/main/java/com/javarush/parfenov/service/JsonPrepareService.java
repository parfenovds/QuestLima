package com.javarush.parfenov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.javarush.parfenov.entity.Quest;
import com.javarush.parfenov.entity.Question;
import com.javarush.parfenov.entity.QuestionType;
import com.javarush.parfenov.repository.QuestionRepository;

import java.util.Optional;

public enum JsonPrepareService {
    INSTANCE;
    private static final QuestionRepository QUESTION_REPOSITORY = QuestionRepository.INSTANCE;
    public String getJson(Quest quest) {
        StringBuilder stringBuilder = new StringBuilder();
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ObjectMapper mapper = new ObjectMapper();
        Optional<Question> optionalQuestion = QUESTION_REPOSITORY.getByType(QuestionType.INIT, quest.getId());
        if(optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            ObjectNode node = factory.objectNode();
            node.put("node_id", String.valueOf(question.getNodeId()))
                .put("name", question.getShortName())
                .put("text", question.getText())
                .put("type", question.getType().name().toLowerCase())
                .put("node_parent", String.valueOf(question.getParentId()))
                .put("lonely_child", "");
            ArrayNode children = factory.arrayNode();

        }
        return null;
    }
}
