package com.javarush.parfenov.mapper;

import com.javarush.parfenov.dto.QuestDto;
import com.javarush.parfenov.entity.Quest;

public enum QuestDtoToQuestMapper implements Mapper<QuestDto, Quest>{
    INSTANCE;

    @Override
    public Quest mapFrom(QuestDto source) {
        return Quest.builder()
                .id(source.getId())
                .userId(source.getUserId())
                .text(source.getText())
                .name(source.getName())
                .build();
    }
}
