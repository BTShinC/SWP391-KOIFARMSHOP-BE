package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Consignment;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.OrdersDetail;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.TotalRevenue;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IConsignmentRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IOrdersDetailRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.ITotalRevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TotalRevenueService {

    @Autowired
    private IOrdersDetailRepository ordersDetailRepository;

    @Autowired
    private IConsignmentRepository consignmentRepository;

    @Autowired
    private ITotalRevenueRepository totalRevenueRepository;

    private String generateNextTotalRevenueID() {
        TotalRevenue lastTotalRevenue = totalRevenueRepository.findTopByOrderByTotalRevenueIDDesc();
        if (lastTotalRevenue != null) {
            String lastId = lastTotalRevenue.getTotalRevenueID();
            int idNumber = Integer.parseInt(lastId.substring(2));
            String nextId = String.format("TR%03d", idNumber + 1);
            return nextId;
        } else {
            return "TR001";
        }
    }

    public TotalRevenue calculateTotalRevenue(Date startDate, Date endDate) {
        double orderFarm = 0.0;
        double orderConsignment = 0.0;
        double careConsignment = 0.0;

        // Calculate orderFarm revenue for farm orders
        List<OrdersDetail> farmOrders = ordersDetailRepository.findCompletedOrdersByTypeAndDate("Trang trại", startDate, endDate);
        for (OrdersDetail orderDetail : farmOrders) {
            orderFarm += orderDetail.getDiscountedPrice();
        }

        // Calculate orderConsignment revenue for consignment orders
        List<OrdersDetail> consignmentOrders = ordersDetailRepository.findCompletedOrdersByTypeAndDate("Ký gửi", startDate, endDate);
        for (OrdersDetail orderDetail : consignmentOrders) {
            double consignmentRevenue = orderDetail.getPrice() * 0.2 - (orderDetail.getPrice() - orderDetail.getDiscountedPrice());
            orderConsignment += consignmentRevenue;
        }

        // Calculate careConsignment revenue for consignment care consignments
        List<Consignment> consignments = consignmentRepository.findByDateRange(startDate, endDate);
        for (Consignment consignment : consignments) {
            careConsignment += consignment.getTotal();
        }

        // Calculate total revenue and save to TotalRevenue entity
        double totalRevenue = orderFarm + orderConsignment + careConsignment;
        TotalRevenue revenue = new TotalRevenue();
        revenue.setTotalRevenueID(generateNextTotalRevenueID());
        revenue.setStartDate(startDate);
        revenue.setEndDate(endDate);
        revenue.setTotalRevenue(totalRevenue);

        return totalRevenueRepository.save(revenue);
    }
}
