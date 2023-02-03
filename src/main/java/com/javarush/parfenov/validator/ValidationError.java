package com.javarush.parfenov.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor
public class ValidationError {
    @Getter
    private String message;

    @Override
    public String toString() {
        return message;
    }
}
