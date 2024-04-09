package com.example.football.util.impl;

import com.example.football.util.MyValidation;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;

@Component
public class MyValidationImpl implements MyValidation {
    public final Validator validator;

    public MyValidationImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }
}
