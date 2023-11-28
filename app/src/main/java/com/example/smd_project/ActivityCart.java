package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class ActivityCart extends AppCompatActivity {

    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = new Intent(this, ActivityProduct.class);
        back = findViewById(R.id.cartBack);
    }
}