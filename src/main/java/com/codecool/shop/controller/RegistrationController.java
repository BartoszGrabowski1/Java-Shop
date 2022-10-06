package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.BillingDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
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
import java.util.Objects;


@WebServlet(name="registrationServlet", urlPatterns = {"/registration"})
public class RegistrationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession httpSession = req.getSession();
        context.setVariable("registerStatus", httpSession.getAttribute("registerStatus"));
        engine.process("product/registration.html", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        if(UserDaoMem.findByEmail(req.getParameter("registration_email"))==null){
            if(Objects.equals(req.getParameter("registration_password"), req.getParameter("repeat_password"))){
                User user =new User( req.getParameter("registration_email"), req.getParameter("registration_password"),req.getParameter("registration_name"));
                UserDaoMem.add(user);
                user.setBilling(BillingDaoMem.findByUserId(user.getId()));
                httpSession.setAttribute("userId", UserDaoMem.findByEmail(req.getParameter("registration_email")).getId());
                httpSession.setAttribute("User", user);
                httpSession.setAttribute("cartSize",CartController.cartSIze(user.getId()));
                httpSession.setAttribute("name", user.getName());
                resp.sendRedirect(req.getContextPath()+"/");
            }
        }
        else{
            httpSession.setAttribute("registerStatus", false);
            resp.sendRedirect(req.getContextPath()+"/registration");
        }
    }
}