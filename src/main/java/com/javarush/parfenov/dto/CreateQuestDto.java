package com.javarush.parfenov.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateQuestDto {
    Long userId;
    String text;
    String name;
}

