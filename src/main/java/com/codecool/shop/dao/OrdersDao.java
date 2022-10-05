package com.codecool.shop.dao;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import java.util.List;
import java.util.Map;

public interface OrdersDao {
    void setData(String user_id);

    void setProductsIdInOrder(int orderId);

    List<Integer> getProductsIdInOrder();


    List<Order> getAllOrderUser(int user_id);

    List<Order> getData();

    Product getProductFromId(int productId);

    List<Integer> getProductId(int orderId);

    List<Product> getAllProductsInOrder();

    Map<Integer,List<Product>> getMapProduct();
}
