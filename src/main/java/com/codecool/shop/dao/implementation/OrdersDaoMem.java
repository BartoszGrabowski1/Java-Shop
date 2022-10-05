package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrdersDao;
import com.codecool.shop.dao.database.DataBaseManager;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersDaoMem implements OrdersDao {

    private List<Order> data;
    private List<Integer> productsIdInOrder;
    private Map<Integer,List<Product>> mapProduct = new HashMap<>();
    private static OrdersDaoMem instance = null;

    public static OrdersDaoMem getInstance() {
        if (instance == null) {
            instance = new OrdersDaoMem();
        }
        return instance;
    }

    public void setMapProduct() {
        mapProduct = getMapProduct();
    }

    @Override
    public void setData(String user_id) {
        int id = Integer.parseInt(user_id);
        data = getAllOrderUser(id);
    }

    @Override
    public void setProductsIdInOrder(int orderId) {
        this.productsIdInOrder = getProductId(orderId);
    }

    @Override
    public List<Integer> getProductsIdInOrder() {
        return productsIdInOrder;
    }



    @Override
    public List<Order> getAllOrderUser(int user_id) {
        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT  id,ordered_at, status,user_id FROM public.order WHERE user_id='" + user_id + "'";
            ResultSet resultSetOrder = connection.createStatement().executeQuery(sql);
            List<Order> result = new ArrayList<>();
            Order order;
            while (resultSetOrder.next()) {
                order = new Order(resultSetOrder.getTimestamp(2), resultSetOrder.getString(3), resultSetOrder.getInt(4));
                order.setId(resultSetOrder.getInt(1));
                result.add(order);
                System.out.println("testOrder");
                System.out.println(order);
            }
            System.out.println("test result");
            System.out.println(result);
            return result;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getData() {
        return data;
    }

    @Override
    public Product getProductFromId(int productId) {
        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT id,name, price, currency, description,category_id,supplier_id FROM product WHERE id='" + productId + "'";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            Product product;
            ProductCategory productCategory = ProductCategoryDaoMem.getInstance().find(resultSet.getInt(6));
            productCategory.setId(resultSet.getInt(6));
            Supplier supplier = SupplierDaoMem.getInstance().find(resultSet.getInt(7));
            supplier.setId(resultSet.getInt(7));
            product = new Product(resultSet.getString(2),
                    resultSet.getBigDecimal(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    productCategory,
                    supplier);
            product.setId(resultSet.getInt(1));

            return product;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> getProductId(int orderId) {

        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT  product_id FROM ordered_products WHERE id='" + orderId + "'";
            ResultSet resultSetOrder = connection.createStatement().executeQuery(sql);
            List<Integer> result = new ArrayList<>();
            int id;
            while (resultSetOrder.next()) {
                id = resultSetOrder.getInt(1);
                result.add(id);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAllProductsInOrder() {
        List<Product> result = new ArrayList<>();
        for (int i = 0; i < getProductId(i).size(); i++) {
            result.add(getProductFromId(getProductId(i).get(i)));
        }
        return result;
    }
    @Override
    public  Map<Integer,List<Product>> getMapProduct(){
        mapProduct.clear();
        for (Order order : data) {
            mapProduct.put(order.getId(), getAllProductsInOrder());
        }
        return mapProduct;
    }
}
