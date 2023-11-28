package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SignupScreen extends AppCompatActivity {

    TextView login, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        Intent intent = new Intent(SignupScreen.this, LoginScreen.class);
        login = findViewById(R.id.loginButton2);
        login.setOnClickListener(v -> startActivity(intent));
        signup = findViewById(R.id.signupButton1);
        signup.setOnClickListener(v -> startActivity(new Intent(SignupScreen.this, ActivityProduct.class)));
    }
}