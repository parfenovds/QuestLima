package com.javarush.parfenov.mapper;

import com.javarush.parfenov.dto.CreateUserDto;
import com.javarush.parfenov.entity.Role;
import com.javarush.parfenov.entity.User;

public enum CreateUserDtoToUserMapper implements Mapper<CreateUserDto, User>{
    INSTANCE;

    @Override
    public User mapFrom(CreateUserDto source) {
        return User.builder()
                .login(source.getLogin())
                .password(source.getPassword())
                .role(Role.valueOf(source.getRole()))
                .build();
    }
}
