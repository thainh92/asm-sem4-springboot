package com.example.assignment1104.controller;

import com.example.assignment1104.entity.Order;
import com.example.assignment1104.entity.OrderDto;
import com.example.assignment1104.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("http://localhost:3000/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Order getById(@PathVariable(name = "id") int id){
        return orderService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    public Order create(@RequestBody OrderDto order){
        return orderService.create(order);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Order update(@PathVariable(name = "id") int id, @RequestBody OrderDto order){
        return orderService.update(id, order);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void delete(@PathVariable(name = "id") int id){
        orderService.delete(id);
    }
}
