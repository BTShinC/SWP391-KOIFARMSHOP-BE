package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.CarePackageRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.CarePackageResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.CarePackage;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.repository.ICarePackageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarePackageService {

    @Autowired
    private ICarePackageRepository carePackageRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Tạo gói chăm sóc mới
    public CarePackageResponse createCarePackage(CarePackageRequest carePackageRequest) {
        String nextId = generateNextCarePackageID();
        CarePackage carePackage = modelMapper.map(carePackageRequest, CarePackage.class);
        carePackage.setCarePackageID(nextId);

        // Chuyển đổi danh sách thành JSON
        carePackage.setImagesFromList(carePackageRequest.getImages());
        carePackage.setServicesFromList(carePackageRequest.getServices());

        CarePackage savedCarePackage = carePackageRepository.save(carePackage);
        return modelMapper.map(savedCarePackage, CarePackageResponse.class);
    }

    private String generateNextCarePackageID() {
        CarePackage lastCarePackage = carePackageRepository.findTopByOrderByCarePackageIDDesc();
        if (lastCarePackage != null) {
            String lastId = lastCarePackage.getCarePackageID();
            int idNumber = Integer.parseInt(lastId.substring(2));
            String nextId = String.format("CP%03d", idNumber + 1);
            return nextId;
        } else {
            return "CP001";
        }
    }

    // Lấy tất cả các gói chăm sóc
    public List<CarePackageResponse> getAllCarePackages() {
        List<CarePackage> carePackages = carePackageRepository.findAll();
        return carePackages.stream()
                .map(carePackage -> modelMapper.map(carePackage, CarePackageResponse.class))
                .collect(Collectors.toList());
    }

    // Lấy gói chăm sóc theo ID
    public CarePackageResponse getCarePackageById(String carePackageId) {
        CarePackage carePackage = carePackageRepository.findById(carePackageId)
                .orElseThrow(() -> new EntityNotFoundException("Care package with ID " + carePackageId + " not found"));
        return modelMapper.map(carePackage, CarePackageResponse.class);
    }

    // Cập nhật gói chăm sóc
    public CarePackageResponse updateCarePackage(String carePackageId, CarePackageRequest carePackageRequest) {
        CarePackage existingCarePackage = carePackageRepository.findById(carePackageId)
                .orElseThrow(() -> new EntityNotFoundException("Care package with ID " + carePackageId + " not found"));

        modelMapper.map(carePackageRequest, existingCarePackage);

        // Cập nhật danh sách thành JSON
        existingCarePackage.setImagesFromList(carePackageRequest.getImages());
        existingCarePackage.setServicesFromList(carePackageRequest.getServices());

        CarePackage updatedCarePackage = carePackageRepository.save(existingCarePackage);
        return modelMapper.map(updatedCarePackage, CarePackageResponse.class);
    }

    // Xóa gói chăm sóc
    public void deleteCarePackage(String carePackageId) {
        CarePackage carePackage = carePackageRepository.findById(carePackageId)
                .orElseThrow(() -> new EntityNotFoundException("Care package with ID " + carePackageId + " not found"));
        carePackageRepository.delete(carePackage);
    }
}
