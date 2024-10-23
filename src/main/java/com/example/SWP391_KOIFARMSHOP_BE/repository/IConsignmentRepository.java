package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Consignment;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConsignmentRepository extends JpaRepository<Consignment, String> {
    Consignment findTopByOrderByConsignmentIDDesc();
    List<Consignment> findByAccountID(String accountId);
    Consignment findByconsignmentID(String consignmentID);
}
