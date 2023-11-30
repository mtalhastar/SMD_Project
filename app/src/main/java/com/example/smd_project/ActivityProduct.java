package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ActivityProduct extends AppCompatActivity {

    RelativeLayout productCard, productCard2, productCard3, productCard4;
    ImageButton home, cart, favorite, order;
    ArrayList<ProductModel> productModels=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        FireBaseUtil.getMessages(this, new FireBaseUtil.DataCallback() {
            @Override
            public void onSuccess(ArrayList<ProductModel> list) {
                productModels = list;
                // Update the UI or perform any other action with the retrieved data
                GridView gridView = findViewById(R.id.gridView);
                ProductAdapter adapter = new ProductAdapter(ActivityProduct.this, productModels);
                gridView.setAdapter(adapter);

            }
            @Override
            public void onFailure(String errorMessage) {
                // Handle failure, e.g., show an error message
            }
        });
//        GridView gridView = findViewById(R.id.gridView);
//        ProductAdapter adapter = new ProductAdapter(this,productModels);
//        gridView.setAdapter(adapter);
//        productCard.setOnClickListener(v -> startActivity(intent));

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