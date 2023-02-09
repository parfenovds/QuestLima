package com.javarush.parfenov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.entity.*;
import com.javarush.parfenov.repository.ParentToChildRepository;
import com.javarush.parfenov.repository.NodeRepository;
import lombok.SneakyThrows;

import java.util.Iterator;

public enum QuestObjectsService {
    INSTANCE;
    private final NodeRepository NODE_REPOSITORY = NodeRepository.INSTANCE;
    private final ParentToChildRepository PARENT_TO_CHILD_REPOSITORY = ParentToChildRepository.INSTANCE;

    @SneakyThrows
    public void jsonParse(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        long questId = root.get("quest_id").asLong();
        NODE_REPOSITORY.deleteByQuest(questId);
        PARENT_TO_CHILD_REPOSITORY.deleteByQuest(questId);
        treeConverter(root);
    }

    private void treeConverter(JsonNode root) {
        Iterator<JsonNode> iterator = root.iterator();
        putNode(root);
        while (iterator.hasNext()) {
            upperConverter(iterator);
        }
    }

    private void putNode(JsonNode node) {
        Node builtNode = Node.builder()
                .nodeId(node.get("node_id").asLong())
                .shortName(node.get("name").asText())
                .parentId(node.get("node_parent").asLong())
                .text(node.get("text").asText())
                .questId(node.get("quest_id").asLong())
                .type(NodeType.valueOf(node.get("type").asText().toUpperCase()))
                .nextLonelyId(node.get("lonely_child").asLong())
                .build();
        NODE_REPOSITORY.create(builtNode);

    }

    private void upperConverter(Iterator<JsonNode> iterator) {
        JsonNode next = iterator.next();
        if (next.isArray()) {
            for (JsonNode node : next) {
                middleConverter(node);
            }
        }
    }

    private void middleConverter(JsonNode node) {
        addAdditionalLinks(node);
        putNode(node);
        if (node.has("children")) {
            lowerConverter(node);
        }
    }

    private void addAdditionalLinks(JsonNode node) {
        if (node.has("additional_linked_nodes")) {
            long nodeId = node.get("node_id").asLong();
            JsonNode additionalLinkedNodes = node.get("additional_linked_nodes");
            if (!additionalLinkedNodes.isEmpty()) {
                for (JsonNode linkedNode : additionalLinkedNodes) {
                    putParentToChild(nodeId, linkedNode, node.get("quest_id").asLong());
                }
            }
        }
    }

    private void putParentToChild(long nodeId, JsonNode linkedNode, Long questId) {
        ParentToChild parentToChild = ParentToChild.builder()
                .questId(questId)
                .parentNodeId(nodeId)
                .childNodeId(linkedNode.get("node_id").asLong())
                .build();
        PARENT_TO_CHILD_REPOSITORY.create(parentToChild);
    }


    private void lowerConverter(JsonNode node) {
        for (JsonNode children : node.get("children")) {
            middleConverter(children);
        }
    }
}
