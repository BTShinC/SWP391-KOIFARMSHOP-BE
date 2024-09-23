package com.example.SWP391_KOIFARMSHOP_BE.service;



import com.example.SWP391_KOIFARMSHOP_BE.pojo.Consignment;

import java.util.List;
import java.util.Optional;

public interface IConsignmentService {
    public List<Consignment> getAllConsignment();
    public Consignment insertConsignment(Consignment consignment);
    public Consignment updateConsignment(long consignmentID, Consignment consignment);
    public void deleteConsignment (long consignmentID);
    public Optional<Consignment> getConsignmentByID(long consignmentID);
}
