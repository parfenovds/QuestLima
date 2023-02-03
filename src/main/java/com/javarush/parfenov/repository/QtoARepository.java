package com.javarush.parfenov.repository;

import com.javarush.parfenov.entity.QtoA;
import com.javarush.parfenov.exception.QException;
import com.javarush.parfenov.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public enum QtoARepository implements ManyToManyRepository<QtoA> {
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


    @Override
    public QtoA create(QtoA entity) {
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
    public Collection<QtoA> getAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<QtoA> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                QtoA qToA = QtoA.builder()
                        .questId(resultSet.getLong("quest_id"))
                        .parentNodeId(resultSet.getLong("parent_node_id"))
                        .childNodeId(resultSet.getLong("child_node_id"))
                        .build();
                result.add(qToA);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<QtoA> getApproptiate(Long questId, Long parentId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_APPROPRIATE_SQL)) {
            preparedStatement.setLong(1, questId);
            preparedStatement.setLong(1, parentId);
            List<QtoA> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                QtoA qToA = QtoA.builder()
                        .questId(resultSet.getLong("quest_id"))
                        .parentNodeId(resultSet.getLong("parent_node_id"))
                        .childNodeId(resultSet.getLong("child_node_id"))
                        .build();
                result.add(qToA);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<QtoA> get(Long questId, Long parentId, Long childId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_UNIQUE)) {
            preparedStatement.setLong(1, questId);
            preparedStatement.setLong(2, parentId);
            preparedStatement.setLong(3, childId);
            QtoA qToA = getQtoA(preparedStatement);
            return Optional.ofNullable(qToA);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static QtoA getQtoA(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        QtoA qToA = null;
        if (resultSet.next()) {
            qToA = QtoA.builder()
                    .questId(resultSet.getLong("quest_id"))
                    .parentNodeId(resultSet.getLong("parent_node_id"))
                    .childNodeId(resultSet.getLong("child_node_id"))
                    .build();
        }
        return qToA;
    }

    @Override
    public boolean update(QtoA entity) {
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
    public boolean delete(QtoA entity) {
        return delete(entity.getQuestId(), entity.getParentNodeId(), entity.getChildNodeId());
    }
}
