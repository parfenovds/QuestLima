package com.javarush.parfenov.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QuestDto {
    Long id;
    Long userId;
    String text;
    String name;
}
