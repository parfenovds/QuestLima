package com.javarush.parfenov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {
    private Long nodeId;
    private String shortName;
    private String text;
    private Long questionId;
    private Long questId;
    private AnswerType type;
    private Long nextQuestionId;
}
