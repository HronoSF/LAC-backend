package com.github.hronosf.validation.annotations;

import com.github.hronosf.validation.validators.InnSwiftBikValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InnSwiftBikValidator.class)
public @interface InnSwiftBik {

    String message() default "Не верное значение поля";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
