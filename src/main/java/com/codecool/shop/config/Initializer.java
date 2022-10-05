package com.codecool.shop.config;
import com.codecool.shop.dao.database.DataBaseManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.sql.SQLException;

@WebListener
public class Initializer implements ServletContextListener {
    public static DataBaseManager dataBaseManager = new DataBaseManager();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            dataBaseManager.setup();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
