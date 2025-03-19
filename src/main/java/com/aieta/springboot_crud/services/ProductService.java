package com.aieta.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import com.aieta.springboot_crud.entities.Product;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long targetId);

    Product save(Product product);

    Optional<Product> update(Long targetId, Product product);
    
    Optional<Product> delete(Long id);

    boolean existsBySku(String sku);
}
