package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.OrderRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrderResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.OrdersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    // Create Order
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        OrderResponse newOrder = ordersService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    // Get All Orders
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Get Order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id) {
        OrderResponse order = ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // Update Order
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable String id, @Valid @RequestBody OrderRequest orderRequest) {
        OrderResponse updatedOrder = ordersService.updateOrder(id, orderRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    // Delete Order
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String id) {
        ordersService.deleteOrder(id);
    }

    // API để tạo Order với Product và ProductCombo
    @PostMapping("/makeOrder")
    public ResponseEntity<?> makeOrder(
            @RequestParam("accountId") String accountId,
            @RequestParam(value = "productIds", required = false) List<String> productIds,
            @RequestParam(value = "productComboIds", required = false) List<String> productComboIds) {

        // Nếu productIds hoặc productComboIds không có giá trị, gán chúng thành danh sách rỗng
        if (productIds == null) {
            productIds = new ArrayList<>();
        }
        if (productComboIds == null) {
            productComboIds = new ArrayList<>();
        }

        // Kiểm tra nếu cả hai đều rỗng
        if (productIds.isEmpty() && productComboIds.isEmpty()) {
            return ResponseEntity.badRequest().body("Order must contain at least one product or product combo.");
        }

        // Gọi service để xử lý tạo đơn hàng
        OrderResponse orderResponse = ordersService.createOrderWithMultipleProducts(accountId, productIds, productComboIds);
        return ResponseEntity.ok(orderResponse);
    }


}
