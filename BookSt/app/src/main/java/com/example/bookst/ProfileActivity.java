package com.example.bookst;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    private EditText name;
    private EditText phone;
    private EditText email;
    private FirebaseAuth mAuth;
    private ImageView img;
    private static final String KEY_TITLE = "First Name";
    private static final String KEY_PHONE = "Phone No";
    private static final String KEY_EMAIL = "Email";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_fragment);
        img = findViewById(R.id.imageView);
        img.setImageResource(R.drawable.ic_baseline_person_24);
        name = findViewById(R.id.txtFname);
        phone = findViewById(R.id.txtPhone);
        email = findViewById(R.id.editTextTextEmailAddress);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String id = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.document("users/"+id.toString());
        userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name1 = documentSnapshot.getString(KEY_TITLE);
                            String phone1 = documentSnapshot.getString(KEY_PHONE);
                            String email1 = documentSnapshot.getString(KEY_EMAIL);
                            name.setText(name1);
                            phone.setText(phone1);
                            email.setText(email1);
                            name.setEnabled(false);
                            phone.setEnabled(false);
                            email.setEnabled(false);
                        } else {
                           // Toast.makeText(MainActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      //  Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, e.toString());
                    }
                });
    }
}
