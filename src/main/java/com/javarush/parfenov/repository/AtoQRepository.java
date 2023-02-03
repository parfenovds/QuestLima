package com.javarush.parfenov.repository;

import com.javarush.parfenov.entity.AtoQ;
import com.javarush.parfenov.exception.QException;
import com.javarush.parfenov.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public enum AtoQRepository implements ManyToManyRepository<AtoQ> {
    INSTANCE;
    private static final String CREATE_SQL = """
            INSERT INTO a_to_q_additional_links (quest_id, parent_node_id, child_node_id) VALUES (?, ?, ?)
            """;
    private static final String FIND_ALL_SQL = """
            SELECT quest_id, parent_node_id, child_node_id FROM a_to_q_additional_links
            """;

    private static final String FIND_UNIQUE = FIND_ALL_SQL + """
            WHERE quest_id = ? AND parent_node_id = ? AND child_node_id = ?
            """;
    private static final String FIND_APPROPRIATE_SQL = FIND_ALL_SQL + """
            WHERE quest_id = ? AND parent_node_id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE a_to_q_additional_links
            SET quest_id = ?,
                parent_node_id = ?,
                child_node_id = ?
            WHERE quest_id = ? AND parent_node_id = ? AND child_node_id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM a_to_q_additional_links
            WHERE quest_id = ? AND parent_node_id = ? AND child_node_id = ?
            """;


    @Override
    public AtoQ create(AtoQ entity) {
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
    public Collection<AtoQ> getAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<AtoQ> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AtoQ aToQ = AtoQ.builder()
                        .questId(resultSet.getLong("quest_id"))
                        .parentNodeId(resultSet.getLong("parent_node_id"))
                        .childNodeId(resultSet.getLong("child_node_id"))
                        .build();
                result.add(aToQ);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<AtoQ> getApproptiate(Long questId, Long parentId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_APPROPRIATE_SQL)) {
            preparedStatement.setLong(1, questId);
            preparedStatement.setLong(1, parentId);
            List<AtoQ> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AtoQ aToQ = AtoQ.builder()
                        .questId(resultSet.getLong("quest_id"))
                        .parentNodeId(resultSet.getLong("parent_node_id"))
                        .childNodeId(resultSet.getLong("child_node_id"))
                        .build();
                result.add(aToQ);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AtoQ> get(Long questId, Long parentId, Long childId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_UNIQUE)) {
            preparedStatement.setLong(1, questId);
            preparedStatement.setLong(2, parentId);
            preparedStatement.setLong(3, childId);
            AtoQ aToQ = getAtoQ(preparedStatement);
            return Optional.ofNullable(aToQ);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static AtoQ getAtoQ(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        AtoQ aToQ = null;
        if (resultSet.next()) {
            aToQ = AtoQ.builder()
                    .questId(resultSet.getLong("quest_id"))
                    .parentNodeId(resultSet.getLong("parent_node_id"))
                    .childNodeId(resultSet.getLong("child_node_id"))
                    .build();
        }
        return aToQ;
    }

    @Override
    public boolean update(AtoQ entity) {
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
    public boolean delete(AtoQ entity) {
        return delete(entity.getQuestId(), entity.getParentNodeId(), entity.getChildNodeId());
    }
}


