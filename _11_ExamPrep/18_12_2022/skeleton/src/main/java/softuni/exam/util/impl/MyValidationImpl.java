package softuni.exam.util.impl;

import org.springframework.stereotype.Component;
import softuni.exam.util.MyValidation;

import javax.validation.Validation;
import javax.validation.Validator;
@Component
public class MyValidationImpl implements MyValidation {
    private final Validator validator;

    public MyValidationImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }
}
