package com.javarush.parfenov.mapper;

import com.javarush.parfenov.dto.NodeDto;
import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.NodeType;
import com.javarush.parfenov.entity.Quest;

public enum NodeDtoToNodeMapper implements Mapper<NodeDto, Node>{
    INSTANCE;

    @Override
    public Node mapFrom(NodeDto source) {
        return Node.builder()
                .nodeId(Long.parseLong(source.getNodeId()))
                .shortName(source.getShortName())
                .text(source.getText())
                .parentId(Long.parseLong(source.getParentId()))
                .questId(Long.parseLong(source.getQuestId()))
                .type(NodeType.valueOf(source.getType()))
                .nextLonelyId(Long.parseLong(source.getNextLonelyId()))
                .build();
    }
}
