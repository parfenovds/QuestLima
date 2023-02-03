package com.javarush.parfenov.repository;

import com.javarush.parfenov.entity.Role;
import com.javarush.parfenov.entity.User;
import com.javarush.parfenov.exception.QException;
import com.javarush.parfenov.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public enum UserRepository implements Repository<User> {
    INSTANCE;
    private static final String CREATE_SQL = """
            INSERT INTO users (login, password, role) VALUES (?, ?, ?)
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, login, password, role FROM users
            """;
    private static final String FIND_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static final String FIND_BY_LOGIN_SQL = FIND_ALL_SQL + """
            WHERE login = ?
            """;
    private static final String FIND_BY_LOGIN_AND_PASSWORD_SQL = FIND_BY_LOGIN_SQL + """
            AND password = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE users
            SET login = ?,
                password = ?,
                role = ?
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;


    @Override
    public User create(User entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getRole().toString());
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
    public Collection<User> getAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<User> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .login(resultSet.getString("login"))
                        .password(resultSet.getString("password"))
                        .role(Role.valueOf(resultSet.getString("role")))
                        .build();
                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_SQL)) {
            preparedStatement.setLong(1, id);
            User user = getUser(preparedStatement);
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> getByLogin(String login) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_SQL)) {
            preparedStatement.setString(1, login);
            User user = getUser(preparedStatement);
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> getByLoginAndPassword(String login, String password) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_AND_PASSWORD_SQL)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            User user = getUser(preparedStatement);
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static User getUser(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if(resultSet.next()) {
            user = User.builder()
                    .id(resultSet.getLong("id"))
                    .login(resultSet.getString("login"))
                    .password(resultSet.getString("password"))
                    .role(Role.valueOf(resultSet.getString("role")))
                    .build();
        }
        return user;
    }

    @Override
    public boolean update(User entity) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getRole().toString());
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
    public boolean delete(User entity) {
        return delete(entity.getId());
    }
}
