package com.example.SWP391_KOIFARMSHOP_BE.service;


import com.example.SWP391_KOIFARMSHOP_BE.pojo.Payment;

import java.util.List;
import java.util.Optional;

public interface IPaymentService {
    public List<Payment> getAllPayment();
    public Payment insertPayment(Payment payment);
    public Payment updatePayment(long paymentID, Payment payment);
    public void deletePayment (long paymentID);
    public Optional<Payment> getPaymentByID(long paymentID);
}
