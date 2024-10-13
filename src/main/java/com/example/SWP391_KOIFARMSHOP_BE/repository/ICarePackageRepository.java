package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.CarePackage;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarePackageRepository extends JpaRepository<CarePackage, String> {
    CarePackage findTopByOrderByCarePackageIDDesc();
}
