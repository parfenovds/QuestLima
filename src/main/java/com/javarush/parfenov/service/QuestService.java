package com.javarush.parfenov.service;

import com.javarush.parfenov.dto.QuestDto;
import com.javarush.parfenov.entity.Quest;
import com.javarush.parfenov.mapper.QuestDtoToQuestMapper;
import com.javarush.parfenov.mapper.QuestToQuestDtoMapper;
import com.javarush.parfenov.repository.QuestRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum QuestService {
    INSTANCE;
    private final QuestRepository QUEST_REPOSITORY = QuestRepository.INSTANCE;
    private final QuestToQuestDtoMapper QUEST_TO_QUEST_DTO_MAPPER = QuestToQuestDtoMapper.INSTANCE;

    public Quest create(QuestDto dto) {
        Quest quest = QuestDtoToQuestMapper.INSTANCE.mapFrom(dto);
        return QUEST_REPOSITORY.create(quest);
    }

    public boolean update(Quest quest) {
        return QUEST_REPOSITORY.update(quest);
    }

    public boolean delete(Long id) {
        return QUEST_REPOSITORY.delete(id);
    }

    public boolean delete(Quest quest) {
        return QUEST_REPOSITORY.delete(quest);
    }

    public Collection<Quest> getAll() {
        return QUEST_REPOSITORY.getAll();
    }

    public Optional<Quest> get(Long id) {
        return QUEST_REPOSITORY.get(id);
    }

    public List<QuestDto> getQuestDtoByUserLogin(String login) {

        return QUEST_REPOSITORY.getByUserLogin(login)
                .stream()
                .map(QUEST_TO_QUEST_DTO_MAPPER::mapFrom)
                .collect(Collectors.toList());
    }


}
