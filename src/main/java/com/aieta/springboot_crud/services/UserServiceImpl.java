package com.aieta.springboot_crud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aieta.springboot_crud.entities.Role;
import com.aieta.springboot_crud.entities.User;
import com.aieta.springboot_crud.repositories.RoleDAO;
import com.aieta.springboot_crud.repositories.UserDAO;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO repository;

    @Autowired
    private RoleDAO roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        optionalRoleUser.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        user.setRoles(roles);
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    @Transactional
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

}
