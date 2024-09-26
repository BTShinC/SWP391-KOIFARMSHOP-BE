package com.example.SWP391_KOIFARMSHOP_BE.service;



import com.example.SWP391_KOIFARMSHOP_BE.pojo.CareInfomation;

import java.util.List;
import java.util.Optional;

public interface ICareInfomationService {
    public List<CareInfomation> getAllCareInfomation();
    public CareInfomation insertCareInfomation(CareInfomation careInfomation);
    public CareInfomation updateCareInfomation(long careInfomationID, CareInfomation careInfomation);
    public void deleteCareInfomation (long careInfomationID);
    public Optional<CareInfomation> getCareInfomationByID(long careInfomationID);
}
