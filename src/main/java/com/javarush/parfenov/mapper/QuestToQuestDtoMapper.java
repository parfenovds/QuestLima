package com.javarush.parfenov.mapper;

import com.javarush.parfenov.dto.QuestDto;
import com.javarush.parfenov.dto.UserDto;
import com.javarush.parfenov.entity.Quest;
import com.javarush.parfenov.entity.User;

public enum QuestToQuestDtoMapper implements Mapper<Quest, QuestDto>{
    INSTANCE;

    @Override
    public QuestDto mapFrom(Quest source) {
        return QuestDto.builder()
                .id(source.getId())
                .userId(source.getUserId())
                .text(source.getText())
                .name(source.getName())
                .build();
    }
}
