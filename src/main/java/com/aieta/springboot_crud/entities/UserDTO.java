package com.aieta.springboot_crud.entities;

import com.aieta.springboot_crud.validations.isValidPassword;
import com.aieta.springboot_crud.validations.isValidUsername;

import jakarta.validation.constraints.NotBlank;

public class UserDTO {

    @isValidUsername
    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    private String username;

    @isValidPassword
    @NotBlank(message = "La contraseña no puede estar vacía.")
    private String password;

    private boolean admin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
