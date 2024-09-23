package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrdersService implements IOrdersService{
    @Autowired
    private IOrdersRepository iOrdersRepository;
    @Override
    public List<Orders> getAllOrders() {
        return iOrdersRepository.findAll();
    }

    @Override
    public Orders insertOrders(Orders orders) {
        return iOrdersRepository.save(orders);
    }

    @Override
    public Orders updateOrders(long ordersID, Orders orders) {
        Orders o = iOrdersRepository.getById(ordersID);
        if(o != null){
            o.setStatus(orders.getStatus());
            o.setTotal(orders.getTotal());
            o.setDate(orders.getDate());
            o.setDescription(orders.getDescription());
            return iOrdersRepository.save(o);
        }
        return null;
    }

    @Override
    public void deleteOrders(long ordersID) {
        iOrdersRepository.deleteById(ordersID);
    }

    @Override
    public Optional<Orders> getOrdersByID(long ordersID) {
        return iOrdersRepository.findById(ordersID);
    }
}
