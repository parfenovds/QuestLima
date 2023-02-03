package com.javarush.parfenov.validator;

import java.util.List;

public interface Validator<T> {
    List<ValidationError> validityChecker(T o);
}
