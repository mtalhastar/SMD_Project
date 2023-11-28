package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class FavoritesActivity extends AppCompatActivity {

    ImageButton home, cart, favorite, order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        home = findViewById(R.id.home);
        home.setOnClickListener(v -> startActivity(new Intent(this, ActivityProduct.class)));
        cart = findViewById(R.id.cart);
        cart.setOnClickListener(v -> startActivity(new Intent(this, ActivityCart.class)));
        favorite = findViewById(R.id.favorite);
        favorite.setOnClickListener(v -> startActivity(new Intent(this, FavoritesActivity.class)));
        order = findViewById(R.id.order);
        order.setOnClickListener(v -> startActivity(new Intent(this, MyOrdersActivity.class)));
    }
}