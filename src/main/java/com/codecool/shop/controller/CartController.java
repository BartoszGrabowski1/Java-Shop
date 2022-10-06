package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.database.DataBaseManager;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.CartService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    CartDao cartDaoStore = CartDaoMem.getInstance();
    ProductDao productDataStore = ProductDaoMem.getInstance();
    CartService cartService = new CartService(cartDaoStore);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        loadCartFromDB(1);
        HttpSession session = req.getSession();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("cart", cartService.getSelectedProducts());
        context.setVariable("value", cartService.getValue());
        context.setVariable("order", cartService.getGroupProducts());
        context.setVariable("name", session.getAttribute("name"));

        engine.process("cart/cart.html", context, resp.getWriter());
    }

    void loadCartFromDB(int userId){
        if (checkIfSomethingInCartChange(userId)){
            try(Connection connection = DataBaseManager.dataSource.getConnection())  {
                String sql = "SELECT c.product_id, p.name, p.price FROM cart c, product p WHERE c.user_id =" + userId + " AND p.id = c.product_id";
                ResultSet resultSet = connection.createStatement().executeQuery(sql);
                while(resultSet.next()){
                    cartDaoStore.addProduct(productDataStore.find(resultSet.getInt(1)));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    boolean checkIfSomethingInCartChange(int userId){
        List<Product> cartDB = new ArrayList<>();

        try(Connection connection = DataBaseManager.dataSource.getConnection())  {
            String sql = "SELECT c.product_id FROM cart c WHERE c.user_id =" + userId;
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while(resultSet.next()){
                cartDB.add(productDataStore.find(resultSet.getInt(1)));
            }
            if (cartDB.size() == cartDaoStore.getSelectedProducts().size()){
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cartDaoStore.clearData();
        return true;
    }


}
