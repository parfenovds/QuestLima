package com.javarush.parfenov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.ParentToChild;
import lombok.SneakyThrows;

import java.util.List;

public enum NextDialogService {
    INSTANCE;

    private final NodeService nodeService = NodeService.INSTANCE;
    private final ParentToChildService parentToChildService = ParentToChildService.INSTANCE;

    @SneakyThrows
    public Node getAnswerNode(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);
        long questId = root.get("questId").asLong();
        long answerNodeId = root.get("nodeId").asLong();
        return nodeService.get(answerNodeId, questId).get();
    }

    public Long getNextQuestionId(Node answerNode, long questId, long answerNodeId) {
        Long nodeId;
        if (answerNode.getNextLonelyId() != 0L) {
            nodeId = answerNode.getNextLonelyId();
        } else {
            List<ParentToChild> appropriate = parentToChildService.getAppropriate(questId, answerNodeId);
            nodeId = appropriate.get(0).getChildNodeId();
        }
        return nodeId;
    }

    public List<Node> getAnswers(long questId, Long nextQuestionId, Node nextQuestion) {
        List<Node> answers = nodeService.getByParentId(nextQuestion.getNodeId(), questId);
        List<ParentToChild> appropriate = parentToChildService.getAppropriate(questId, nextQuestionId);
        for (ParentToChild parentToChild : appropriate) {
            Long childNodeId = parentToChild.getChildNodeId();
            Node node1 = nodeService.get(childNodeId, questId).get();
            answers.add(node1);
        }
        return answers;
    }
}
