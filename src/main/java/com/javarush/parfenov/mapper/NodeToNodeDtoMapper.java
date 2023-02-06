package com.javarush.parfenov.mapper;

import com.javarush.parfenov.dto.NodeDto;
import com.javarush.parfenov.dto.QuestDto;
import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.NodeType;

public enum NodeToNodeDtoMapper implements Mapper<Node, NodeDto>{
    INSTANCE;

    @Override
    public NodeDto mapFrom(Node source) {
        return NodeDto.builder()
                .nodeId(String.valueOf(source.getNodeId()))
                .shortName(source.getShortName())
                .text(source.getText())
                .parentId(String.valueOf(source.getParentId()))
                .questId(String.valueOf(source.getQuestId()))
                .type(source.getType().name())
                .nextLonelyId(String.valueOf(source.getNextLonelyId()))
                .build();
    }
}
