package com.javarush.parfenov.repository;

import com.javarush.parfenov.entity.Question;
import com.javarush.parfenov.entity.QuestionType;
import com.javarush.parfenov.exception.QException;
import com.javarush.parfenov.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public enum QuestionRepository implements TwoPrimaryKeyRepository<Question> {
    INSTANCE;
    private static final String CREATE_SQL = """
            INSERT INTO questions (node_id, short_name, text, quest_id, type) VALUES (?, ?, ?, ?, ?)
            """;
    private static final String FIND_ALL_SQL = """
            SELECT node_id, short_name, text, quest_id, type FROM questions
            """;
    private static final String FIND_SQL = FIND_ALL_SQL + """
            WHERE node_id = ? AND quest_id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE questions
            SET node_id = ?,
                short_name = ?,
                text = ?,
                quest_id = ?,
                type = ?
            WHERE node_id = ? AND quest_id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM questions
            WHERE node_id = ? AND quest_id = ?
            """;


    @Override
    public Question create(Question entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL)
        ) {
            preparedStatement.setLong(1, entity.getNodeId());
            preparedStatement.setString(2, entity.getShortName());
            preparedStatement.setString(3, entity.getText());
            preparedStatement.setLong(4, entity.getQuestId());
            preparedStatement.setString(5, entity.getType().name());
            preparedStatement.execute();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Question> getAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Question> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Question question = Question.builder()
                        .nodeId(resultSet.getLong("node_id"))
                        .shortName(resultSet.getString("short_name"))
                        .text(resultSet.getString("text"))
                        .questId(resultSet.getLong("quest_id"))
                        .type(QuestionType.valueOf(resultSet.getString("type")))
                        .build();
                result.add(question);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Question> get(Long nodeId, Long questId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_SQL)) {
            preparedStatement.setLong(1, nodeId);
            preparedStatement.setLong(2, questId);
            Question question = getQuestion(preparedStatement);
            return Optional.ofNullable(question);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Question getQuestion(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        Question question = null;
        if (resultSet.next()) {
            question = Question.builder()
                    .nodeId(resultSet.getLong("node_id"))
                    .shortName(resultSet.getString("short_name"))
                    .text(resultSet.getString("text"))
                    .questId(resultSet.getLong("quest_id"))
                    .type(QuestionType.valueOf(resultSet.getString("type")))
                    .build();
        }
        return question;
    }

    @Override
    public boolean update(Question entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, entity.getQuestId());
            preparedStatement.setString(2, entity.getShortName());
            preparedStatement.setString(3, entity.getText());
            preparedStatement.setLong(4, entity.getQuestId());
            preparedStatement.setString(5, entity.getType().name());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public boolean delete(Question entity) {
        return delete(entity.getNodeId(), entity.getQuestId());
    }
}

