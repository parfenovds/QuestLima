package com.javarush.parfenov.repository;

import com.javarush.parfenov.entity.ParentToChild;
import com.javarush.parfenov.exception.QException;
import com.javarush.parfenov.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ParentToChildRepository implements ManyToManyRepository<ParentToChild> {
    INSTANCE;
    private static final String CREATE_SQL = """
            INSERT INTO q_to_a_additional_links (quest_id, parent_node_id, child_node_id) VALUES (?, ?, ?)
            """;
    private static final String FIND_ALL_SQL = """
            SELECT quest_id, parent_node_id, child_node_id FROM q_to_a_additional_links
            """;

    private static final String FIND_UNIQUE = FIND_ALL_SQL + """
            WHERE quest_id = ? AND parent_node_id = ? AND child_node_id = ?
            """;
    private static final String FIND_APPROPRIATE_SQL = FIND_ALL_SQL + """
            WHERE quest_id = ? AND parent_node_id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE q_to_a_additional_links
            SET quest_id = ?,
                parent_node_id = ?,
                child_node_id = ?
            WHERE quest_id = ? AND parent_node_id = ? AND child_node_id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM q_to_a_additional_links
            WHERE quest_id = ? AND parent_node_id = ? AND child_node_id = ?
            """;
    private static final String DELETE_BY_QUEST_SQL = """
            DELETE FROM q_to_a_additional_links
            WHERE quest_id = ?
            """;


    @Override
    public ParentToChild create(ParentToChild entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)
        ) {
            preparedStatement.setLong(1, entity.getQuestId());
            preparedStatement.setLong(2, entity.getParentNodeId());
            preparedStatement.setLong(3, entity.getChildNodeId());
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ParentToChild> getAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            return getParentToChildren(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ParentToChild> getApproptiate(Long questId, Long parentId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_APPROPRIATE_SQL)) {
            preparedStatement.setLong(1, questId);
            preparedStatement.setLong(2, parentId);
            return getParentToChildren(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ParentToChild> getParentToChildren(PreparedStatement preparedStatement) throws SQLException {
        List<ParentToChild> result = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ParentToChild parentToChild = ParentToChild.builder()
                    .questId(resultSet.getLong("quest_id"))
                    .parentNodeId(resultSet.getLong("parent_node_id"))
                    .childNodeId(resultSet.getLong("child_node_id"))
                    .build();
            result.add(parentToChild);
        }
        return result;
    }

    @Override
    public Optional<ParentToChild> get(Long questId, Long parentId, Long childId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_UNIQUE)) {
            preparedStatement.setLong(1, questId);
            preparedStatement.setLong(2, parentId);
            preparedStatement.setLong(3, childId);
            ParentToChild parentToChild = getParentToChild(preparedStatement);
            return Optional.ofNullable(parentToChild);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static ParentToChild getParentToChild(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        ParentToChild parentToChild = null;
        if (resultSet.next()) {
            parentToChild = ParentToChild.builder()
                    .questId(resultSet.getLong("quest_id"))
                    .parentNodeId(resultSet.getLong("parent_node_id"))
                    .childNodeId(resultSet.getLong("child_node_id"))
                    .build();
        }
        return parentToChild;
    }

    @Override
    public boolean update(ParentToChild entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, entity.getQuestId());
            preparedStatement.setLong(2, entity.getParentNodeId());
            preparedStatement.setLong(3, entity.getChildNodeId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long questId, Long parentId, Long childId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, questId);
            preparedStatement.setLong(2, parentId);
            preparedStatement.setLong(3, childId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new QException(e);
        }
    }

    @Override
    public boolean delete(ParentToChild entity) {
        return delete(entity.getQuestId(), entity.getParentNodeId(), entity.getChildNodeId());
    }

    public boolean deleteByQuest(long questId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_QUEST_SQL)) {
            preparedStatement.setLong(1, questId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new QException(e);
        }
    }
}
