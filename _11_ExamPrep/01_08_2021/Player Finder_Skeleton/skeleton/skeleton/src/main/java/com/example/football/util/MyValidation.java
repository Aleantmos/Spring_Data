package com.example.football.util;

import javax.validation.Validator;

public interface MyValidation {

    <E> boolean isValid(E entity);
}
