package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.CareDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICareDetailRepository extends JpaRepository<CareDetail, String> {
    List<CareDetail> findByConsignment_ConsignmentID(String consignmentID);
    CareDetail findTopByOrderByCareDetailIDDesc();
}
