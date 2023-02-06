package com.javarush.parfenov.mapper;

import com.javarush.parfenov.dto.ParentToChildDto;
import com.javarush.parfenov.entity.ParentToChild;

public enum ParentToChildToParentToChildDtoMapper implements Mapper<ParentToChild, ParentToChildDto>{
    INSTANCE;
    @Override
    public ParentToChildDto mapFrom(ParentToChild source) {
        return ParentToChildDto.builder()
                .questId(String.valueOf(source.getQuestId()))
                .parentNodeId(String.valueOf(source.getParentNodeId()))
                .childNodeId(String.valueOf(source.getChildNodeId()))
                .build();
    }
}
