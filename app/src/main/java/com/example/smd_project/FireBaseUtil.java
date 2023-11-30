package com.example.smd_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.HashMap;

public class FireBaseUtil {

    public static void signUp(Context context, String username,String email, String password){
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveUser(username);
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


    public static String getUserId(){
        return FirebaseAuth.getInstance().getUid();
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

    public interface DataCallback {
        void onSuccess(ArrayList<ProductModel> list);
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

    public static ArrayList<ProductModel> retrieveData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("products", Context.MODE_PRIVATE);
        String json = preferences.getString("prod", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            return gson.fromJson(json, type);
        }

        return new ArrayList<>();
    }

    public static void saveUser(String username) {
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
                            addUserToFirestore(usersRef, username);
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

    private static void addUserToFirestore(CollectionReference usersRef, String username) {
        // Use the username as the document ID
        DocumentReference userDocRef = usersRef.document(username);

        // Add the user to the Firestore collection
        userDocRef.set(new HashMap<>()) // You can replace HashMap with a proper user model
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
}
