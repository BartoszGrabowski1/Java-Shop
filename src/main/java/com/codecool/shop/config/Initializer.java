package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        Supplier apple = new Supplier("Apple", "Smartphone");
        Supplier asus = new Supplier("Asus", "Laptops");
        supplierDataStore.add(apple);
        supplierDataStore.add(amazon);
        supplierDataStore.add(lenovo);
        supplierDataStore.add(asus);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory phone = new ProductCategory("Phone", "Mobile-Phone", "Phone is good");
        ProductCategory laptop = new ProductCategory("Laptop", "SmallComputer", "Good laptops");
        productCategoryDataStore.add(tablet);
        productCategoryDataStore.add(phone);
        productCategoryDataStore.add(laptop);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", new BigDecimal("49.9"), "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", new BigDecimal("479"), "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", new BigDecimal("89"), "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Iphone 20 pro", new BigDecimal("999"), "USD", "Iphone 20 pro is goood really goood", phone, apple));
        productDataStore.add(new Product("Iphone 20 MAX", new BigDecimal("899"), "USD", "Iphone 20 pro is goood really goood too", phone, apple));
        productDataStore.add(new Product("Ultra Phone", new BigDecimal("499"), "USD", "This phone is good very good", phone, apple));
        productDataStore.add(new Product("Laptop lenovo 9000", new BigDecimal("1300"), "USD", "Very good laptop", laptop, lenovo));
        productDataStore.add(new Product("Laptop lenovo 3500", new BigDecimal("1000"), "USD", "Good laptop too", laptop, lenovo));
        productDataStore.add(new Product("Laptop asus 500", new BigDecimal("800"), "USD", "Good asus laptop too", laptop, asus));

    }
}
