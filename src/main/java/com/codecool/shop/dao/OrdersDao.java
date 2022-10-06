package com.codecool.shop.dao;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import java.util.List;


public interface OrdersDao {
    void setData(String user_id);
    List<Order> getAllOrderUser(int user_id);

    List<Order> getData();

    void takeListProducts();

    List<Product> getProductId(int orderId);
}
