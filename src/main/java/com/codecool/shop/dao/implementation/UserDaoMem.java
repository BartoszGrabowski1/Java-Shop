package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.database.DataBaseManager;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoMem  {

    public static void add(User user){
        try (Connection conn = DataBaseManager.dataSource.getConnection()) {
            String sql = "INSERT INTO shop_user (name, mail, password) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static User find(int id) {
        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT id,name,mail,password FROM user WHERE mail = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            User user = new User(rs.getString(2),rs.getString(3),rs.getString(4));
            user.setId(rs.getInt(1));
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static User findByEmail(String email) {
        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT id,name,mail,password FROM shop_user WHERE mail = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            rs.next();
            User user = new User(rs.getString(3),rs.getString(4),rs.getString(2));
            user.setId(rs.getInt(1));
            return user;

        } catch (SQLException e) {
            return  null;
        }
    }

    public static void updateUserInfo(User user){
        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "UPDATE shop_user SET name=?, mail=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setInt(3,user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating player info", e);
        }

    }
}
