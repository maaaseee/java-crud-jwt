package com.aieta.springboot_crud.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aieta.springboot_crud.entities.Role;

@Repository
public interface RoleDAO extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String roleName);
}
