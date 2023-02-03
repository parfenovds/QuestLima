package com.javarush.parfenov.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    String login;
    String role;
}
