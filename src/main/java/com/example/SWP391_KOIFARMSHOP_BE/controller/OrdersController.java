package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
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


    // Lấy ra tất cả order
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Lấy sản phảma theo orderid
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id) {
        OrderResponse order = ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    //Lấy order theo accounid
    @GetMapping("/account/{accountID}")
    public ResponseEntity<List<OrderResponse>> getOrdersByAccountId(@PathVariable String accountId) {
        List<OrderResponse> orders = ordersService.getOrdersByAccountId(accountId);
        return ResponseEntity.ok(orders);
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

    // Tạo order
    @PostMapping("/makeOrder")
    public ResponseEntity<?> makeOrder(
            @RequestParam("accountID") String accountId,
            @RequestParam(value = "productIDs", required = false) List<String> productIds,
            @RequestParam(value = "productComboIDs", required = false) List<String> productComboIds,
            @RequestParam(value = "promotionID", required = false) String promotionID) {

        // Nếu productIds hoặc productComboIds không có giá trị, gán chúng thành danh sách rỗng
        if (productIds == null) {
            productIds = new ArrayList<>();
        }
        if (productComboIds == null) {
            productComboIds = new ArrayList<>();
        }

        // Kiểm tra nếu cả hai danh sách đều rỗng
        if (productIds.isEmpty() && productComboIds.isEmpty()) {
            return ResponseEntity.badRequest().body("Order must contain at least one product or product combo.");
        }

        try {
            // Gọi service để xử lý tạo đơn hàng
            OrderResponse orderResponse = ordersService.createOrderWithMultipleProducts(accountId, productIds, productComboIds, promotionID);
            return ResponseEntity.ok(orderResponse);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order: " + ex.getMessage());
        }
    }

}
