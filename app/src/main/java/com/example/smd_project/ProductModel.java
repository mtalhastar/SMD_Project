package com.example.smd_project;

public class ProductModel {

String category;
String description;

int quantity=1;

    public String getQuantity() {
        return String.valueOf(this.quantity);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(){
        if(quantity>=0){
            this.quantity++;
        }
    }

    public void decreaseQuantity(){
        if(quantity>0){
            this.quantity--;
        }
    }

    public ProductModel(String category, String description, String imageUrl, String longDescription, String chocolato, String price) {
        this.category = category;
        this.description = description;
        this.imageUrl = imageUrl;
        this.longDescription = longDescription;
        Chocolato = chocolato;
        this.price = price;

    }

    String imageUrl;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getChocolato() {
        return Chocolato;
    }

    public void setChocolato(String chocolato) {
        Chocolato = chocolato;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String longDescription;
String Chocolato;
String price;



}
