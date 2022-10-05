package com.codecool.shop.service;

import com.codecool.shop.dao.OrdersDao;
import com.codecool.shop.model.Order;

import java.util.List;

public class OrdersService {

    private OrdersDao orderDao;

    public OrdersService(OrdersDao orderDao) {
        this.orderDao = orderDao;
    }
    public List<Order> getOrders(){
        return orderDao.getData();
    }
}
