package com.example.smd_project;
import java.util.ArrayList;

public class Cart {
    private ArrayList<ProductModel> productModels = new ArrayList<>();
    private static Cart instance;

    private Cart() {
        // Private constructor to prevent instantiation.
    }

    public static Cart getInstance() {
        if (instance == null) {
            synchronized (Cart.class) {
                if (instance == null) {
                    instance = new Cart();
                }
            }
        }
        return instance;
    }
    public ArrayList<ProductModel> getProductModels() {
        return productModels;
    }

    public void setProductModels(ArrayList<ProductModel> productModels) {
        this.productModels = productModels;
    }

    public String calculatePrice(){
        int price = 0;

        for (ProductModel existingProduct : productModels) {
            price += Integer.parseInt( existingProduct.getPrice()) * Integer.parseInt(existingProduct.getQuantity());
        }

        // Convert the total price to String
        return String.valueOf(price);
    }
    public String createOrder(){
       String order="";

        for (ProductModel existingProduct : productModels) {
            order +=existingProduct.getChocolato()+" "+ "$"+ existingProduct.getPrice()+" X "+existingProduct.getQuantity().toString()+" = "+Integer.parseInt( existingProduct.getPrice()) * Integer.parseInt(existingProduct.getQuantity())+"\n";
        }

        // Convert the total price to String
        return order;
    }

    public void addProduct(ProductModel productModel) {
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


    // Other methods for manipulating the cart go here
}
