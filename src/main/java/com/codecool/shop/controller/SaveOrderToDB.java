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
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "SaveOrderToDB", urlPatterns = {"/save_order"})
public class SaveOrderToDB extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartDaoStore = CartDaoMem.getInstance();
        CartService cartService = new CartService(cartDaoStore);
        HttpSession session = req.getSession();
        String idUser = session.getAttribute("userId").toString();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("cart", cartService.getSelectedProducts());
        context.setVariable("value", cartService.getValue());
        context.setVariable("name", session.getAttribute("name"));
        System.out.println("test user");
        System.out.println(Integer.parseInt(idUser));
        serviceOrder(Integer.parseInt(idUser));
        resp.sendRedirect(req.getContextPath()+"/order");
//        engine.process("product/order.html", context, resp.getWriter());
    }

    Instant instant = Instant.now();
    Timestamp sqlTimestamp = Timestamp.from(instant);

    private void serviceOrder(int userId) {
        saveOrderToDB(userId);
        int orderId = getOrderId(userId);
        saveOrderedProductsToDB(userId);
        deleteCartInDB(userId);
    }

    void saveOrderToDB(int userId) {
        try (Connection conn = DataBaseManager.dataSource.getConnection()) {
            String sql = "INSERT INTO public.order (ordered_at,status,user_id) VALUES (?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, sqlTimestamp);
            statement.setString(2, "pay");
            statement.setInt(3, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    void deleteCartInDB(int userId) {
        try (Connection conn = DataBaseManager.dataSource.getConnection()) {
            String sql = "DELETE FROM cart WHERE user_id='" + userId + "'";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    void saveOrderedProductToDB(int productId, int orderId) {
        try (Connection conn = DataBaseManager.dataSource.getConnection()) {
            String sql = "INSERT INTO public.ordered_products (product_id, order_id) VALUES (?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, productId);
            statement.setInt(2, orderId);
            statement.executeUpdate();
            System.out.println("DUPA");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private List<Integer> ListIdProducts(int userId) {
        System.out.println("dupa user");
        System.out.println(userId);
        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT  product_id FROM public.cart WHERE user_id=" +userId;
            ResultSet resultSetOrder = connection.createStatement().executeQuery(sql);
            List<Integer> result = new ArrayList<>();
            int number;

            while (resultSetOrder.next()) {
                number = resultSetOrder.getInt(1);
                result.add(number);
            }
            System.out.println("dupawwwwwww");
            System.out.println(result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void saveOrderedProductsToDB(int orderId) {
        List<Integer> list = ListIdProducts(orderId);
        System.out.println("dupa2");
        System.out.println(list.size());
        for (Integer integer : list) {
            saveOrderedProductToDB(integer, orderId);
        }
    }

    private int getOrderId(int userId) {
        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT  MAX(id) FROM public.order WHERE user_id='" + userId + "'";
            ResultSet resultSetOrder = connection.createStatement().executeQuery(sql);
            int result = 0;
            while (resultSetOrder.next()) {
                result += resultSetOrder.getInt(1);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
