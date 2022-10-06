package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.database.DataBaseManager;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoMem implements ProductDao {
    private List<Product> data;
    private static ProductDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    protected ProductDaoMem() {
    }


    public static ProductDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        product.setId(data.size() + 1);
        data.add(product);
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void setData() {
        data = getAll();
    }


    @Override
    public List<Product> getAll(){
        try(Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT id,name, price, currency, description,category_id,supplier_id FROM product";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            Product product;
            while(resultSet.next()){
                ProductCategory productCategory= ProductCategoryDaoMem.getInstance().find(resultSet.getInt(6));
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
                result.add(product);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return data.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return data.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }


}
