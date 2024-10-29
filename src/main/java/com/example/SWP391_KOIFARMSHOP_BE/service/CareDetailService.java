package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.CareDetailRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.CareDetailResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.CareDetail;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Consignment;
import com.example.SWP391_KOIFARMSHOP_BE.repository.ICareDetailRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IConsignmentRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.PropertyMap;  // Đảm bảo import PropertyMap

@Service
public class CareDetailService {

    @Autowired
    private ICareDetailRepository careDetailRepository;

    @Autowired
    private IConsignmentRepository consignmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct  // Thêm cấu hình ánh xạ khi service khởi động
    public void setupMapper() {
        modelMapper.addMappings(new PropertyMap<CareDetail, CareDetailResponse>() {
            @Override
            protected void configure() {
                map().setConsignmentID(source.getConsignment().getConsignmentID());
            }
        });
    }

    // Tạo ID cho CareDetail
    private String generateNextCareDetailId() {
        CareDetail lastDetail = careDetailRepository.findTopByOrderByCareDetailIDDesc();
        if (lastDetail != null) {
            String lastId = lastDetail.getCareDetailID();
            int idNumber = Integer.parseInt(lastId.substring(2));
            return String.format("CD%03d", idNumber + 1);
        } else {
            return "CD001";
        }
    }

    // Tạo mới CareDetail
    public CareDetailResponse createCareDetail(CareDetailRequest request) {
        // Kiểm tra nếu consignment tồn tại và có consignmentType là "Chăm sóc"
        Consignment consignment = consignmentRepository.findById(request.getConsignmentID())
                .orElseThrow(() -> new EntityNotFoundException("Consignment với ID " + request.getConsignmentID() + " không tìm thấy"));

        if (!"Ký gửi chăm sóc".equalsIgnoreCase(consignment.getConsignmentType())) {
            throw new IllegalArgumentException("Consignment phải có consignmentType là 'Chăm sóc' để thêm CareDetails.");
        }

        // Khởi tạo CareDetail và ánh xạ các thuộc tính
        CareDetail careDetail = new CareDetail();
        careDetail.setCareDetailID(generateNextCareDetailId());
        careDetail.setDescription(request.getDescription());
        careDetail.setUpdateDate(request.getUpdateDate());
        careDetail.setImages(request.getImages());
        careDetail.setConsignment(consignment);

        CareDetail savedDetail = careDetailRepository.save(careDetail);
        return modelMapper.map(savedDetail, CareDetailResponse.class);
    }

    // Lấy CareDetails theo ConsignmentID
    public List<CareDetailResponse> getCareDetailsByConsignmentId(String consignmentID) {
        List<CareDetail> careDetails = careDetailRepository.findByConsignment_ConsignmentID(consignmentID);
        return careDetails.stream()
                .map(careDetail -> modelMapper.map(careDetail, CareDetailResponse.class))
                .collect(Collectors.toList());
    }
}
