package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView login, signup;
    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Intent intent = new Intent(LoginScreen.this, ActivityProduct.class);
        login = findViewById(R.id.loginButton1);
        login.setOnClickListener(v -> FireBaseUtil.login(LoginScreen.this,email.getText().toString(),password.getText().toString()));
        signup = findViewById(R.id.signupButton2);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signup.setOnClickListener(v -> startActivity(new Intent(LoginScreen.this,SignupScreen.class)));
    }
}