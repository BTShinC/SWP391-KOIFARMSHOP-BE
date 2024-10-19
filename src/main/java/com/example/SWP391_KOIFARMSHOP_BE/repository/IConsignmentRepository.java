package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Consignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConsignmentRepository extends JpaRepository<Consignment, String> {
    Consignment findTopByOrderByConsignmentIDDesc();
}
