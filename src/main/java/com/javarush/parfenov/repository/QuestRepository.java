package com.javarush.parfenov.repository;

import com.javarush.parfenov.entity.Quest;
import com.javarush.parfenov.exception.QException;
import com.javarush.parfenov.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum QuestRepository implements Repository<Quest> {
    INSTANCE;
    private static final String CREATE_SQL = """
            INSERT INTO quests (user_id, text, name) VALUES (?, ?, ?)
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, user_id, text, name FROM quests
            """;
    private static final String FIND_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private static final String FIND_BY_USER_LOGIN_SQL = """
            SELECT q.id, q.user_id, q.text, q.name, u.login FROM quests q
            JOIN users u on u.id = q.user_id
            WHERE login = ?;
            """;
    private static final String UPDATE_SQL = """
            UPDATE quests
            SET user_id = ?,
                text = ?,
                name = ?
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM quests
            WHERE id = ?
            """;


    @Override
    public Quest create(Quest entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setString(2, entity.getText());
            preparedStatement.setString(3, entity.getName());
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
    public List<Quest> getAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            return getQuests(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Quest> getByUserLogin(String login) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_LOGIN_SQL)) {
            preparedStatement.setString(1, login);
            return getQuests(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Quest> getQuests(PreparedStatement preparedStatement) throws SQLException {
        List<Quest> result = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Quest quest = Quest.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .text(resultSet.getString("text"))
                    .name(resultSet.getString("name"))
                    .build();
            result.add(quest);
        }
        return result;
    }

    @Override
    public Optional<Quest> get(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_SQL)) {
            preparedStatement.setLong(1, id);
            Quest quest = getQuest(preparedStatement);
            return Optional.ofNullable(quest);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Quest getQuest(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        Quest quest = null;
        if (resultSet.next()) {
            quest = Quest.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .text(resultSet.getString("text"))
                    .name(resultSet.getString("name"))
                    .build();
        }
        return quest;
    }

    @Override
    public boolean update(Quest entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setString(2, entity.getText());
            preparedStatement.setString(3, entity.getName());
            preparedStatement.setLong(4, entity.getId());
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
    public boolean delete(Quest entity) {
        return delete(entity.getId());
    }
}

