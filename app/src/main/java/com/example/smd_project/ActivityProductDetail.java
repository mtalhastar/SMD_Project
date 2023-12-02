package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ActivityProductDetail extends AppCompatActivity {

    ImageView backButton;
    Button addToCart;
    ImageView heartButton;  // Added ImageView for heart button
    ImageView productImage;  // Added ImageView for product image
    TextView productName;  // Added TextView for product name
    TextView productPrice;  // Added TextView for product price
    TextView productDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        backButton = findViewById(R.id.imageView2);
        addToCart = findViewById(R.id.addToCartButton);
        heartButton = findViewById(R.id.imageView3);
        productImage = findViewById(R.id.imageView);
        productName = findViewById(R.id.textView);
        productPrice = findViewById(R.id.textView2);
        productDescription = findViewById(R.id.textView3);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        String name = intent.getStringExtra("Name");
        String longDescription = intent.getStringExtra("LongDescription");
        String shortDescription = intent.getStringExtra("shortDescription");
        String price=intent.getStringExtra("price");
        productPrice.setText("$"+price);
        // Now you can use the data as needed
        productName.setText(name);
        productDescription.setText(longDescription);
        Picasso.get().load(imageUrl).into(productImage);

        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favourite.getInstance().addToFavourite(new ProductModel("", shortDescription, imageUrl, longDescription, name, price
                ));

                Intent intent = new Intent(ActivityProductDetail.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart.getInstance().addProduct(new ProductModel("",shortDescription,imageUrl,longDescription,name,price));
                if(Cart.getInstance().getProductModels().size()!=0) {
                    Intent intent = new Intent(ActivityProductDetail.this, ActivityCart.class);
                    startActivity(intent);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityProductDetail.this, ActivityProduct.class);
                startActivity(intent);
            }
        });
        // Initializing views

    }
}