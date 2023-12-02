package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;  // Import TextView

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {

    ImageButton home, cart, favorite, order;
    ImageView back;
    ArrayList<OrderModel> items=new ArrayList<>();

    OrderAdapter adapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        recyclerView = findViewById(R.id.orderrecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyOrdersActivity.this));
        adapter=new OrderAdapter(items, MyOrdersActivity.this);
        recyclerView.setAdapter(adapter);
        FireBaseUtil.getOrders(this, new FireBaseUtil.DataCallbackOrders() {
            @Override
            public void onSuccess(ArrayList<OrderModel> list) {
                items.clear(); // Clear the existing items in the list
                items.addAll(list); // Add the new items to the list
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(String errorMessage) {
                // Handle failure, e.g., show an error message
            }
        });



        Intent intent = new Intent(this, ActivityProduct.class);
        back = findViewById(R.id.orderBack);
        back.setOnClickListener(v -> startActivity(intent));

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
