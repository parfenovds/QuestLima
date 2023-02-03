package com.javarush.parfenov.mapper;

import com.javarush.parfenov.dto.UserDto;
import com.javarush.parfenov.entity.User;

public enum UserToUserDtoMapper implements Mapper<User, UserDto> {
    INSTANCE;
    @Override
    public UserDto mapFrom(User source) {
        return UserDto.builder()
                .login(source.getLogin())
                .role(source.getRole().name())
                .build();
    }
}
