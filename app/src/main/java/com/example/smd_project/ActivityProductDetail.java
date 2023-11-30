package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class ActivityProductDetail extends AppCompatActivity {
    ImageView backButton;
    Button addToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = new Intent(this, ActivityProduct.class);
        backButton = findViewById(R.id.imageView2);
        backButton.setOnClickListener(v -> startActivity(intent));
        addToCart = findViewById(R.id.addToCartButton);
        addToCart.setOnClickListener(v -> startActivity(new Intent(this, ActivityCart.class)));
    }
}