package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrdersDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrdersDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.service.CartService;
import com.codecool.shop.service.OrdersService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "OrderServlet", urlPatterns = {"/order"})
public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartDaoStore = CartDaoMem.getInstance();
        CartService cartService = new CartService(cartDaoStore);
        HttpSession session = req.getSession();
        Object id = session.getAttribute("id");
        System.out.println(id.toString());

        OrdersDao ordersDaoStore = OrdersDaoMem.getInstance();
        ordersDaoStore.setData(id.toString());
        OrdersService ordersService = new OrdersService(ordersDaoStore);
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("ordersList", ordersService.getOrders());
        context.setVariable("ordersMapOrderIdToListProducts", ordersDaoStore.getMapProduct());
        context.setVariable("name", session.getAttribute("name"));
        System.out.println("test");
        System.out.println(ordersDaoStore.getMapProduct());

        engine.process("product/order.html", context, resp.getWriter());
    }

}
