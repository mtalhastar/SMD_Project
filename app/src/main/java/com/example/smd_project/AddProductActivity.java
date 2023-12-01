package com.example.smd_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    ImageView checkoutBack ;
    // IDs for elements in the second LinearLayout
    EditText editTextName;
    EditText category ;
    EditText description;
    EditText details ;
    EditText price;
    Button addImageButton ;
    Button addButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        storageReference = FirebaseStorage.getInstance().getReference("images");
        db = FirebaseFirestore.getInstance();
        // IDs for elements in the first LinearLayout
         checkoutBack = findViewById(R.id.checkoutBack);
         checkoutBack.setOnClickListener(view ->  startActivity(new Intent(AddProductActivity.this,ActivityProduct.class)));
        // IDs for elements in the second LinearLayout
         editTextName = findViewById(R.id.editTextName);
         category = findViewById(R.id.category);
         description = findViewById(R.id.desciption);
         details = findViewById(R.id.details);
         price = findViewById(R.id.price);

        Button addButton = findViewById(R.id.button1);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }
        });

        // TODO: Add any other elements you want to reference here
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Upload the image to Firebase Storage
            uploadImage();
        }
    }

    private void uploadImage() {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Handle successful upload
                            Toast.makeText(AddProductActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();

                            // Save the image URL to Firestore
                            saveImageUrl(fileReference);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle unsuccessful upload
                            Toast.makeText(AddProductActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveImageUrl(StorageReference fileReference) {
        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Save the download URL to Firestore
                String imageUrls = uri.toString();

                // Create a Map to store key-value pairs
                Map<String, Object> productData = new HashMap<>();
                productData.put("category", category.getText().toString());
                productData.put("description", description.getText().toString());
                productData.put("imageUrl", imageUrls);
                productData.put("longDescription", details.getText().toString());
                productData.put("name", editTextName.getText().toString());
                productData.put("price", price.getText().toString());

                // Add the Map to the "icecreams" collection
                db.collection("icecreams").add(productData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Data added successfully
                                Toast.makeText(AddProductActivity.this, "Data added to Firestore", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddProductActivity.this,ActivityProduct.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure to add data
                                Toast.makeText(AddProductActivity.this, "Failed to add data to Firestore", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private String getFileExtension(Uri uri) {
        // Get the file extension of the selected image
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }
}
