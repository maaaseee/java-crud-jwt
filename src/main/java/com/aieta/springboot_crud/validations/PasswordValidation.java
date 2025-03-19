package com.aieta.springboot_crud.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class PasswordValidation implements ConstraintValidator<isValidPassword, String> {
    
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        // Evitar validar null (esto lo manejaríamos con @NotNull por separado)
        if (password == null || password.isBlank()) {
            return true;
        }
        
        context.disableDefaultConstraintViolation();
        List<String> violations = new ArrayList<>();
        
        // Validar longitud mínima
        if (password.length() < 8) {
            violations.add("La contraseña debe tener al menos 8 caracteres");
        }
        
        // Validar al menos una mayúscula
        if (!password.matches(".*[A-Z].*")) {
            violations.add("La contraseña debe contener al menos una letra mayúscula");
        }
        
        // Validar al menos un número
        if (!password.matches(".*\\d.*")) {
            violations.add("La contraseña debe contener al menos un número");
        }
        
        // Validar que no contenga punto y coma
        if (password.contains(";")) {
            violations.add("La contraseña no puede contener el carácter ';'");
        }
        
        // Validar caracteres permitidos
        if (!password.matches("^[A-Za-z0-9@#$%^&+=!]*$")) {
            violations.add("La contraseña contiene caracteres no permitidos. Solo se permiten letras, números y los caracteres especiales @#$%^&+=!");
        }
        
        // Si hay violaciones, añadirlas al contexto
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
