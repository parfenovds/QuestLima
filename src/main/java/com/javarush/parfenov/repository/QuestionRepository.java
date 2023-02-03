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

public enum QuestionRepository implements Repository<Question> {
    INSTANCE;
    private static final String CREATE_SQL = """
            INSERT INTO questions (short_name, text, quest_id, type) VALUES (?, ?, ?, ?)
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, short_name, text, quest_id, type FROM questions
            """;
    private static final String FIND_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE questions
            SET short_name = ?,
                text = ?,
                quest_id = ?,
                type = ?
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM questions
            WHERE id = ?
            """;


    @Override
    public Question create(Question entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, entity.getShortName());
            preparedStatement.setString(2, entity.getText());
            preparedStatement.setLong(3, entity.getQuestId());
            preparedStatement.setString(4, entity.getType().name());
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getLong("id"));
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
                        .id(resultSet.getLong("id"))
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
    public Optional<Question> get(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_SQL)) {
            preparedStatement.setLong(1, id);
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
                    .id(resultSet.getLong("id"))
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
            preparedStatement.setString(1, entity.getShortName());
            preparedStatement.setString(2, entity.getText());
            preparedStatement.setLong(3, entity.getQuestId());
            preparedStatement.setString(4, entity.getType().name());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new QException(e);
        }
    }

    @Override
    public boolean delete(Question entity) {
        return delete(entity.getId());
    }
}

