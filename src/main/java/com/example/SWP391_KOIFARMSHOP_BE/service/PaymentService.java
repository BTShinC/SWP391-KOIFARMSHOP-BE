package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Payment;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PaymentService implements IPaymentService{
    @Autowired
    private IPaymentRepository iPaymentRepository;
    @Override
    public List<Payment> getAllPayment() {
        return iPaymentRepository.findAll();
    }

    @Override
    public Payment insertPayment(Payment payment) {
        return iPaymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(long paymentID, Payment payment) {
        Payment pm = iPaymentRepository.getById(paymentID);
        if(pm != null){
            pm.setSalePrice(payment.getSalePrice());
            pm.setShipPrice(payment.getShipPrice());
            pm.setSubTotal(payment.getSubTotal());
            return iPaymentRepository.save(pm);
        }
        return null;
    }

    @Override
    public void deletePayment(long paymentID) {
        iPaymentRepository.deleteById(paymentID);
    }

    @Override
    public Optional<Payment> getPaymentByID(long paymentID) {
        return iPaymentRepository.findById(paymentID);
    }
}
