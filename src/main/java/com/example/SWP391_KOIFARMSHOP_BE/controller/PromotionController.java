package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.PromotionRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.PromotionResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    // Tạo mã khuyến mãi mới
    @PostMapping
    public ResponseEntity<PromotionResponse> createPromotion(@RequestBody PromotionRequest promotionRequest) {
        PromotionResponse promotionResponse = promotionService.createPromotion(promotionRequest);
        return ResponseEntity.ok(promotionResponse);
    }

    // Lấy tất cả mã khuyến mãi
    @GetMapping
    public ResponseEntity<List<PromotionResponse>> getAllPromotions() {
        List<PromotionResponse> promotions = promotionService.getAllPromotions();
        return ResponseEntity.ok(promotions);
    }

    // Lấy mã khuyến mãi theo ID
    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable String id) {
        PromotionResponse promotionResponse = promotionService.getPromotionById(id);
        return ResponseEntity.ok(promotionResponse);
    }

    // Cập nhật mã khuyến mãi
    @PutMapping("/{id}")
    public ResponseEntity<PromotionResponse> updatePromotion(@PathVariable String id,
                                                             @RequestBody PromotionRequest promotionRequest) {
        PromotionResponse updatedPromotion = promotionService.updatePromotion(id, promotionRequest);
        return ResponseEntity.ok(updatedPromotion);
    }

    // Xóa mã khuyến mãi
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable String id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
}
