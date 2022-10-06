package com.codecool.shop.model;

import java.sql.Timestamp;
import java.util.List;

public class Order extends BaseModel{
    private Timestamp ordered_at;
    private String status;
    private int user_id;
    private List<Product> productsList;

    public Order(Timestamp ordered_at, String status, int user_id, List<Product> productsList) {
        this.ordered_at = ordered_at;
        this.status = status;
        this.user_id = user_id;
        this.productsList = productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    @Override
    public String toString() {
        return "Order{" +
                ", order_id=" + id +
                ", ordered_at=" + ordered_at +
                ", status='" + status + '\'' +
                ", user_id=" + user_id +
                ", productsList=" + productsList +
                '}';
    }
}
