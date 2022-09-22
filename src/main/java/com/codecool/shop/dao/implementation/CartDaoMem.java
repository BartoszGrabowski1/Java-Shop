package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartDaoMem implements CartDao {
    private List<Product> data = new ArrayList<>();
    private Map<Product, Integer> groupProduct = new HashMap<>();


    private static CartDaoMem instance = null;

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public Map<Product, Integer> getGroupProduct() {
        groupProduct.clear();
        for (Product selectedProduct : data) {
            groupProduct.put(selectedProduct, groupProduct.getOrDefault(selectedProduct, 0) + 1);
        }
        return groupProduct;
    }

    @Override
    public void addProduct(Product product) {
        data.add(product);

    }


    @Override
    public BigDecimal getValue() {
        return data.stream()
                .map(Product::getDefaultPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }


    @Override
    public void removeProduct(Product product) {
        for (Product selectedProduct : data) {
            if (selectedProduct.getName().equals(product.getName())) {
                data.remove(selectedProduct);
            }
        }
    }

    @Override
    public void increaseProductQuantity(Product product) {
        for (Product selectedProduct : data) {
            if (selectedProduct.getName().equals(product.getName())) {
                data.add(new Product(
                        selectedProduct.getName()
                        , selectedProduct.getDefaultPrice()
                        , selectedProduct.getDefaultCurrency().toString()
                        , selectedProduct.getDescription()
                        , selectedProduct.getProductCategory()
                        , selectedProduct.getSupplier()));
            }
        }
    }

    @Override
    public void decreaseProductQuantity(Product product) {
        for (Product selectedProduct : data) {
            if (selectedProduct.getName().equals(product.getName())) {
                data.remove(selectedProduct);
                return;
            }
        }
    }


    @Override
    public List<Product> getSelectedProducts() {
        return data;
    }
}
