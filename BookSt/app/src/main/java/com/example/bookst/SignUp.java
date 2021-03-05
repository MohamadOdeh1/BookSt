package com.example.bookst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {
    private EditText txtMail;
    private EditText txtPass;
    private EditText txtPhone;
    private EditText txtFname;
    private EditText txtLname;
    private Button btnSignUp;
    private String emailA;
    private String passwordA;
    private String userId;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtMail = (EditText) findViewById(R.id.emailF);
        txtPass = (EditText) findViewById(R.id.passwordF);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        txtFname = (EditText) findViewById(R.id.firstName);
        txtPhone = (EditText) findViewById(R.id.phoneNo);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignUp(txtMail.getText().toString(),txtPass.getText().toString());
            }
        });
    }
    private void btnSignUp(String email,String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            userId = user.getUid();
                            String name = txtFname.getText().toString();
                            String phoneNo = txtPhone.getText().toString();
                            String emailAd = txtMail.getText().toString();
                            DocumentReference documentReference = fstore.collection("users").document(userId);
                            Map<String,Object> user1 = new HashMap<>();
                            user1.put("First Name",name);
                            user1.put("Phone No",phoneNo);
                            user1.put("Email",emailAd);
                            documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SignUp.this, "Saved",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(SignUp.this, MainActivity.class));

                                }
                            });

                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }
}