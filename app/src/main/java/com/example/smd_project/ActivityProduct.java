package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityProduct extends AppCompatActivity {

    RelativeLayout productCard, productCard2, productCard3, productCard4;
    ImageButton home, cart, favorite, order;
    TextView textView;
    TextView textView3;
    TextView textView1;
    EditText searchField;
    ProductAdapter adapter;
    ArrayList<ProductModel> productModels=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        textView=findViewById(R.id.textView1);
        textView.setOnClickListener(v ->FireBaseUtil.Logout(this));
        textView=findViewById(R.id.textView2);
        textView.setText("");
        searchField=findViewById(R.id.searchField);
        searchField.getText();
        searchField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        filter(s.toString());
                    }
                }
        );
        textView3=findViewById(R.id.textView3);
        textView3.setOnClickListener(v -> startActivity(new Intent(this, AddProductActivity.class)));

        FireBaseUtil.getMessages(this, new FireBaseUtil.DataCallback() {
            @Override
            public void onSuccess(ArrayList<ProductModel> list) {
                productModels = list;
                // Update the UI or perform any other action with the retrieved data
                GridView gridView = findViewById(R.id.gridView);
                adapter = new ProductAdapter(ActivityProduct.this, productModels);
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
    private void filter(String search) {
        ArrayList<ProductModel> productmodel =new ArrayList<>();

        for (int i = 0; i < productModels.size(); i++) {
            if (productModels.get(i).getChocolato().toLowerCase().contains(search.toLowerCase())) {

                productmodel.add(productModels.get(i));
            }
        }
        adapter.filterList(productmodel);
    }

}