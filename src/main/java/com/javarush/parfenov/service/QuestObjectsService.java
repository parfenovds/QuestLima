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
    private static final NodeRepository NODE_REPOSITORY = NodeRepository.INSTANCE;
    private static final ParentToChildRepository PARENT_TO_CHILD_REPOSITORY = ParentToChildRepository.INSTANCE;
    private static final Long QUEST_ID = 1L;

    @SneakyThrows
    public void jsonParse(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
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
                .questId(QUEST_ID)
                .type(NodeType.valueOf(node.get("type").asText().toUpperCase()))
                .nextLonelyId(node.get("lonely_child").asLong())
                .build();
//        System.out.println(builtNode);
        NODE_REPOSITORY.create(builtNode);

    }

    private void upperConverter(Iterator<JsonNode> iterator) {
        JsonNode next = iterator.next();
        if (next.isArray()) {
            for (JsonNode node : next) {
                extracted(node);
            }
        }
    }

    private void extracted(JsonNode node) {
        addAdditionalLinks(node);
        putNode(node);
        if (node.has("children")) {
            lowerConverter(node);
        }
    }

    private void addAdditionalLinks(JsonNode node) {
        if (node.has("additional_linked_nodes")) {
            System.out.println("HOOOO");
            long nodeId = node.get("node_id").asLong();
            JsonNode additionalLinkedNodes = node.get("additional_linked_nodes");
            if (!additionalLinkedNodes.isEmpty()) {
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
//        System.out.println(parentToChild);
        PARENT_TO_CHILD_REPOSITORY.create(parentToChild);
    }


    private void lowerConverter(JsonNode node) {
        for (JsonNode children : node.get("children")) {
            extracted(children);
        }
    }
}
