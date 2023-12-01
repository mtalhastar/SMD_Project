package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupScreen extends AppCompatActivity {
    TextView login, signup;
    EditText email,password,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        Intent intent = new Intent(SignupScreen.this, LoginScreen.class);
        login = findViewById(R.id.loginButton2);
        login.setOnClickListener(v -> startActivity(intent));
        signup = findViewById(R.id.signupButton1);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        if(username.equals("") || email.equals("") || password.equals("")){
            Toast.makeText(this,"Some of the fields are empty",Toast.LENGTH_LONG).show();
        }else {
            signup.setOnClickListener(v -> FireBaseUtil.signUp(SignupScreen.this, username.getText().toString(), email.getText().toString(), password.getText().toString()));
        }
    }
}