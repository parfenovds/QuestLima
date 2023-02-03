package com.javarush.parfenov.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String login;
    String password;
    String role;
}
