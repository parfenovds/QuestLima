package com.javarush.parfenov.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ParentToChildDto {
    String questId;
    String parentNodeId;
    String childNodeId;
}
