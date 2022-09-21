package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.service.CartService;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.service.SupplierService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartDaoStore = CartDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        HttpSession session = req.getSession();

        SupplierService supplierService = new SupplierService(supplierDataStore);
        ProductService productService = new ProductService(productDataStore,productCategoryDataStore);
        CartService cartService = new CartService(cartDaoStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        filterCategoriesAndSuppliers(req, supplierService, productService, context);


        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierService.getAllSuppliers());
        context.setVariable("name", session.getAttribute("name"));
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }

    private static void filterCategoriesAndSuppliers(HttpServletRequest req, SupplierService supplierService, ProductService productService, WebContext context) {
        if ((req.getParameter("categoryId") != null) && (req.getParameter("supplierId") == null)) {
            int category_id = Integer.parseInt(req.getParameter("categoryId"));
            context.setVariable("supplier", null);
            context.setVariable("category", productService.getProductCategory(category_id));
            context.setVariable("products", productService.getProductsForCategory(category_id));
        } else if ((req.getParameter("categoryId") == null) && (req.getParameter("supplierId") != null)) {
            int supplier_id = Integer.parseInt(req.getParameter("supplierId"));
            context.setVariable("category", null);
            context.setVariable("supplier", supplierService.getSupplier(supplier_id));
            context.setVariable("products", productService.getProductsFromSupplier(supplier_id));
        } else if ((req.getParameter("categoryId") != null) && (req.getParameter("supplierId") != null)){
            int category_id = Integer.parseInt(req.getParameter("categoryId"));
            int supplier_id = Integer.parseInt(req.getParameter("supplierId"));
            context.setVariable("category", productService.getProductCategory(category_id));
            context.setVariable("supplier", supplierService.getSupplier(supplier_id));
            context.setVariable("products", productService.getProductsFromSupplierAndCategory(category_id,supplier_id));
        } else {
            context.setVariable("supplier", null);
            context.setVariable("category", null);
            context.setVariable("products", productService.getAllProducts());
        }

    }

}
