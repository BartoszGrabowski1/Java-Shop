package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CartDao {

    void addProduct(Product product);

    void clearData();

    BigDecimal getValue();

    void removeProduct(Product product);

    void removeProductsByGivenName(String name);

    void increaseProductQuantity(Product product);

    void decreaseProductQuantity(Product product);

    Map<Product, Integer> getGroupProduct();

    List<Product> getSelectedProducts();

    int getNumberOfItemsWithGivenName(String name);

}
