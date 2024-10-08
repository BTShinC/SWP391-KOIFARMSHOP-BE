package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IShopCartRepository extends JpaRepository<ShopCart, String> {
    ShopCart findTopByOrderByShopCartIDDesc();
    // Lấy tất cả sản phẩm trong giỏ hàng theo Account
    List<ShopCart> findByAccount_AccountID(String accountId);
}
