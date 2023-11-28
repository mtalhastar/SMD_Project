package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LoginScreen extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Intent intent = new Intent(LoginScreen.this, ActivityProduct.class);
        textView = findViewById(R.id.loginButton1);
        textView.setOnClickListener(v -> startActivity(intent));
    }
}