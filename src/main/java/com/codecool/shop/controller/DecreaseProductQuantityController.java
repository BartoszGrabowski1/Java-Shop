package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "DecreaseProductQuantityServlet", urlPatterns = {"/decreaseProductQuantity"})
public class DecreaseProductQuantityController extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartDaoStore = CartDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoMem.getInstance();
        String id = req.getParameter("prod_id");
        int productId = Integer.parseInt(id);
        cartDaoStore.removeProduct(productDataStore.find(productId));

    }
}
