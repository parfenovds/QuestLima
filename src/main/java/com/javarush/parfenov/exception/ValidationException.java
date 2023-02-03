package com.javarush.parfenov.exception;

import com.javarush.parfenov.validator.ValidationError;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends RuntimeException {
    @Getter
    private final List<ValidationError> validationErrors;
    public ValidationException(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
