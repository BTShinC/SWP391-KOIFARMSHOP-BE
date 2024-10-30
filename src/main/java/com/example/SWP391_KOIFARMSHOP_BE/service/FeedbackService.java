package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.model.FeedbackRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.FeedbackResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrderResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Feedback;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IFeedbackRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IOrdersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    @Autowired
    private IFeedbackRepository feedbackRepository;

    @Autowired
    private IOrdersRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    IAccountRepository iAccountRepository;

    private  String generateFeedbackID() {
        Feedback lastfeedback = feedbackRepository.findTopByOrderByFeedbackIDDesc();
        if (lastfeedback != null) {
            String lastId = lastfeedback.getFeedbackID();
            int idNumber = Integer.parseInt(lastId.substring(1));
            String nextId = String.format("F%03d", idNumber + 1);
            return nextId;
        } else {
            return "F001";
        }
    }
    public FeedbackResponse createFeedback(FeedbackRequest feedbackRequest) {
        // Kiểm tra đơn hàng có tồn tại không
        Optional<Orders> order = orderRepository.findById(feedbackRequest.getOrderID());
        if (!order.isPresent()) {
            throw new EntityNotFoundException("Order not found with ID: " + feedbackRequest.getOrderID());
        }

        // Kiểm tra accountID trong yêu cầu có khớp với accountID của đơn hàng hay không
        if (!order.get().getAccount().getAccountID().equals(feedbackRequest.getAccountID())) {
            throw new IllegalArgumentException("Account ID does not match with the order's account.");
        }

        System.out.println("Description in FeedbackRequest: " + feedbackRequest.getDescription());

        // Tạo mới feedback
        Feedback feedback = new Feedback();
        feedback.setFeedbackID(generateFeedbackID());
        feedback.setDescription(feedbackRequest.getDescription());

        feedback.setOrder(order.get());
        feedback.setImage(feedbackRequest.getImage());

        feedbackRepository.save(feedback);

        // Trả về FeedbackResponse
        FeedbackResponse response = new FeedbackResponse();
        response.setFeedbackID(feedback.getFeedbackID());
        response.setDescription(feedback.getDescription());
        response.setImage(feedback.getImage());
        return response;

    }

    public List<FeedbackResponse> getAllFeedback() {
        return feedbackRepository.findAll().stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackResponse.class))
                .collect(Collectors.toList());
    }

    // Phương thức mới: Lấy feedback thông qua orderID
    public FeedbackResponse getFeedbackByOrderID(String orderID) {
        // Kiểm tra đơn hàng có tồn tại không
        Optional<Orders> order = orderRepository.findById(orderID);
        if (!order.isPresent()) {
            throw new EntityNotFoundException("Order not found with ID: " + orderID);
        }
        Account account = order.get().getAccount();
        if (account == null) {
            throw new EntityNotFoundException("Account not found for order ID: " + orderID);
        }
        String accountID = account.getAccountID();
        // Tìm Feedback liên quan đến Order
        Feedback feedback = feedbackRepository.findByOrder(order.get())
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found for order ID: " + orderID));

        // Chuyển đổi Feedback thành FeedbackResponse
        FeedbackResponse response = new FeedbackResponse();
        response.setFeedbackID(feedback.getFeedbackID());
        response.setDescription(feedback.getDescription());
        response.setImage(feedback.getImage());
        response.setOrderID(orderID);
        response.setAccountID(accountID);

        return response;
    }


}


