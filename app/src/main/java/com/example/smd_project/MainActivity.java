package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, navigate to the home screen
            startActivity(new Intent(this, ActivityProduct.class));
            finish(); // Optional: Close the current activity if needed
        }
        textView = findViewById(R.id.getStartedButton);
        Intent intent = new Intent(this, LoginScreen.class);
        textView.setOnClickListener(v -> startActivity(intent));
    }
}