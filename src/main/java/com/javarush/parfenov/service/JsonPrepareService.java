package com.javarush.parfenov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.javarush.parfenov.entity.ParentToChild;
import com.javarush.parfenov.entity.Quest;
import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.NodeType;
import com.javarush.parfenov.repository.NodeRepository;
import com.javarush.parfenov.repository.ParentToChildRepository;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.Optional;

public enum JsonPrepareService {
    INSTANCE;
    private final NodeRepository NODE_REPOSITORY = NodeRepository.INSTANCE;
    private final ParentToChildRepository PARENT_TO_CHILD_REPOSITORY = ParentToChildRepository.INSTANCE;

    @SneakyThrows
    public String getJson(Long questId) {
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ObjectMapper mapper = new ObjectMapper();
        Optional<Node> optNode = NODE_REPOSITORY.getByType(NodeType.INIT, questId);
        Node sourceNode = optNode.get();
        ObjectNode resultJNode = recursiveTreeConstructor(factory, questId, sourceNode);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultJNode);
    }

    public String getJson(Quest quest) {
        return getJson(quest.getId());
    }

    private ObjectNode recursiveTreeConstructor(JsonNodeFactory factory, Long questId, Node sourceNode) {
        ObjectNode targetJNode = factory.objectNode();
        Long nodeId = sourceNode.getNodeId();
        targetJNode
                .put("node_id", String.valueOf(nodeId))
                .put("name", sourceNode.getShortName())
                .put("text", sourceNode.getText())
                .put("type", sourceNode.getType().name().toLowerCase())
                .put("quest_id", questId)
                .put("node_parent", String.valueOf(sourceNode.getParentId()))
                .put("lonely_child", String.valueOf(sourceNode.getNextLonelyId()));
        addAdditionalLinks(factory, questId, targetJNode, nodeId);
        addChildrenNodes(factory, questId, targetJNode, nodeId);
        return targetJNode;
    }

    private void addAdditionalLinks(JsonNodeFactory factory, Long questId, ObjectNode targetJNode, Long nodeId) {
        ArrayNode additionalLinkedNodes = factory.arrayNode();
        Collection<ParentToChild> parentToChildren = PARENT_TO_CHILD_REPOSITORY.getApproptiate(questId, nodeId);
        if (!parentToChildren.isEmpty()) {
            for (ParentToChild parentToChild : parentToChildren) {
                ObjectNode additionalLinkingNode = factory.objectNode()
                        .put("node_id", String.valueOf(parentToChild.getChildNodeId()));
                additionalLinkedNodes.add(additionalLinkingNode);
            }
            targetJNode.set("additional_linked_nodes", additionalLinkedNodes);
        }
    }

    private void addChildrenNodes(JsonNodeFactory factory, Long questId, ObjectNode targetJNode, Long nodeId) {
        ArrayNode children = factory.arrayNode();
        Collection<Node> childNodes = NODE_REPOSITORY.getByParentId(nodeId, questId);
        if (!childNodes.isEmpty()) {
            for (Node childNode : childNodes) {
                children.add(recursiveTreeConstructor(factory, questId, childNode));
            }
            targetJNode.set("children", children);
        }
    }
}
