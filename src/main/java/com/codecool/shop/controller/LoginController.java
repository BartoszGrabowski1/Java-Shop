package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name="loginServlet", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("loginStatus", httpSession.getAttribute("loginStatus"));
        engine.process("product/login.html", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        if(UserDaoMem.findByEmail(req.getParameter("login_email"))==null){
            httpSession.setAttribute("loginStatus", false);
            resp.sendRedirect(req.getContextPath()+"/login");
        }else{
            User user = UserDaoMem.findByEmail(req.getParameter("login_email"));
            if(Objects.equals(req.getParameter("login_password"), user.getPassword())){
                httpSession.setAttribute("name", "Developer");
                httpSession.setAttribute("userId", UserDaoMem.findByEmail(req.getParameter("registration_email")).getId());
                resp.sendRedirect(req.getContextPath()+"/");
            }
            else{
                httpSession.setAttribute("loginStatus", false);
                resp.sendRedirect(req.getContextPath()+"/login");
            }
        }
    }
}
