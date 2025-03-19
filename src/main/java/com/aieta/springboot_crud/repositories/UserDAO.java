package com.aieta.springboot_crud.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aieta.springboot_crud.entities.User;

@Repository
public interface UserDAO extends CrudRepository<User, Long> {

    @SuppressWarnings("null")
    @EntityGraph(attributePaths = {"roles"})
    Iterable<User> findAll();

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String Username);
}
