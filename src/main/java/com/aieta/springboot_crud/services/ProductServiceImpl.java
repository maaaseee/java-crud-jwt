package com.aieta.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aieta.springboot_crud.entities.Product;
import com.aieta.springboot_crud.repositories.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO repository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long targetId) {
        return repository.findById(targetId);
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> update(Long targetId, Product product) {
        Optional<Product> productOptional = repository.findById(targetId);

        if (productOptional.isPresent()) {
            Product foundProduct = productOptional.orElseThrow();
            foundProduct.setSku(product.getSku());
            foundProduct.setName(product.getName());
            foundProduct.setDescription(product.getDescription());
            foundProduct.setPrice(product.getPrice());

            return Optional.of(repository.save(foundProduct));
        }

        return productOptional;
    }

    @Transactional
    @Override
    public Optional<Product> delete(Long targetId) {
        Optional<Product> productOptional = repository.findById(targetId);

        productOptional.ifPresent(foundProduct -> {
            repository.delete(foundProduct);
        });
        return productOptional;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsBySku(String sku) {
        return repository.existsBySku(sku);
    }
}
