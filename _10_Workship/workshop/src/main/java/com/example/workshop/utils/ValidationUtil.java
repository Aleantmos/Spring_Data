package com.example.workshop.utils;

import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    private final Validator validator;

    public ValidationUtil(Validator validator) {
        this.validator = validator;
    }

    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).isEmpty();
    }
}
