package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.BillingDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.Billing;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.lang.Integer.parseInt;

@WebServlet(name="billingServlet", urlPatterns = {"/billing"})
public class BillingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession httpSession = req.getSession();
        context.setVariable("User", httpSession.getAttribute("User"));
        context.setVariable("edit", true);
        context.setVariable("cartSize",session.getAttribute("cartSize"));
        engine.process("product/user.html", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("User");
        Billing billing;
        if(user.getBilling() == null){
            billing = new Billing(req.getParameter("user_city"), req.getParameter("user_street"), parseInt(req.getParameter("user_zip")), parseInt(req.getParameter("user_phone")), user.getId());
            user.setBilling(billing);
            BillingDaoMem.add(billing);
        }else{
            billing = user.getBilling();
            BillingDaoMem.updateBilling(billing);
        }
        user.setName(req.getParameter("user_name"));
        user.setEmail(req.getParameter("user_mail"));
        UserDaoMem.updateUserInfo(user);
        context.setVariable("edit", false);
        context.setVariable("cartSize",session.getAttribute("cartSize"));
        resp.sendRedirect(req.getContextPath()+"/user");
    }
}
