package com.javarush.parfenov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtoQ {
    private Long parentNodeId;
    private Long childNodeId;
}