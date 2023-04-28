package com.example.assignment1104.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(int id) {
        super("Could not found the product with id " + id);
    }
}
