package com.example.smd_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

public class FireBaseUtil {

    public static void signUp(Context context, String username,String email, String password){
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            saveUser(username, mAuth.getUid());
                            Toast.makeText(context, "Signup success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context,ActivityProduct.class);
                            context.startActivity(intent);
                        } else {

                            Toast.makeText(context, "Signup failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void cashOnDelivery(Context context,String city,String phone,String price,String postalCode,String homeaddress) {
                // Create a Map to store key-value pairs

        if (city == null || city.trim().isEmpty()) {
            // City is empty or contains only whitespace, handle this case (show a message, return, etc.)
            Toast.makeText(context, "City cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate the phone field
        if (phone == null || phone.trim().isEmpty()) {
            Toast.makeText(context, "Phone cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate the price field
        if (price == null || price.trim().isEmpty()) {
            Toast.makeText(context, "Price cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate the postalCode field
        if (postalCode == null || postalCode.trim().isEmpty()) {
            Toast.makeText(context, "Postal Code cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate the homeaddress field
        if (homeaddress == null || homeaddress.trim().isEmpty()) {
            Toast.makeText(context, "Home Address cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
                Map<String, Object> productData = new HashMap<>();
                productData.put("city", city);
                productData.put("homeaddress", homeaddress);
                productData.put("phone", phone);
                productData.put("postalCode", postalCode);
                productData.put("price", price);
                productData.put("status", "In Progress");
                productData.put("userId", FirebaseAuth.getInstance().getUid());
                productData.put("items", Cart.getInstance().createOrder());


                // Add the Map to the "icecreams" collection
                FirebaseFirestore.getInstance().collection("orders").add(productData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Data added successfully

                                Toast.makeText(context, "Order placed Successfully", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, MyOrdersActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure to add data
                                Toast.makeText(context, "Failed to add order to Firestore", Toast.LENGTH_SHORT).show();
                            }
                        });
            }



    public static FirebaseUser getUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }
    public static void Logout(Context context){
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(context, LoginScreen.class);
        context.startActivity(intent);


    }

    public void addProduct(ProductModel product) {
        FirebaseFirestore.getInstance().collection("icecreams").add(product)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            // Product added successfully
                            DocumentReference documentReference = task.getResult();
                            if (documentReference != null) {
                                String productId = documentReference.getId();
                                // You can handle success here, e.g., display a success message
                                // or perform any other actions needed.
                                Log.d("FirestoreHelper", "Product added with ID: " + productId);
                            }
                        } else {
                            // Handle errors
                            Log.e("FirestoreHelper", "Error adding product", task.getException());
                        }
                    }
                });
    }
    public static void getMessages(Context context, final DataCallback callback) {

        if (NetworkUtil.isDeviceOnline(context)) {
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            FirebaseFirestore.getInstance().setFirestoreSettings(settings);

            CollectionReference messagesRef = FirebaseFirestore.getInstance().collection("icecreams");
            messagesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        // Handle the error
                        callback.onFailure(e.getMessage());
                        return;
                    }
                    ArrayList<ProductModel> list=new ArrayList<>();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        ProductModel chatMessage = new ProductModel(
                                document.getString("category"),
                                document.getString("description"),
                                document.getString("imageUrl"),
                                document.getString("longDescription"),
                                document.getString("name"),
                                document.getString("price")
                        );

                        list.add(chatMessage);
                    }

                    // Save data and return the list through the callback
                    saveData(context, list);
                    callback.onSuccess(list);
                }
            });
        } else {
            ArrayList<ProductModel> list=new ArrayList<>();
            list = retrieveData(context);
            callback.onSuccess(list);
        }
    }



    public static void getOrders(Context context, final DataCallbackOrders callback) {

        if (NetworkUtil.isDeviceOnline(context)) {
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();
            FirebaseFirestore.getInstance().setFirestoreSettings(settings);

            CollectionReference messagesRef = FirebaseFirestore.getInstance().collection("orders");
            messagesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        // Handle the error
                        callback.onFailure(e.getMessage());
                        return;
                    }
                    ArrayList<OrderModel> list=new ArrayList<>();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        OrderModel chatMessage = new OrderModel(
                                document.get("city").toString(),
                                document.get("homeaddress").toString(),
                                document.get("phone").toString(),
                                document.get("postalCode").toString(),
                                document.get("price").toString(),
                                document.get("status").toString(),
                                document.get("userId").toString()
                               );

                        if(chatMessage.getUserId().equalsIgnoreCase(FirebaseAuth.getInstance().getUid())) {
                            chatMessage.setItems(document.get("items").toString());
                            list.add(chatMessage);
                            saveOrder(context, list);
                        }
                    }

                    // Save data and return the list through the callback
                    callback.onSuccess(list);
                }
            });
        } else {
            ArrayList<OrderModel> list=new ArrayList<>();
            list = retrieveOrder(context);
            callback.onSuccess(list);
        }
    }

    public interface DataCallback {
        void onSuccess(ArrayList<ProductModel> list);
        void onFailure(String errorMessage);
    }

    public interface DataCallbackOrders {
        void onSuccess(ArrayList<OrderModel> list);
        void onFailure(String errorMessage);
    }


    public static void saveData(Context context, ArrayList<ProductModel> dataList) {
        SharedPreferences preferences = context.getSharedPreferences("products", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(dataList);

        editor.putString("prod", json);
        editor.apply();
    }

    public static void saveOrder(Context context, ArrayList<OrderModel> dataList) {
        SharedPreferences preferences = context.getSharedPreferences("orders", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(dataList);

        editor.putString("order", json);
        editor.apply();
    }

    public static ArrayList<ProductModel> retrieveData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("products", Context.MODE_PRIVATE);
        String json = preferences.getString("prod", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ProductModel>>() {}.getType();
            return gson.fromJson(json, type);
        }

        return new ArrayList<>();
    }

    public static ArrayList<OrderModel> retrieveOrder(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("orders", Context.MODE_PRIVATE);
        String json = preferences.getString("order", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<OrderModel>>() {}.getType();
            return gson.fromJson(json, type);
        }

        return new ArrayList<>();
    }

    public static void saveUser(String username,String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("user");

        // Check if the user already exists
        usersRef.document(username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            // User already exists, handle accordingly
                            // For example, you can update the existing user or notify the caller
                            return;
                        } else {
                            // User does not exist, proceed to add
                            addUserToFirestore(usersRef, username,uid);
                        }
                    } else {
                        // Handle the exception if the document retrieval fails
                        Exception exception = task.getException();
                        if (exception != null) {
                            // Handle the exception
                        }
                    }
                });
    }

    private static void addUserToFirestore(CollectionReference usersRef, String username,String uid) {
        // Use the username as the document ID
        DocumentReference userDocRef = usersRef.document(username);
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("uid", uid);
        // Add the user to the Firestore collection
        userDocRef.set(userData) // You can replace HashMap with a proper user model
                .addOnSuccessListener(aVoid -> {

                })
                .addOnFailureListener(e -> {
                    // Handle the error if adding the user fails
                });
    }

    public static void login(Context context, String email, String password){
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Signin success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context,ActivityProduct.class);
                            context.startActivity(intent);
                        } else {

                            Toast.makeText(context, "Signin failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
