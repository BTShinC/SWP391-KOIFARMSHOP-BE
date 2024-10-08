package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.ShopCartRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ShopCartResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop-cart")
@CrossOrigin
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;

    // Thêm sản phẩm hoặc combo vào giỏ hàng
    @PostMapping("/add")
    public ResponseEntity<ShopCartResponse> addToCart(@RequestBody ShopCartRequest shopCartRequest) {
        ShopCartResponse shopCart = shopCartService.addToCart(shopCartRequest);
        return ResponseEntity.ok(shopCart);
    }

    // Lấy tất cả sản phẩm trong giỏ hàng của một tài khoản
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<ShopCartResponse>> getCartItems(@PathVariable String accountId) {
        List<ShopCartResponse> cartItems = shopCartService.getCartItems(accountId);
        return ResponseEntity.ok(cartItems);
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<ShopCartResponse> updateCartItem(
            @PathVariable String cartItemId,
            @RequestParam int quantity) {
        ShopCartResponse updatedItem = shopCartService.updateCartItem(cartItemId, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable String cartItemId) {
        shopCartService.removeCartItem(cartItemId);
        return ResponseEntity.ok("Item removed from cart successfully");
    }

    // Xóa tất cả sản phẩm trong giỏ hàng của một tài khoản
    @DeleteMapping("/clear/{accountId}")
    public ResponseEntity<String> clearCart(@PathVariable String accountId) {
        shopCartService.clearCart(accountId);
        return ResponseEntity.ok("Cart cleared successfully");
    }
}
