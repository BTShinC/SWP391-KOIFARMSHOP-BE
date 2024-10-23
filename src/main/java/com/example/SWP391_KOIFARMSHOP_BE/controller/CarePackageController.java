package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.CarePackageRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.CarePackageResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.CarePackageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carePackages")
public class CarePackageController {

    @Autowired
    private CarePackageService carePackageService;

    // Tạo gói chăm sóc mới
    @PostMapping
    public ResponseEntity<CarePackageResponse> createCarePackage(@Valid @RequestBody CarePackageRequest carePackageRequest) {
        CarePackageResponse newCarePackage = carePackageService.createCarePackage(carePackageRequest);
        return ResponseEntity.ok(newCarePackage);
    }

    // Lấy tất cả các gói chăm sóc
    @GetMapping
    public ResponseEntity<List<CarePackageResponse>> getAllCarePackages() {
        List<CarePackageResponse> carePackages = carePackageService.getAllCarePackages();
        return ResponseEntity.ok(carePackages);
    }

    // Lấy gói chăm sóc theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CarePackageResponse> getCarePackageById(@PathVariable String id) {
        CarePackageResponse carePackage = carePackageService.getCarePackageById(id);
        return ResponseEntity.ok(carePackage);
    }

    // Cập nhật gói chăm sóc
    @PutMapping("/{id}")
    public ResponseEntity<CarePackageResponse> updateCarePackage(@PathVariable String id, @Valid @RequestBody CarePackageRequest carePackageRequest) {
        CarePackageResponse updatedCarePackage = carePackageService.updateCarePackage(id, carePackageRequest);
        return ResponseEntity.ok(updatedCarePackage);
    }

    // Xóa gói chăm sóc
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarePackage(@PathVariable String id) {
        carePackageService.deleteCarePackage(id);
        return ResponseEntity.ok("Care package deleted successfully");
    }

}
