package com.javarush.khmelov.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game implements AbstractEntity {
    private Long id;

    private Long questId;

    private Long userId;

    private Long currentQuestionId;

    private GameState gameState;
}
