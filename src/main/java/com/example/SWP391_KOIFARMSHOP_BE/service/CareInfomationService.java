package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.CareInfomation;
import com.example.SWP391_KOIFARMSHOP_BE.repository.ICareInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CareInfomationService implements ICareInfomationService{
    @Autowired
    private ICareInformationRepository iCareInformationRepository;
    @Override
    public List<CareInfomation> getAllCareInfomation() {
        return iCareInformationRepository.findAll();
    }

    @Override
    public CareInfomation insertCareInfomation(CareInfomation careInfomation) {
        return iCareInformationRepository.save(careInfomation);
    }

    @Override
    public CareInfomation updateCareInfomation(long careInfomationID, CareInfomation careInfomation) {
        CareInfomation ci = iCareInformationRepository.getById(careInfomationID);
        if (ci != null) {
            ci.setDateExpiration(careInfomation.getDateExpiration());
            ci.setDateReceived(careInfomation.getDateReceived());
            ci.setStatus(careInfomation.getStatus());
            return  iCareInformationRepository.save(ci);
        }
        return null;
    }
    @Override
    public void deleteCareInfomation(long careInfomationID) {
        iCareInformationRepository.deleteById(careInfomationID);
    }

    @Override
    public Optional<CareInfomation> getCareInfomationByID(long careInfomationID) {
        return iCareInformationRepository.findById(careInfomationID);
    }
}
