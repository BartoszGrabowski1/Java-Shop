package com.codecool.shop.dao.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {
    public static Properties loadProperties(){
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/connection.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            System.out.println(properties);
            return properties;
        } catch (IOException exception) {
            System.out.println("Error while reading properties.");
        }
        return null;
    }
}
