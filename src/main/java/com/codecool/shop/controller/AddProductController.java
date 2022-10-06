package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.database.DataBaseManager;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;

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


@WebServlet(name = "AddToCartServlet", urlPatterns = {"/add"})
public class AddProductController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String id = req.getParameter("prod_id");
        int productId = Integer.parseInt(id);
        resp.sendRedirect("/");
        String idUser = httpSession.getAttribute("userId").toString();
        saveProductFromCartToDB(productId, Integer.parseInt(idUser));
    }

    void saveProductFromCartToDB(int productId, int userId){
        PreparedStatement preparedStatement = null;
        try(Connection connection = DataBaseManager.dataSource.getConnection())  {
            String sql = "INSERT INTO cart (product_id, user_id) VALUES (?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
//            ResultSet resultSet = connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
