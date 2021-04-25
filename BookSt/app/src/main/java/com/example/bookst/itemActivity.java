package com.example.bookst;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.google.android.gms.common.util.CollectionUtils.mapOf;

public class itemActivity extends AppCompatActivity {
    private TextView bookNm;
    private TextView bookyear;
    private TextView bookPub;
    private TextView bookLc;
    ActionBar actionBar;
    private TextView bookAuth;
    private TextView textPho;
    private FirebaseAuth mAuth;
    private Button btnGet;
    private ImageView img;
    FirebaseDatabase  database;
    DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference();
        bookNm = findViewById(R.id.textName);
        bookyear = findViewById(R.id.textYear);
        bookPub = findViewById(R.id.yexyPub);
        textPho = findViewById(R.id.txtPhone11);
        bookLc = findViewById(R.id.textLc);
        bookAuth = findViewById(R.id.textAuth);
        btnGet = findViewById(R.id.btnOrder);
        //img = findViewById(R.id.imgVip);
        if(MainActivity.bookD.isVip()){
            //img.setImageResource(R.drawable.pp);
        }
        bookNm.setText(MainActivity.bookD.getBookName().toString());
        bookAuth.setText(MainActivity.bookD.getAuthor().toString());
        bookPub.setText(MainActivity.bookD.getPublisherName().toString());
        bookLc.setText(MainActivity.bookD.getLocation().toString());
        bookyear.setText(MainActivity.bookD.getPublishingYear().toString());
        textPho.setText(MainActivity.phoneme);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = MainActivity.bookD.getId();
                mDatabaseRef.child("books").child(id).child("available").setValue("false");
                MainActivity.bb.add(MainActivity.bookD);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(user.getUid())
                        .update("Books",MainActivity.bb);
                MainActivity.books.remove(MainActivity.itemNo);
                btnGet.setEnabled(false);
                startDialog();
            }
        });
    }

    private void startDialog() {
        DialogCont dg = new DialogCont();
        dg.show(getSupportFragmentManager(), "Information");
    }
}