package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.SaleTransaction;
import com.example.SWP391_KOIFARMSHOP_BE.repository.ISaleTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SaleTransactionService{
    @Autowired
    private ISaleTransactionRepository iSaleTransactionRepository;

    public List<SaleTransaction> getAllSaleTransaction() {
        return iSaleTransactionRepository.findAll();
    }


    public SaleTransaction insertSaleTransaction(SaleTransaction saleTransaction) {
        return iSaleTransactionRepository.save(saleTransaction);
    }


    public SaleTransaction updateSaleTransaction(long saleTransactionID, SaleTransaction saleTransaction) {
        SaleTransaction st = iSaleTransactionRepository.getById(saleTransactionID);
        if(st != null){
            st.setStatus(saleTransaction.getStatus());
            st.setSaleDate(saleTransaction.getSaleDate());
            st.setSalePrice(saleTransaction.getSalePrice());
            return iSaleTransactionRepository.save(st);
        }
        return null;
    }


    public void deleteSaleTransaction(long saleTransactionID) {
        iSaleTransactionRepository.deleteById(saleTransactionID);
    }

   
    public Optional<SaleTransaction> getSaleTransactionByID(long saleTransactionID) {
        return iSaleTransactionRepository.findById(saleTransactionID);
    }
}
