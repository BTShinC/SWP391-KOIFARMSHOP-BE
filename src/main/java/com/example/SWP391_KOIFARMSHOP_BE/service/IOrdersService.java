package com.example.SWP391_KOIFARMSHOP_BE.service;



import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;

import java.util.List;
import java.util.Optional;

public interface IOrdersService {
    public List<Orders> getAllOrders();
    public Orders insertOrders(Orders orders);
    public Orders updateOrders(long ordersID, Orders orders);
    public void deleteOrders (long ordersID);
    public Optional<Orders> getOrdersByID(long ordersID);
}
