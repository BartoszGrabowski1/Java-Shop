package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.database.DataBaseManager;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;

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


@WebServlet(name = "DecreaseProductQuantityServlet", urlPatterns = {"/decreaseProductQuantity"})
public class DecreaseProductQuantityController extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("cartSize", parseInt(session.getAttribute("cartSize").toString())-1);
        CartDao cartDaoStore = CartDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoMem.getInstance();
        String id = req.getParameter("prod_id");
        int productId = parseInt(id);
        cartDaoStore.removeProduct(productDataStore.find(productId));
        System.out.println(productId);
        System.out.println(getID(productId));
        deleteSingleProductFromDb(getID(productId));

    }

    void deleteSingleProductFromDb(int prodID) {
        try (Connection conn = DataBaseManager.dataSource.getConnection()) {
            String sql = "DELETE FROM cart WHERE id="+prodID;
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        int getID(int id){
            try (Connection conn = DataBaseManager.dataSource.getConnection()) {
                String sql = " SELECT id FROM cart WHERE product_id='" + id + "' LIMIT 1";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getInt(1);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


}
