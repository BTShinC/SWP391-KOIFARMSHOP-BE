package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Consignment;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IConsignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ConsignmentService implements IConsignmentService{
    @Autowired
    private IConsignmentRepository iConsignmentRepository;
    @Override
    public List<Consignment> getAllConsignment() {
        return iConsignmentRepository.findAll();
    }

    @Override
    public Consignment insertConsignment(Consignment consignment) {
        return iConsignmentRepository.save(consignment);
    }

    @Override
    public Consignment updateConsignment(long consignmentID, Consignment consignment) {
        Consignment c = iConsignmentRepository.getById(consignmentID);
        if(c != null){
            c.setConsignmentDate(consignment.getConsignmentDate());
            c.setStatus(consignment.getStatus());
            return iConsignmentRepository.save(c);
        }
        return null;
    }

    @Override
    public void deleteConsignment(long consignmentID) {
        iConsignmentRepository.deleteById(consignmentID);
    }

    @Override
    public Optional<Consignment> getConsignmentByID(long consignmentID) {
        return iConsignmentRepository.findById(consignmentID);
    }
}
