package com.javarush.parfenov.repository;

import com.javarush.parfenov.entity.Answer;
import com.javarush.parfenov.entity.AnswerType;
import com.javarush.parfenov.exception.QException;
import com.javarush.parfenov.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public enum AnswerRepository implements Repository<Answer> {
    INSTANCE;
    private static final String CREATE_SQL = """
            INSERT INTO answers (short_name, text, question_id, quest_id, type, next_question_id) VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, short_name, text, question_id, quest_id, type, next_question_id FROM answers
            """;
    private static final String FIND_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE answers
            SET short_name = ?,
                text = ?,
                question_id = ?,
                quest_id = ?,
                type = ?,
                next_question_id = ?
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM answers
            WHERE id = ?
            """;


    @Override
    public Answer create(Answer entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, entity.getShortName());
            preparedStatement.setString(2, entity.getText());
            preparedStatement.setLong(3, entity.getQuestionId());
            preparedStatement.setLong(4, entity.getQuestId());
            preparedStatement.setString(5, entity.getType().name());
            preparedStatement.setLong(6, entity.getNextQuestionId());
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
    public Collection<Answer> getAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Answer> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Answer answer = Answer.builder()
                        .id(resultSet.getLong("id"))
                        .shortName(resultSet.getString("short_name"))
                        .text(resultSet.getString("text"))
                        .questionId(resultSet.getLong("question_id"))
                        .questId(resultSet.getLong("quest_id"))
                        .type(AnswerType.valueOf(resultSet.getString("type")))
                        .questId(resultSet.getLong("next_question_id"))
                        .build();
                result.add(answer);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Answer> get(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_SQL)) {
            preparedStatement.setLong(1, id);
            Answer answer = getAnswer(preparedStatement);
            return Optional.ofNullable(answer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Answer getAnswer(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        Answer answer = null;
        if (resultSet.next()) {
            answer = Answer.builder()
                    .id(resultSet.getLong("id"))
                    .shortName(resultSet.getString("short_name"))
                    .text(resultSet.getString("text"))
                    .questionId(resultSet.getLong("question_id"))
                    .questId(resultSet.getLong("quest_id"))
                    .type(AnswerType.valueOf(resultSet.getString("type")))
                    .questId(resultSet.getLong("next_question_id"))
                    .build();
        }
        return answer;
    }

    @Override
    public boolean update(Answer entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getShortName());
            preparedStatement.setString(2, entity.getText());
            preparedStatement.setLong(3, entity.getQuestionId());
            preparedStatement.setLong(4, entity.getQuestId());
            preparedStatement.setString(5, entity.getType().name());
            preparedStatement.setLong(6, entity.getNextQuestionId());
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
    public boolean delete(Answer entity) {
        return delete(entity.getId());
    }
}

