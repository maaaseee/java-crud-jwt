package com.aieta.springboot_crud.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.aieta.springboot_crud.entities.Product;

@Component
public class ProductValidation implements Validator {

    @SuppressWarnings("null")
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @SuppressWarnings("null")
    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null, "no puede estar vacío.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", null, "no puede estar vacío.");
        if (product.getPrice() == null) {
            errors.rejectValue("price", null, "tiene que tener un precio.");
        } else if (product.getPrice() <= 0) {
            errors.rejectValue("price", null, "no puede ser menor o igual a 0");
        }
    }

}
