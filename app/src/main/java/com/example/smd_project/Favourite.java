package com.example.smd_project;

import java.util.ArrayList;

public class Favourite {

    private ArrayList<ProductModel> productModels = new ArrayList<>();
    private static Favourite instance;

    private Favourite() {
        // Private constructor to prevent instantiation.
    }

    public static Favourite getInstance() {
        if (instance == null) {
            synchronized (Cart.class) {
                if (instance == null) {
                    instance = new Favourite();
                }
            }
        }
        return instance;
    }
    public ArrayList<ProductModel> getFavourite() {
        return productModels;
    }

    public void setProductModels(ArrayList<ProductModel> productModels) {
        this.productModels = productModels;
    }


    public void addToFavourite(ProductModel productModel) {
        boolean exists = false;

        for (ProductModel existingProduct : productModels) {
            if (existingProduct.getChocolato().equalsIgnoreCase(productModel.getChocolato())) {
                existingProduct.increaseQuantity();
                exists = true;
                break;
            }
        }

        if (!exists) {
            productModels.add(productModel);
        }
    }

}
