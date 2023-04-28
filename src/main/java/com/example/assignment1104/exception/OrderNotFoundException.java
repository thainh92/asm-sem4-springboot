package com.example.assignment1104.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(int id) {
        super("Could not find the order with id " + id);
    }
}
