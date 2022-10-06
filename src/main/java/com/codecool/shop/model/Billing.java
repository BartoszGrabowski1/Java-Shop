package com.codecool.shop.model;

public class Billing {
    private int id;
    private String city;
    private String street;
    private int zipCode;
    private int phone;

    private int userId;

    public Billing(String city, String street, int zipCode, int phone, int userId) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.phone = phone;
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public int getPhone() {
        return phone;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
