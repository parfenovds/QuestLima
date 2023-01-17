package com.javarush.khmelov.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer implements AbstractEntity {
    private Long id;

    private Long questionId;

    private String text;

    private Long nextQuestionId;
}
