package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.TotalRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ITotalRevenueRepository extends JpaRepository<TotalRevenue, String> {

    // Lấy danh thu theo khoảng thời gian startDate và endDate
    List<TotalRevenue> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(Date startDate, Date endDate);


    // Lấy bản ghi TotalRevenue mới nhất, để tính toán và lưu tổng danh thu
    TotalRevenue findTopByOrderByTotalRevenueIDDesc();
}
