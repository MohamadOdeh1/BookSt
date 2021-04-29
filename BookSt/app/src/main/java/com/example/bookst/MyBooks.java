package com.example.bookst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyBooks extends AppCompatActivity {
    private ListView mybooks;
    public static List books;
    private BookList adapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books2);
        mybooks = findViewById(R.id.myBooks);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
    }
    protected void onStart(){
        super.onStart();

       // books = new ArrayList<BookData>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.document("users/"+user.getUid().toString());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                books = (List) documentSnapshot.get("Books");
            }
        });
    }
}