package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.CarePackage;
import com.example.SWP391_KOIFARMSHOP_BE.repository.ICarePackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CarePackageService {
    @Autowired
    private ICarePackageRepository iCarePackageRepository;

    public List<CarePackage> getAllCarePackage() {
        return iCarePackageRepository.findAll();
    }


    public CarePackage insertCarePackage(CarePackage carePackage) {
        return iCarePackageRepository.save(carePackage);
    }


    public CarePackage updateCarePackage(long carePackageID, CarePackage carePackage) {
        CarePackage cp = iCarePackageRepository.getById(carePackageID);
        if(cp != null){
            cp.setPackageName(carePackage.getPackageName());
            cp.setDescription(carePackage.getDescription());
            cp.setPrice(carePackage.getPrice());
            cp.setDuration(carePackage.getDuration());
            cp.setFoodIntakePerDay(carePackage.getFoodIntakePerDay());
            return iCarePackageRepository.save(cp);
        }
        return null;
    }


    public void deleteCarePackage(long carePackageID) {
        iCarePackageRepository.deleteById(carePackageID);
    }


    public Optional<CarePackage> getCarePackageByID(long carePackageID) {
        return iCarePackageRepository.findById(carePackageID);
    }
}
