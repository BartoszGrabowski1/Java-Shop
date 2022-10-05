package com.codecool.shop.dao.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseManager {

    SupplierDaoMem supplierDao;
    ProductDao productDao;
    ProductCategoryDao productCategoryDao;

    public static DataSource dataSource;

    public DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        Properties connectionProps = Property.loadProperties();
        dataSource.setDatabaseName(connectionProps.getProperty("database"));
        dataSource.setUser(connectionProps.getProperty("user"));
//        dataSource.setURL(connectionProps.getProperty("url"));
        dataSource.setPassword(connectionProps.getProperty("password"));
        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");
        return dataSource;

    }

    public void setup() throws SQLException, IOException {
        try {
            dataSource = connect();
            productDao = ProductDaoMem.getInstance();
            supplierDao = SupplierDaoMem.getInstance();
            productCategoryDao = ProductCategoryDaoMem.getInstance();
            productCategoryDao.setData();
            supplierDao.setData();
            productDao.setData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
