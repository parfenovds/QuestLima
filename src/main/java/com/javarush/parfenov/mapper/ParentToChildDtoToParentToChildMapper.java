package com.javarush.parfenov.mapper;

import com.javarush.parfenov.dto.ParentToChildDto;
import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.NodeType;
import com.javarush.parfenov.entity.ParentToChild;

public enum ParentToChildDtoToParentToChildMapper implements Mapper<ParentToChildDto, ParentToChild>{
    INSTANCE;
    @Override
    public ParentToChild mapFrom(ParentToChildDto source) {
        return ParentToChild.builder()
                .questId(Long.parseLong(source.getQuestId()))
                .parentNodeId(Long.parseLong(source.getParentNodeId()))
                .childNodeId(Long.parseLong(source.getChildNodeId()))
                .build();
    }
}
