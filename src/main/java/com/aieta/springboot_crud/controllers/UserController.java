package com.aieta.springboot_crud.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aieta.springboot_crud.entities.User;
import com.aieta.springboot_crud.entities.UserDTO;
import com.aieta.springboot_crud.services.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200",originPatterns = "*")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list() {
        return service.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO userDTO) {
        User user = new User(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        userDTO.setAdmin(false);
        User user = new User(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }
}
