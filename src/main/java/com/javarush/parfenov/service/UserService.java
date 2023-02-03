package com.javarush.parfenov.service;

import com.javarush.parfenov.dto.CreateUserDto;
import com.javarush.parfenov.dto.UserDto;
import com.javarush.parfenov.entity.User;
import com.javarush.parfenov.exception.ValidationException;
import com.javarush.parfenov.mapper.CreateUserDtoToUserMapper;
import com.javarush.parfenov.mapper.UserToUserDtoMapper;
import com.javarush.parfenov.repository.UserRepository;
import com.javarush.parfenov.validator.CreateUserValidator;
import com.javarush.parfenov.validator.ValidationError;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public enum UserService {
    INSTANCE;
    private final UserRepository userRepository = UserRepository.INSTANCE;
    private final CreateUserValidator createUserValidator = CreateUserValidator.INSTANCE;
    private final UserToUserDtoMapper userToUserDtoMapper = UserToUserDtoMapper.INSTANCE;
    public User create(CreateUserDto dto) {
        userValidation(dto);
        User user = CreateUserDtoToUserMapper.INSTANCE.mapFrom(dto);
        return userRepository.create(user);
    }

    private void userValidation(CreateUserDto dto) {
        List<ValidationError> validationErrors = createUserValidator.validityChecker(dto);
        if(!validationErrors.isEmpty()) {
            throw new ValidationException(validationErrors);
        }
    }

    public boolean update(User user) {
        return userRepository.update(user);
    }
    public boolean delete(Long id) {
        return userRepository.delete(id);
    }
    public boolean delete(User user) {
        return userRepository.delete(user);
    }
    public Collection<User> getAll() {
        return userRepository.getAll();
    }
    public Optional<User> get(Long id) {
        return userRepository.get(id);
    }

    public Optional<User> getByLogin(String login) {
        return userRepository.getByLogin(login);
    }

    public Optional<UserDto> login(String login, String password) {
        Optional<User> userByLoginAndPassword = userRepository.getByLoginAndPassword(login, password);
        UserDto userDto = null;
        if(userByLoginAndPassword.isPresent()) {
            userDto = userToUserDtoMapper.mapFrom(userByLoginAndPassword.get());
        }
        return Optional.ofNullable(userDto);
    }
}
