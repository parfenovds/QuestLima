package com.javarush.khmelov.lesson13.service;

import com.javarush.khmelov.lesson13.entity.User;
import com.javarush.khmelov.lesson13.repository.Repository;
import com.javarush.khmelov.lesson13.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

public enum UserService {

    USER_SERVICE;

    private final Repository<User> userRepository = new UserRepository();

    public void create(User user) {
        userRepository.create(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    public Optional<User> get(long id) {
        return userRepository.get(id);
    }
}
