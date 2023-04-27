package com.example.assignment1104.service;

import com.example.assignment1104.entity.Product;
import com.example.assignment1104.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product getById(int id){
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product id: " + id + " not found"));
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public Product edit(int id, Product product){
        return productRepository.findById(id).map(productFound -> {
            productFound.setCode(product.getCode());
            productFound.setName(product.getName());
            productFound.setPrice(product.getPrice());
            productFound.setQuantity(product.getQuantity());
            productFound.setDescription(product.getDescription());
            return productRepository.save(productFound);
        }).orElse(null);
    }

    public void delete(int id){
        productRepository.deleteById(id);
    }
}
