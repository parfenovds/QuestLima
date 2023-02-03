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
            INSERT INTO a_to_q_additional_links (parent_node_id, child_node_id) VALUES (?, ?)
            """;
    private static final String FIND_ALL_SQL = """
            SELECT parent_node_id, child_node_id FROM a_to_q_additional_links
            """;

    private static final String FIND_UNIQUE = FIND_ALL_SQL + """
            WHERE parent_node_id = ? AND child_node_id = ?
            """;
    private static final String FIND_APPROPRIATE_SQL = FIND_ALL_SQL + """
            WHERE parent_node_id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE a_to_q_additional_links
            SET parent_node_id = ?,
                child_node_id = ?
            WHERE parent_node_id = ? AND child_node_id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM a_to_q_additional_links
            WHERE parent_node_id = ? AND child_node_id = ?
            """;


    @Override
    public AtoQ create(AtoQ entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)
        ) {
            preparedStatement.setLong(1, entity.getParentNodeId());
            preparedStatement.setLong(2, entity.getChildNodeId());
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
    public Collection<AtoQ> getApproptiate(Long parentId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_APPROPRIATE_SQL)) {
            preparedStatement.setLong(1, parentId);
            List<AtoQ> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AtoQ aToQ = AtoQ.builder()
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
    public Optional<AtoQ> get(Long parentId, Long childId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_UNIQUE)) {
            preparedStatement.setLong(1, parentId);
            preparedStatement.setLong(2, childId);
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
            preparedStatement.setLong(1, entity.getParentNodeId());
            preparedStatement.setLong(2, entity.getChildNodeId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long parentId, Long childId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, parentId);
            preparedStatement.setLong(2, childId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new QException(e);
        }
    }

    @Override
    public boolean delete(AtoQ entity) {
        return delete(entity.getParentNodeId(), entity.getChildNodeId());
    }
}


