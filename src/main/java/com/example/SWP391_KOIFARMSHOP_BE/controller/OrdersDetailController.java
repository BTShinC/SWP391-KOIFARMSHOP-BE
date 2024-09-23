package com.example.SWP391_KOIFARMSHOP_BE.controller;


import com.example.SWP391_KOIFARMSHOP_BE.pojo.OrdersDetail;
import com.example.SWP391_KOIFARMSHOP_BE.service.IOrdersDetailService;
import com.example.SWP391_KOIFARMSHOP_BE.service.IOrdersService;
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
    private IOrdersDetailService iOrdersDetailService;
    @GetMapping("/")
    public ResponseEntity<List<OrdersDetail>> fetchALlOrdersDetail(){
        return ResponseEntity.ok(iOrdersDetailService.getAllOrdersDetail());
    }
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public OrdersDetail saveOrdersDetail(@org.springframework.web.bind.annotation.RequestBody OrdersDetail ordersDetail){
        return iOrdersDetailService.insertOrdersDetail(ordersDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdersDetail> updateOrdersDetail(@PathVariable long id, @RequestBody OrdersDetail ordersDetail){
        OrdersDetail updateOrdersDetail = iOrdersDetailService.updateOrdersDetail(id, ordersDetail);
        return ResponseEntity.ok(updateOrdersDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrdersDetail(@PathVariable long id){
        iOrdersDetailService.deleteOrdersDetail(id);
        return ResponseEntity.ok("Delete Order Detail success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrdersDetail>> getOrdersDetailByID(@PathVariable long id){
        Optional<OrdersDetail> ordersDetail = iOrdersDetailService.getOrdersDetailByID(id);
        return  ResponseEntity.ok(ordersDetail);
    }
}
