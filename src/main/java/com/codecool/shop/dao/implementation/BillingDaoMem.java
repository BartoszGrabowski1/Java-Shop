package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.database.DataBaseManager;
import com.codecool.shop.model.Billing;

import java.sql.*;

public class BillingDaoMem {

    public static void add(Billing billing){
        try (Connection conn = DataBaseManager.dataSource.getConnection()) {
            String sql = "INSERT INTO user_billing (phone, city, street, zip_code, user_id) VALUES (?, ?, ?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, billing.getPhone());
            statement.setString(2, billing.getCity());
            statement.setString(3, billing.getStreet());
            statement.setInt(4, billing.getZipCode());
            statement.setInt(5, billing.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Billing findByUserId(int id) {
        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "SELECT id,city,street,zip_code,phone,user_id FROM user_billing WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            Billing billing = new Billing(rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6));
            billing.setId(rs.getInt(1));
            return billing;
        } catch (SQLException e) {
            return null;
        }
    }

    public static void updateBilling(Billing billing){
        try (Connection connection = DataBaseManager.dataSource.getConnection()) {
            String sql = "UPDATE user_billing SET city=?,street=?,zip_code=?,phone=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, billing.getCity());
            statement.setString(2, billing.getStreet());
            statement.setInt(3, billing.getZipCode());
            statement.setInt(4,billing.getPhone());
            statement.setInt(5, billing.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating player info", e);
        }

    }
}
