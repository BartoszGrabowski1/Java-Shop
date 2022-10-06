package com.codecool.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

    public String getOrdered_at() {
        String s = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(ordered_at);
        return s;
    }

    public String getStatus() {
        return status;
    }
    public BigDecimal sumOfProducts(){
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Product product : productsList) {
            sum = sum.add(product.getDefaultPrice());
        }
        return sum;
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
