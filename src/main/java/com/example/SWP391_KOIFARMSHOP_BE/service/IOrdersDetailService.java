package com.example.SWP391_KOIFARMSHOP_BE.service;



import com.example.SWP391_KOIFARMSHOP_BE.pojo.OrdersDetail;

import java.util.List;
import java.util.Optional;

public interface IOrdersDetailService {
    public List<OrdersDetail> getAllOrdersDetail();
    public OrdersDetail insertOrdersDetail(OrdersDetail ordersDetail);
    public OrdersDetail updateOrdersDetail(long ordersDetailID, OrdersDetail ordersDetail);
    public void deleteOrdersDetail (long ordersDetailID);
    public Optional<OrdersDetail> getOrdersDetailByID(long ordersDetailID);
}
