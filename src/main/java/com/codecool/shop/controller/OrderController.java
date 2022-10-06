package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrdersDao;
import com.codecool.shop.dao.implementation.OrdersDaoMem;
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

        HttpSession session = req.getSession();

        OrdersDao ordersDaoStore = OrdersDaoMem.getInstance();
        ordersDaoStore.setData(session.getAttribute("userId").toString());
        OrdersService ordersService = new OrdersService(ordersDaoStore);
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("ordersList", ordersService.getOrders());
        context.setVariable("name", session.getAttribute("name"));
        engine.process("product/order.html", context, resp.getWriter());


    }

}
