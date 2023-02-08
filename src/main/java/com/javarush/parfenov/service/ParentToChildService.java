package com.javarush.parfenov.service;

import com.javarush.parfenov.dto.ParentToChildDto;
import com.javarush.parfenov.entity.ParentToChild;
import com.javarush.parfenov.mapper.ParentToChildDtoToParentToChildMapper;
import com.javarush.parfenov.repository.ParentToChildRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public enum ParentToChildService {
    INSTANCE;
    private final ParentToChildRepository PARENT_TO_CHILD_REPOSITORY = ParentToChildRepository.INSTANCE;
    private final ParentToChildDtoToParentToChildMapper PARENT_TO_CHILD_DTO_TO_PARENT_TO_CHILD_MAPPER =
            ParentToChildDtoToParentToChildMapper.INSTANCE;

    public ParentToChild create(ParentToChildDto dto) {
        ParentToChild parentToChild = PARENT_TO_CHILD_DTO_TO_PARENT_TO_CHILD_MAPPER.mapFrom(dto);
        return PARENT_TO_CHILD_REPOSITORY.create(parentToChild);
    }

    public boolean update(ParentToChild parentToChild) {
        return PARENT_TO_CHILD_REPOSITORY.update(parentToChild);
    }

    public boolean delete(ParentToChild parentToChild) {
        return PARENT_TO_CHILD_REPOSITORY.delete(parentToChild);
    }

    public Collection<ParentToChild> getAll() {
        return PARENT_TO_CHILD_REPOSITORY.getAll();
    }

    public Optional<ParentToChild> get(Long questId, Long parentId, Long childId) {
        return PARENT_TO_CHILD_REPOSITORY.get(questId, parentId, childId);
    }

    public List<ParentToChild> getAppropriate(Long questId, Long parentId) {
        return PARENT_TO_CHILD_REPOSITORY.getApproptiate(questId, parentId);
    }
}
