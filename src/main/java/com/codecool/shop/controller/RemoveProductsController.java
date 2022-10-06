package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.database.DataBaseManager;
import com.codecool.shop.dao.implementation.CartDaoMem;

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

import static java.lang.Integer.parseInt;

@WebServlet(name = "RemoveCart", urlPatterns = {"/removeAll"})
public class RemoveProductsController extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CartDao cartDaoStore = CartDaoMem.getInstance();
        String name = req.getParameter("name");
        int numberOfItemsToDelete = cartDaoStore.getNumberOfItemsWithGivenName(name);
        session.setAttribute("cartSize",parseInt(session.getAttribute("cartSize").toString())-numberOfItemsToDelete);
        cartDaoStore.removeProductsByGivenName(name);
        deleteAllProductsOfGivenID(getIdByGivenName(name));


    }
    void deleteAllProductsOfGivenID(int prodID) {
        try (Connection conn = DataBaseManager.dataSource.getConnection()) {
            String sql = "DELETE FROM cart WHERE product_id="+prodID;
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    int getIdByGivenName(String prodName){
        try (Connection conn = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT id FROM product WHERE name=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,prodName);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
