package com.codecool.shop.service;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartService {
    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<Product> getSelectedProducts(){
        return cartDao.getSelectedProducts();
    }
    public Map<Product,Integer> getGroupProducts(){
        return cartDao.getGroupProduct();
    }
    public BigDecimal getValue(){
        return cartDao.getValue();
    }
}
