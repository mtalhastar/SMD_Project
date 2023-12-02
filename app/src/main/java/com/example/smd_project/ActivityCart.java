package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityCart extends AppCompatActivity {

    ImageView back;
    Button checkout;
    ArrayList<ProductModel> items=new ArrayList<>();
    CartAdapter adapter;
    RecyclerView recyclerView;

    TextView summaryprice;

    TextView totalsummaryprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        items=Cart.getInstance().getProductModels();
        Log.d("items", String.valueOf(items.size()));
        recyclerView = findViewById(R.id.recyclerid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        summaryprice=findViewById(R.id.summaryprice);
        totalsummaryprice=findViewById(R.id.summarytotalprice);
        adapter=new CartAdapter(items, this, new CartAdapter.CartListener() {
            @Override
            public void onItemQuantityChanged() {

                summaryprice.setText("$"+Cart.getInstance().calculatePrice());
                totalsummaryprice.setText("$"+ Integer.parseInt(Cart.getInstance().calculatePrice())*0.90);
            }
        });
        recyclerView.setAdapter(adapter);


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.removeitem(position);
                if(adapter.getItemCount()==0){
                    summaryprice.setText("$"+Cart.getInstance().calculatePrice());
                    totalsummaryprice.setText("$"+ Integer.parseInt(Cart.getInstance().calculatePrice())*0.90);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Intent intent = new Intent(this, ActivityProduct.class);
        back = findViewById(R.id.cartBack);
        back.setOnClickListener(v -> startActivity(intent));
        checkout = findViewById(R.id.proceedButton);
        checkout.setOnClickListener(v -> startActivity(new Intent(this, CheckoutActivity.class)));






    }
}