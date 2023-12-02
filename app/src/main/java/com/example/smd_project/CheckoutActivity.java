package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CheckoutActivity extends AppCompatActivity {

    ImageButton home, cart, favorite, order;
    ImageView back;


    private EditText editTextName, editTextCity, editTextPostalCode, editTextPhone;
    private Button cashOnDeliveryButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        editTextName = findViewById(R.id.editTextName);
        editTextCity = findViewById(R.id.editTextCity);
        editTextPostalCode = findViewById(R.id.editTextPostalCode);
        editTextPhone = findViewById(R.id.editTextPhone);



        cashOnDeliveryButton = findViewById(R.id.button1);
        cashOnDeliveryButton.setOnClickListener(v -> {
            FireBaseUtil.cashOnDelivery(this,editTextCity.getText().toString(),editTextPhone.getText().toString(),Cart.getInstance().calculatePrice(),editTextPostalCode.getText().toString(),editTextName.getText().toString());
        });

        Intent intent = new Intent(this, ActivityProduct.class);
        back = findViewById(R.id.checkoutBack);
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