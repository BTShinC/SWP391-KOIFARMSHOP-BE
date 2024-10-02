package com.example.SWP391_KOIFARMSHOP_BE.service;


import com.example.SWP391_KOIFARMSHOP_BE.pojo.SaleTransaction;

import java.util.List;
import java.util.Optional;

public interface ISaleTransactionService {
    public List<SaleTransaction> getAllSaleTransaction();
    public SaleTransaction insertSaleTransaction(SaleTransaction saleTransaction);
    public SaleTransaction updateSaleTransaction(long saleTransactionID, SaleTransaction saleTransaction);
    public void deleteSaleTransaction (long saleTransactionID);
    public Optional<SaleTransaction> getSaleTransactionByID(long saleTransactionID);
}
