package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class ActivityProduct extends AppCompatActivity {

    RelativeLayout productCard, productCard2, productCard3, productCard4;
    ImageButton home, cart, favorite, order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = new Intent(this, ActivityProductDetail.class);
        productCard = findViewById(R.id.productCard);
        productCard.setOnClickListener(v -> startActivity(intent));
        productCard2 = findViewById(R.id.productCard2);
        productCard2.setOnClickListener(v -> startActivity(intent));
        productCard3 = findViewById(R.id.productCard3);
        productCard3.setOnClickListener(v -> startActivity(intent));
        productCard4 = findViewById(R.id.productCard4);
        productCard4.setOnClickListener(v -> startActivity(intent));
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