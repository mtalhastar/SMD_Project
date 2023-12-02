package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LoginScreen extends AppCompatActivity {

    TextView login, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        
        Intent intent = new Intent(LoginScreen.this, ActivityProduct.class);
        login = findViewById(R.id.loginButton1);
        login.setOnClickListener(v -> startActivity(intent));
        signup = findViewById(R.id.signupButton2);
        signup.setOnClickListener(v -> startActivity(new Intent(LoginScreen.this, SignupScreen.class)));
    }
}