package com.codecool.shop.model;

import java.sql.Timestamp;

public class Order extends BaseModel{
    private Timestamp ordered_at;
    private String status;
    private int user_id;

    public Order(Timestamp ordered_at, String status, int user_id) {
        super();
        this.ordered_at = ordered_at;
        this.status = status;
        this.user_id = user_id;
    }

    public Timestamp getOrdered_at() {
        return ordered_at;
    }

    public void setOrdered_at(Timestamp ordered_at) {
        this.ordered_at = ordered_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ordered_at=" + ordered_at +
                ", status='" + status + '\'' +
                ", user_id=" + user_id +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
