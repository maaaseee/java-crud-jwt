package com.aieta.springboot_crud.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aieta.springboot_crud.entities.Product;

@Repository
public interface ProductDAO extends CrudRepository<Product, Long> {
    boolean existsBySku(String sku);
}
