package com.example.assignment1104.controller;

import com.example.assignment1104.entity.Product;
import com.example.assignment1104.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:3000/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<Product> getAll(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable(name = "id") int id){
        return productService.getById(id);
    }

    @PostMapping("")
    public Product create(@RequestBody Product product){
        return productService.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable(name = "id") int id, @RequestBody Product product){
        return productService.edit(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") int id){
        productService.delete(id);
    }
}
