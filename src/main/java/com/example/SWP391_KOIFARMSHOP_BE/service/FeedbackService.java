package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.model.FeedbackRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.FeedbackResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Feedback;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IFeedbackRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private IFeedbackRepository iFeedbackRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Lấy tất cả feedback
    public List<FeedbackResponse> getAllFeedback() {
        return iFeedbackRepository.findAll().stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackResponse.class))
                .collect(Collectors.toList());
    }

    // Tạo mới feedback
    public FeedbackResponse createFeedback(FeedbackRequest feedbackRequest) {
        String nextId = generateNextFeedbackId();
        Feedback feedback = modelMapper.map(feedbackRequest, Feedback.class);
        feedback.setFeedbackID(nextId);
        feedback.setOrders(null);
        Feedback saveFeedback = iFeedbackRepository.save(feedback);
        return  modelMapper.map(saveFeedback, FeedbackResponse.class);
    }

    private String generateNextFeedbackId() {
        Feedback lastFeedback = iFeedbackRepository.findTopByOrderByFeedbackIDDesc();
        if (lastFeedback != null) {
            String lastId = lastFeedback.getFeedbackID();
            int idNumber = Integer.parseInt(lastId.substring(1));
            String nextId = String.format("F%03d", idNumber + 1);
            return nextId;
        } else {
            return "F001";
        }
    }


    // Cập nhật feedback
    public FeedbackResponse updateFeedback(String feedbackID, FeedbackRequest feedbackRequest) {
        Feedback feedback = iFeedbackRepository.findById(feedbackID)
                .orElseThrow(() -> new RuntimeException("Feedback with ID " + feedbackID + " not found"));

        modelMapper.map(feedbackRequest, feedback);
        Feedback updatedFeedback = iFeedbackRepository.save(feedback);
        return modelMapper.map(updatedFeedback, FeedbackResponse.class);
    }

    // Xóa feedback
    public void deleteFeedback(String feedbackID) {
        Feedback feedback = iFeedbackRepository.findById(feedbackID)
                .orElseThrow(() -> new RuntimeException("Feedback with ID " + feedbackID + " not found"));
        iFeedbackRepository.delete(feedback);
    }

    // Lấy feedback theo ID và trả về dưới dạng FeedbackResponse
    public FeedbackResponse getFeedbackByID(String feedbackID) {
        Feedback feedback = iFeedbackRepository.findById(feedbackID)
                .orElseThrow(() -> new RuntimeException("Feedback with ID " + feedbackID + " not found"));
        return modelMapper.map(feedback, FeedbackResponse.class);
    }
}
