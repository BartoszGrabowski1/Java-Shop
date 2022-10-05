package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrdersDao;
import com.codecool.shop.dao.database.DataBaseManager;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrdersDaoMem implements OrdersDao {
    private ProductDaoMem productDaoMem = ProductDaoMem.getInstance();
    private List<Order> data;
    private static OrdersDaoMem instance = null;

    public static OrdersDaoMem getInstance() {
        if (instance == null) {
            instance = new OrdersDaoMem();
        }
        return instance;
    }


    @Override
    public void setData(String user_id) {
        int id = Integer.parseInt(user_id);
        data = getAllOrderUser(id);
        takeListProducts();
    }

    @Override
    public List<Order> getData() {
        return data;
    }

    @Override
    public List<Order> getAllOrderUser(int user_id) {
        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT  id,ordered_at, status,user_id FROM public.order WHERE user_id='" + user_id + "'";
            ResultSet resultSetOrder = connection.createStatement().executeQuery(sql);
            List<Order> result = new ArrayList<>();
            Order order;
            List<Product> list = new ArrayList<>();
            while (resultSetOrder.next()) {
                order = new Order(resultSetOrder.getTimestamp(2), resultSetOrder.getString(3), resultSetOrder.getInt(4), list);
                order.setId(resultSetOrder.getInt(1));
                result.add(order);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void takeListProducts() {
        for (Order order : data) {
            order.setProductsList(getProductId(order.getId()));
        }
    }

    @Override
    public List<Product> getProductId(int orderId) {

        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT  product_id FROM ordered_products WHERE order_id='" + orderId + "'";
            ResultSet resultSetOrder = connection.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (resultSetOrder.next()) {
                result.add(productDaoMem.find(resultSetOrder.getInt(1)));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
