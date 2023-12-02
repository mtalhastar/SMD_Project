package com.example.smd_project;


import java.util.List;

public class OrderModel {
    private String city;
    private String homeaddress;
    private String phone;
    private String postalCode;
    private String price;
    private String status;
    private String userId;
    private String items;

    // Constructor
    public OrderModel(String city, String homeaddress, String phone, String postalCode, String price, String status, String userId) {
        this.city = city;
        this.homeaddress = homeaddress;
        this.phone = phone;
        this.postalCode = postalCode;
        this.price = price;
        this.status = status;
        this.userId = userId;
    }

    // Getter and Setter methods
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
