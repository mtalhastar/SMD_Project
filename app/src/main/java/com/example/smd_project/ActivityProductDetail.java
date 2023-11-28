package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class ActivityProductDetail extends AppCompatActivity {

    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = new Intent(this, ActivityProduct.class);
        backButton = findViewById(R.id.imageView2);
        backButton.setOnClickListener(v -> startActivity(intent));
    }
}