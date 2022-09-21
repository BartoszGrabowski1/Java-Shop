package com.codecool.shop.service;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class CartService {
    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<Product> getSelectedProducts(){
        return cartDao.getSelectedProducts();
    }
    public BigDecimal getValue(){
        return cartDao.getValue();
    }
}
