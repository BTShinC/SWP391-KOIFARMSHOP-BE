package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import com.example.SWP391_KOIFARMSHOP_BE.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/oders")
public class OrdersController {
    @Autowired
    private IOrdersService iOrdersService;
    @GetMapping("/")
    public ResponseEntity<List<Orders>> fetchALlOrders(){
        return ResponseEntity.ok(iOrdersService.getAllOrders());
    }
    @PostMapping("/")
    @ResponseStatus (HttpStatus.CREATED)
    public Orders saveOrders(@org.springframework.web.bind.annotation.RequestBody Orders orders){
        return iOrdersService.insertOrders(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrders(@PathVariable long id, @RequestBody Orders orders){
        Orders updateOrders = iOrdersService.updateOrders(id, orders);
        return ResponseEntity.ok(updateOrders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrders(@PathVariable long id){
        iOrdersService.deleteOrders(id);
        return ResponseEntity.ok("Delete Order success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Orders>> getOrdersByID(@PathVariable long id){
        Optional<Orders> orders = iOrdersService.getOrdersByID(id);
        return  ResponseEntity.ok(orders);
    }
}
