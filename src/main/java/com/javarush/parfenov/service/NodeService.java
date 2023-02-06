package com.javarush.parfenov.service;

import com.javarush.parfenov.dto.NodeDto;
import com.javarush.parfenov.dto.QuestDto;
import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.NodeType;
import com.javarush.parfenov.entity.Quest;
import com.javarush.parfenov.mapper.NodeDtoToNodeMapper;
import com.javarush.parfenov.mapper.NodeToNodeDtoMapper;
import com.javarush.parfenov.mapper.QuestDtoToQuestMapper;
import com.javarush.parfenov.mapper.QuestToQuestDtoMapper;
import com.javarush.parfenov.repository.NodeRepository;
import com.javarush.parfenov.repository.QuestRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum NodeService {
    INSTANCE;
    private static final NodeRepository NODE_REPOSITORY = NodeRepository.INSTANCE;
    private static final NodeDtoToNodeMapper NODE_DTO_TO_NODE_MAPPER = NodeDtoToNodeMapper.INSTANCE;
    private static final QuestToQuestDtoMapper QUEST_TO_QUEST_DTO_MAPPER = QuestToQuestDtoMapper.INSTANCE;
    public Node create(NodeDto dto) {
//        userValidation(dto);
        Node node = NODE_DTO_TO_NODE_MAPPER.mapFrom(dto);
        return NODE_REPOSITORY.create(node);
    }

//    private void userValidation(CreateUserDto dto) {
//        List<ValidationError> validationErrors = createUserValidator.validityChecker(dto);
//        if(!validationErrors.isEmpty()) {
//            throw new ValidationException(validationErrors);
//        }
//    }

    public boolean update(Node node) {
        return NODE_REPOSITORY.update(node);
    }
//    public boolean delete(Long id) {
//        return NODE_REPOSITORY.delete(id);
//    }
    public boolean delete(Node node) {
        return NODE_REPOSITORY.delete(node);
    }
    public Collection<Node> getAll() {
        return NODE_REPOSITORY.getAll();
    }
    public Optional<Node> get(Long nodeId, Long questId) {
        return NODE_REPOSITORY.get(nodeId, questId);
    }

    public Optional<Node> getByType(NodeType nodeType, Long questId) {
        return NODE_REPOSITORY.getByType(nodeType, questId);
    }
    public List<Node> getByParentId(Long parentId, Long questId) {
        return NODE_REPOSITORY.getByParentId(parentId, questId);
    }

//    public List<QuestDto> getQuestDtoByUserLogin(String login) {
//        return NODE_REPOSITORY.getByUserLogin(login)
//                .stream()
//                .map(QUEST_TO_QUEST_DTO_MAPPER::mapFrom)
//                .collect(Collectors.toList());
//    }


}

