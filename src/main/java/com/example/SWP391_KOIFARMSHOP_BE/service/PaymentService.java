package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.model.FeedbackRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.FeedbackResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.PaymentRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.PaymentResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Feedback;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Payment;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IPaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private IPaymentRepository iPaymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Lấy tất cả Payment
    public List<PaymentResponse> getAllPayments() {
        return iPaymentRepository.findAll().stream()
                .map(payment -> modelMapper.map(payment, PaymentResponse.class))
                .collect(Collectors.toList());
    }

    // Tạo mới Payment
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        String nextId = generateNextPaymentId();
        Payment payment = modelMapper.map(paymentRequest, Payment.class);
        payment.setPaymentID(nextId);
        payment.setOrders(null);
        Payment savePayment = iPaymentRepository.save(payment);
        return  modelMapper.map(savePayment, PaymentResponse.class);
    }

    private String generateNextPaymentId() {
        Payment lastPayment = iPaymentRepository.findTopByOrderByPaymentIDDesc();
        if (lastPayment != null) {
            String lastId = lastPayment.getPaymentID();
            int idNumber = Integer.parseInt(lastId.substring(2));
            String nextId = String.format("PM%03d", idNumber + 1);
            return nextId;
        } else {
            return "PM001";
        }
    }

    // Cập nhật Payment
    public PaymentResponse updatePayment(String paymentID, PaymentRequest paymentRequest) {
        Payment payment = iPaymentRepository.findById(paymentID)
                .orElseThrow(() -> new RuntimeException("Payment with ID " + paymentID + " not found"));

        modelMapper.map(paymentRequest, payment);
        Payment updatedPayment = iPaymentRepository.save(payment);
        return modelMapper.map(updatedPayment, PaymentResponse.class);
    }

    // Xóa Payment
    public void deletePayment(String paymentID) {
        Payment payment = iPaymentRepository.findById(paymentID)
                .orElseThrow(() -> new RuntimeException("Payment with ID " + paymentID + " not found"));
        iPaymentRepository.delete(payment);
    }

    // Lấy Payment theo ID
    public PaymentResponse getPaymentByID(String paymentID) {
        Payment payment = iPaymentRepository.findById(paymentID)
                .orElseThrow(() -> new RuntimeException("Payment with ID " + paymentID + " not found"));
        return modelMapper.map(payment, PaymentResponse.class);
    }
}
