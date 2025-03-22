package com.aieta.springboot_crud.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ExistsOnDbValidation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ExistsOnDb {
    String message() default "ya existe en la base de datos.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
