package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.database.DataBaseManager;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoMem implements ProductCategoryDao {

    private List<ProductCategory> data;
    private static ProductCategoryDaoMem instance = null;


    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoMem() {

    }
    @Override
    public void setData() {
        this.data = getAll();
    }
    public static ProductCategoryDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoMem();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        category.setId(data.size() + 1);
        data.add(category);
    }

    @Override
    public ProductCategory find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<ProductCategory> getAll() {
        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT id,name,department,description FROM category";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            List<ProductCategory> result = new ArrayList<>();
            ProductCategory productCategory;
            while (resultSet.next()) {
                productCategory = new ProductCategory(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                productCategory.setId(resultSet.getInt(1));
                result.add(productCategory);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
