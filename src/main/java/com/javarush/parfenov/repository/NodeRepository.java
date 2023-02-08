package com.javarush.parfenov.repository;

import com.javarush.parfenov.entity.Node;
import com.javarush.parfenov.entity.NodeType;
import com.javarush.parfenov.exception.QException;
import com.javarush.parfenov.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum NodeRepository implements TwoPrimaryKeyRepository<Node> {
    INSTANCE;
    private static final String CREATE_SQL = """
            INSERT INTO nodes (node_id, short_name, text, parent_id, quest_id, type, next_lonely_id) VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String FIND_ALL_SQL = """
            SELECT node_id, short_name, text, parent_id, quest_id, type, next_lonely_id FROM nodes
            """;
    private static final String FIND_SQL = FIND_ALL_SQL + """
            WHERE node_id = ? AND quest_id = ?
            """;

    private static final String FIND_BY_TYPE_AND_QUEST_ID_SQL = FIND_ALL_SQL + """
            WHERE type = ? AND quest_id = ?
            """;

    private static final String FIND_BY_PARENT_ID_AND_QUEST_ID_SQL = FIND_ALL_SQL + """
            WHERE parent_id = ? AND quest_id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE nodes
            SET node_id = ?,
                short_name = ?,
                text = ?,
                parent_id = ?,
                quest_id = ?,
                type = ?,
                next_lonely_id = ?
            WHERE node_id = ? AND quest_id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM nodes
            WHERE node_id = ? AND quest_id = ?
            """;

    private static final String DELETE_BY_QUEST_SQL = """
            DELETE FROM nodes
            WHERE quest_id = ?
            """;


    @Override
    public Node create(Node entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)
        ) {
            prepareStatement(entity, preparedStatement);
            preparedStatement.execute();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Node entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement(entity, preparedStatement);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void prepareStatement(Node entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, entity.getNodeId());
        preparedStatement.setString(2, entity.getShortName());
        preparedStatement.setString(3, entity.getText());
        preparedStatement.setLong(4, entity.getParentId());
        preparedStatement.setLong(5, entity.getQuestId());
        preparedStatement.setString(6, entity.getType().name());
        preparedStatement.setLong(7, entity.getNextLonelyId());
    }

    @Override
    public List<Node> getAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            return prepareNodes(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Node> getByParentId(Long parentId, Long questId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PARENT_ID_AND_QUEST_ID_SQL)) {
            preparedStatement.setLong(1, parentId);
            preparedStatement.setLong(2, questId);
            return prepareNodes(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Node> prepareNodes(PreparedStatement preparedStatement) throws SQLException {
        List<Node> result = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Node node = Node.builder()
                    .nodeId(resultSet.getLong("node_id"))
                    .shortName(resultSet.getString("short_name"))
                    .text(resultSet.getString("text"))
                    .parentId(resultSet.getLong("parent_id"))
                    .questId(resultSet.getLong("quest_id"))
                    .type(NodeType.valueOf(resultSet.getString("type")))
                    .nextLonelyId(resultSet.getLong("next_lonely_id"))
                    .build();
            result.add(node);
        }
        return result;
    }
    @Override
    public Optional<Node> get(Long nodeId, Long questId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_SQL)) {
            preparedStatement.setLong(1, nodeId);
            preparedStatement.setLong(2, questId);
            Node node = getNode(preparedStatement);
            return Optional.ofNullable(node);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: it should return collection!!!

    public Optional<Node> getByType(NodeType nodeType, Long questId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_TYPE_AND_QUEST_ID_SQL)) {
            preparedStatement.setString(1, nodeType.name());
            preparedStatement.setLong(2, questId);
            Node node = getNode(preparedStatement);
            return Optional.ofNullable(node);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static Node getNode(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        Node node = null;
        if (resultSet.next()) {
            node = Node.builder()
                    .nodeId(resultSet.getLong("node_id"))
                    .shortName(resultSet.getString("short_name"))
                    .text(resultSet.getString("text"))
                    .parentId(resultSet.getLong("parent_id"))
                    .questId(resultSet.getLong("quest_id"))
                    .type(NodeType.valueOf(resultSet.getString("type")))
                    .nextLonelyId(resultSet.getLong("next_lonely_id"))
                    .build();
        }
        return node;
    }

    @Override
    public boolean delete(Long nodeId, Long questId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, nodeId);
            preparedStatement.setLong(2, questId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new QException(e);
        }
    }

    public boolean deleteByQuest(Long questId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_QUEST_SQL)) {
            preparedStatement.setLong(1, questId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new QException(e);
        }
    }

    @Override
    public boolean delete(Node entity) {
        return delete(entity.getNodeId(), entity.getQuestId());
    }
}

