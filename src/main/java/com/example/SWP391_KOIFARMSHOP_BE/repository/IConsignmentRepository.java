package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Consignment;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IConsignmentRepository extends JpaRepository<Consignment, String> {
    Consignment findTopByOrderByConsignmentIDDesc();
    List<Consignment> findByAccountID(String accountId);
    Consignment findByconsignmentID(String consignmentID);
    // Lấy tất cả Consignment có ngày trong khoảng startDate và endDate
    @Query("SELECT c FROM Consignment c WHERE c.consignmentDate BETWEEN :startDate AND :endDate AND c.status IN :statuses")
    List<Consignment> findByDateRangeAndStatuses(Date startDate, Date endDate, List<String> statuses);

}
