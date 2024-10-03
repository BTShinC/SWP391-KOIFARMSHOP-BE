package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.OrdersRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrdersResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.OrdersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/oders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    // Create Order
    @PostMapping
    public ResponseEntity<OrdersResponse> createOrder(@Valid @RequestBody OrdersRequest ordersRequest) {
        OrdersResponse newOrder = ordersService.createOrder(ordersRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    // Get All Orders
    @GetMapping
    public ResponseEntity<List<OrdersResponse>> getAllOrders() {
        List<OrdersResponse> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Get Order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrdersResponse> getOrderById(@PathVariable Long id) {
        OrdersResponse order = ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // Update Order
    @PutMapping("/{id}")
    public ResponseEntity<OrdersResponse> updateOrder(@PathVariable Long id, @Valid @RequestBody OrdersRequest ordersRequest) {
        OrdersResponse updatedOrder = ordersService.updateOrder(id, ordersRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    // Delete Order
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        ordersService.deleteOrder(id);
    }
}
