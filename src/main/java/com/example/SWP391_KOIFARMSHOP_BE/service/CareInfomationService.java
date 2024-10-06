package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.CareInfomation;
import com.example.SWP391_KOIFARMSHOP_BE.repository.ICareInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CareInfomationService{
    @Autowired
    private ICareInformationRepository iCareInformationRepository;

    public List<CareInfomation> getAllCareInfomation() {
        return iCareInformationRepository.findAll();
    }


    public CareInfomation insertCareInfomation(CareInfomation careInfomation) {
        return iCareInformationRepository.save(careInfomation);
    }


    public CareInfomation updateCareInfomation(long careInfomationID, CareInfomation careInfomation) {
        Optional<CareInfomation> optionalCareInfomation = iCareInformationRepository.findById(careInfomationID);
        if (optionalCareInfomation.isEmpty()) {
            throw new EntityNotFoundException("Care infomation not found");
        }

        CareInfomation ci = optionalCareInfomation.get();
            ci.setDateExpiration(careInfomation.getDateExpiration());
            ci.setDateReceived(careInfomation.getDateReceived());
            ci.setStatus(careInfomation.getStatus());
        return iCareInformationRepository.save(ci);
    }

    public void deleteCareInfomation(long careInfomationID) {
        Optional<CareInfomation> optionalCareInfomation = iCareInformationRepository.findById(careInfomationID);
        if (optionalCareInfomation.isEmpty()) {
            throw new EntityNotFoundException("Care infomation not found");
        }
        iCareInformationRepository.deleteById(careInfomationID);
    }


    public Optional<CareInfomation> getCareInfomationByID(long careInfomationID) {
        Optional<CareInfomation> optionalCareInfomation = iCareInformationRepository.findById(careInfomationID);
        if (optionalCareInfomation.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }
        return optionalCareInfomation;
    }
}
