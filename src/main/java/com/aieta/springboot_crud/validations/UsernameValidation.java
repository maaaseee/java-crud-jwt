package com.aieta.springboot_crud.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aieta.springboot_crud.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UsernameValidation implements ConstraintValidator<isValidUsername, String> {

    @Autowired
    private UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.isBlank()) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        List<String> violations = new ArrayList<>();

        if (username.length() < 4 || username.length() > 28) {
            violations.add("El nombre de usuario debe tener entre 4 y 28 caracteres.");
        }

        if (service.existsByUsername(username)) {
            violations.add("El nombre de usuario ingresado ya está siendo utilizado.");
        }

        if (!violations.isEmpty()) {
            for (String violation : violations) {
                context.buildConstraintViolationWithTemplate(violation)
                        .addConstraintViolation();
            }
            return false;
        }

        return true;
    }
}
