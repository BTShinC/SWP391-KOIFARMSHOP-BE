package com.example.SWP391_KOIFARMSHOP_BE.service;



import com.example.SWP391_KOIFARMSHOP_BE.pojo.CarePackage;

import java.util.List;
import java.util.Optional;

public interface ICarePackageService{
    public List<CarePackage> getAllCarePackage();
    public CarePackage insertCarePackage(CarePackage carePackage);
    public CarePackage updateCarePackage(long carePackageID, CarePackage carePackage);
    public void deleteCarePackage (long carePackageID);
    public Optional<CarePackage> getCarePackageByID(long carePackageID);
}
