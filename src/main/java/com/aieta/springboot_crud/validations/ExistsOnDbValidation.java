package com.aieta.springboot_crud.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.aieta.springboot_crud.services.ProductService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
@Lazy
public class ExistsOnDbValidation implements ConstraintValidator<ExistsOnDb, String> {

    @Autowired
    private ProductService service;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if (service == null) {
                return true; 
            }
            return !service.existsBySku(value);
        } catch (Exception e) {
            context.buildConstraintViolationWithTemplate("Error al validar: " + e.getMessage())
                  .addConstraintViolation();
            return false;
        }
    }
}
