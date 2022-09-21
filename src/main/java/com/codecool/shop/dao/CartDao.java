package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CartDao {

    void addProduct(Product product);
    BigDecimal getValue();
    void removeProduct(Product product);
    void increaseProductQuantity(Product product);
    void decreaseProductQuantity(Product product);

    List<Product> getSelectedProducts();

}
