package com.example.assignment1104.service;

import com.example.assignment1104.entity.*;
import com.example.assignment1104.exception.OrderNotFoundException;
import com.example.assignment1104.exception.ProductNotFoundException;
import com.example.assignment1104.repository.CustomerRepository;
import com.example.assignment1104.repository.OrderDetailRepository;
import com.example.assignment1104.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductService productService;

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public Order getById(int id){
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order create(OrderDto order) {
        Order newOrder = new Order();
        newOrder.setCode(order.getCode());

        //Set customer
        Customer customer = customerRepository.findById(order.getCustomerId()).get();
        if (customer == null) {
            throw new IllegalArgumentException("Customer must not be null");
        }
        newOrder.setCustomer(customer);

        // Set order details
        Set<OrderDetail> orderDetails = new HashSet<>();
        for (OrderDetailDto orderDetailDto : order.getOrderDetailDto()) {
            OrderDetail newOrderDetail = new OrderDetail();
            newOrderDetail.setQuantity(orderDetailDto.getQuantity());
            Product product = productService.getById(orderDetailDto.getProductId());
            newOrderDetail.setProduct(product);
            newOrderDetail.setOrder(newOrder);
            orderDetails.add(newOrderDetail);
        }
        newOrder.setOrderDetails(orderDetails);
        return orderRepository.save(newOrder);
    }

    //run is wrong
    public Order update(int orderId, OrderDto updatedOrder) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            Customer customer = customerRepository.findById(updatedOrder.getCustomerId()).orElse(null);
            if (customer == null) {
                throw new IllegalArgumentException("Invalid customer id");
            }
            order.setCustomer(customer);
            order.getOrderDetails().clear();
            for (OrderDetailDto orderDetailDto : updatedOrder.getOrderDetailDto()) {
                Product product = productService.getById(orderDetailDto.getProductId());
                if (product == null) {
                    throw new IllegalArgumentException("Invalid product id");
                }
                OrderDetail newOrderDetail = new OrderDetail();
                newOrderDetail.setProduct(product);
                newOrderDetail.setQuantity(orderDetailDto.getQuantity());
                newOrderDetail.setOrder(order);
                orderDetailRepository.save(newOrderDetail);
            }
            return orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order not found");
        }
    }

    public void delete(int id){
        Order order = orderRepository.findById(id)
                        .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(order);
    }
}
