package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.PromotionRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.PromotionResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Promotion;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IPromotionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    @Autowired
    private IPromotionRepository promotionRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Tạo mã khuyến mãi mới
    public PromotionResponse createPromotion(PromotionRequest promotionRequest) {
        Promotion promotion = new Promotion();
        promotion.setPromotionID(generateNextPromotionId());
        promotion.setDiscountValue(promotionRequest.getDiscountValue());
        promotion.setDescription(promotionRequest.getDescription());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());

        Promotion savedPromotion = promotionRepository.save(promotion);
        return modelMapper.map(savedPromotion, PromotionResponse.class);
    }

    // Lấy tất cả các mã khuyến mãi
    public List<PromotionResponse> getAllPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions.stream()
                .map(promotion -> modelMapper.map(promotion, PromotionResponse.class))
                .collect(Collectors.toList());
    }

    // Lấy mã khuyến mãi theo ID
    public PromotionResponse getPromotionById(String promotionID) {
        Promotion promotion = promotionRepository.findById(promotionID)
                .orElseThrow(() -> new EntityNotFoundException("Promotion with ID " + promotionID + " not found"));
        return modelMapper.map(promotion, PromotionResponse.class);
    }

    // Cập nhật mã khuyến mãi
    public PromotionResponse updatePromotion(String promotionID, PromotionRequest promotionRequest) {
        Promotion existingPromotion = promotionRepository.findById(promotionID)
                .orElseThrow(() -> new EntityNotFoundException("Promotion with ID " + promotionID + " not found"));

        existingPromotion.setDiscountValue(promotionRequest.getDiscountValue());
        existingPromotion.setDescription(promotionRequest.getDescription());
        existingPromotion.setStartDate(promotionRequest.getStartDate());
        existingPromotion.setEndDate(promotionRequest.getEndDate());

        Promotion updatedPromotion = promotionRepository.save(existingPromotion);
        return modelMapper.map(updatedPromotion, PromotionResponse.class);
    }

    // Xóa mã khuyến mãi
    public void deletePromotion(String promotionID) {
        Promotion promotion = promotionRepository.findById(promotionID)
                .orElseThrow(() -> new EntityNotFoundException("Promotion with ID " + promotionID + " not found"));
        promotionRepository.delete(promotion);
    }

    // Sinh ID tiếp theo cho mã khuyến mãi
    private String generateNextPromotionId() {
        Promotion lastPromotion = promotionRepository.findTopByOrderByPromotionIDDesc();
        if (lastPromotion != null) {
            String lastId = lastPromotion.getPromotionID();
            int idNumber = Integer.parseInt(lastId.substring(2));  // Bỏ ký tự đầu tiên
            return String.format("PM%03d", idNumber + 1);  // Sinh mã mới dạng "Pxxx"
        } else {
            return "PM001";  // Mã khuyến mãi đầu tiên
        }
    }
}
