package com.humna.projectsmd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    PaymentSheet paymentSheet;
    String paymentIntentClientSecret;
    PaymentSheet.CustomerConfiguration configuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        fetchApi();
        Button button= findViewById(R.id.stripe);
        button.setOnClickListener((new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Log.d("DEBUG", "paymentIntentClientSecret: " + paymentIntentClientSecret);
                if (paymentIntentClientSecret != null && !paymentIntentClientSecret.isEmpty()) {
                    paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret,
                            new PaymentSheet.Configuration(" Humna", configuration));
                } else {
                    Toast.makeText(getApplicationContext(), "API LOADING...", Toast.LENGTH_SHORT).show();
                }
            }
        }));

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResults);

    }
private void onPaymentSheetResults(final PaymentSheetResult paymentSheetResult){
        if(paymentSheetResult instanceof PaymentSheetResult.Canceled){
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    if(paymentSheetResult instanceof PaymentSheetResult.Failed){
        Toast.makeText(this, ((PaymentSheetResult.Failed) paymentSheetResult).getError().getMessage(), Toast.LENGTH_SHORT).show();
    }
    if(paymentSheetResult instanceof PaymentSheetResult.Completed){
       // fetchApi();
        Toast.makeText(this, "Payment Successfull", Toast.LENGTH_SHORT).show();
    }
}

    public void fetchApi(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://localhost:63343/stripe_andriod_api/index.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("API_RESPONSE", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("DEBUG", "Customer: " + jsonObject.getString("customer"));
                            Log.d("DEBUG", "Ephemeral Key: " + jsonObject.getString("ephemeralKey"));
                            Log.d("DEBUG", "Payment Intent: " + jsonObject.getString("paymentIntent"));
                            Log.d("DEBUG", "Publishable Key: " + jsonObject.getString("publishableKey"));

                            configuration = new PaymentSheet.CustomerConfiguration(
                                    jsonObject.getString("customer"),
                                    jsonObject.getString("ephemeralKey")
                            );
                            paymentIntentClientSecret = jsonObject.getString("paymentIntent");
                            PaymentConfiguration.init(getApplicationContext(), jsonObject.getString("publishableKey"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON_ERROR", e.getMessage());
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("API_ERROR", error.toString());

            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("authKey", "abc");
                return paramV;
            }
        };
        queue.add(stringRequest);
    }

}