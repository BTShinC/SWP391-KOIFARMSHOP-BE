package com.example.SWP391_KOIFARMSHOP_BE.controller;


import com.example.SWP391_KOIFARMSHOP_BE.pojo.OrdersDetail;
import com.example.SWP391_KOIFARMSHOP_BE.service.OrdersDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin
@RequestMapping("/api/ordersDetail")
public class OrdersDetailController {
    @Autowired
    OrdersDetailService ordersDetailService;
    @GetMapping("/")
    public ResponseEntity<List<OrdersDetail>> fetchALlOrdersDetail(){
        return ResponseEntity.ok(ordersDetailService.getAllOrdersDetail());
    }
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public OrdersDetail saveOrdersDetail(@RequestBody OrdersDetail ordersDetail){
        return ordersDetailService.insertOrdersDetail(ordersDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdersDetail> updateOrdersDetail(@PathVariable String id, @RequestBody OrdersDetail ordersDetail){
        OrdersDetail updateOrdersDetail = ordersDetailService.updateOrdersDetail(id, ordersDetail);
        return ResponseEntity.ok(updateOrdersDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrdersDetail(@PathVariable String id){
        ordersDetailService.deleteOrdersDetail(id);
        return ResponseEntity.ok("Delete Order Detail success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrdersDetail>> getOrdersDetailByID(@PathVariable String id){
        Optional<OrdersDetail> ordersDetail = ordersDetailService.getOrdersDetailByID(id);
        return  ResponseEntity.ok(ordersDetail);
    }
}
