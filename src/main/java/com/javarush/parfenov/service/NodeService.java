package com.javarush.parfenov.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.parfenov.dto.NodeDto;
import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.NodeType;
import com.javarush.parfenov.mapper.NodeDtoToNodeMapper;
import com.javarush.parfenov.repository.NodeRepository;
import com.javarush.parfenov.util.JsonStringExtractor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public enum NodeService {
    INSTANCE;
    private final NodeRepository NODE_REPOSITORY = NodeRepository.INSTANCE;
    private final NodeDtoToNodeMapper NODE_DTO_TO_NODE_MAPPER = NodeDtoToNodeMapper.INSTANCE;

    public Node create(NodeDto dto) {
        Node node = NODE_DTO_TO_NODE_MAPPER.mapFrom(dto);
        return NODE_REPOSITORY.create(node);
    }

    public boolean update(Node node) {
        return NODE_REPOSITORY.update(node);
    }

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
}

