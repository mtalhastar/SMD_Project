package com.example.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    ImageButton home, cart, favorite, order;
    ImageView back;

    Button button;

    PaymentSheet paymentSheet;
    String paymentIntentClientSecret;
    PaymentSheet.CustomerConfiguration configuration;


    private EditText editTextName, editTextCity, editTextPostalCode, editTextPhone;
    private Button cashOnDeliveryButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        fetchApi();
        editTextName = findViewById(R.id.editTextName);
        editTextCity = findViewById(R.id.editTextCity);
        editTextPostalCode = findViewById(R.id.editTextPostalCode);
        editTextPhone = findViewById(R.id.editTextPhone);


        button = findViewById(R.id.pay_now);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paymentIntentClientSecret != null) {
                    paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret,
                            new PaymentSheet.Configuration("Talha", configuration));
                }
            }
        });

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

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

    private void onPaymentSheetResult (final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }
        if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(this, ((PaymentSheetResult.Failed) paymentSheetResult).getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
            FireBaseUtil.cashOnDelivery(this,editTextCity.getText().toString(),editTextPhone.getText().toString(),Cart.getInstance().calculatePrice(),editTextPostalCode.getText().toString(),editTextName.getText().toString());

        }
    }

    public void fetchApi() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2/stripe/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            configuration = new PaymentSheet.CustomerConfiguration(
                                    jsonObject.getString("customer"),
                                    jsonObject.getString("ephemeralKey")
                            );
                            paymentIntentClientSecret = jsonObject.getString("paymentIntent");
                            PaymentConfiguration.init(getApplicationContext(), jsonObject.getString("publishableKey"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("authKey", "abc");
                paramV.put("amount", "200");
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}