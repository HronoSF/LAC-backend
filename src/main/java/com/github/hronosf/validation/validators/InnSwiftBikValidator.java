package com.github.hronosf.validation.validators;

import com.github.hronosf.validation.annotations.InnSwiftBik;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InnSwiftBikValidator implements ConstraintValidator<InnSwiftBik, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.matches("\\d{12}") || value.matches("[0-9A-Z]+");
    }
}
