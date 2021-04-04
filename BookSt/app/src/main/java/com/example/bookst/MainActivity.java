package com.example.bookst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ListView list;
    private TextView textMail;
    private TextView textName;
    private FirebaseAuth mAuth;
    public static List<BookData> books;
    private DatabaseReference reference2;
    private FirebaseAuth firebaseAuth1;
    public static String bookName;
    public static String author;
    public static String publisher;
    public static String pubYear;
    public static String location;
    private String email;
    private String name;
    public static List<BookData> bb;
    public static BookData bookD;
    public static String phoneme;
    public static int itemNo;
    public static List<Map<String, Object>> bob;
    public static final String KEY_PHONO = "Phone No";
    public static final String KEY_NAME = "First Name";
    BookData bookData;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        textMail = header.findViewById(R.id.mailPro);
        textName = header.findViewById(R.id.namePro);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.document("users/"+user.getUid().toString());
        userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            bb = (List<BookData>) documentSnapshot.get("Books");
                            phoneme = documentSnapshot.getString(KEY_PHONO);
                            name = documentSnapshot.getString(KEY_NAME);
                            textMail.setText(user.getEmail().toString());
                            textName.setText(name);
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
        firebaseAuth1 = FirebaseAuth.getInstance();
        reference2 = FirebaseDatabase.getInstance().getReference().child("books");
        list = (ListView) findViewById(R.id.listU);
        bookData = new BookData();
        books = new ArrayList<BookData>();
        books.clear();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new LendFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_add);
        */
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_add:
                startActivity(new Intent(MainActivity.this,LendFragment.class));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                break;
            //case R.id.nav_books:
                //startActivity(new Intent(MainActivity.this,MyBooksActivity.class));
            case R.id.nav_log:
                firebaseAuth1.signOut();
                finish();

                break;
            /*case R.id.nav_rate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;*/
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                books.clear();
                for(DataSnapshot bookSnapShot : dataSnapshot.getChildren() ){
                    String name = bookSnapShot.child("bookName").getValue(String.class);
                    String loc = bookSnapShot.child("location").getValue(String.class);
                    String year = bookSnapShot.child("publishingYear").getValue(String.class);
                    String pub = bookSnapShot.child("publisherName").getValue(String.class);
                    String bId = bookSnapShot.child("id").getValue(String.class);
                    String auth = bookSnapShot.child("author").getValue(String.class);
                    String isAvailable  = bookSnapShot.child("available").getValue(String.class);
                    if(Boolean.valueOf(isAvailable)) {
                        books.add(new BookData(name, auth, pub, year, bId, loc, isAvailable, phoneme));
                    }
                }
                BookList adapter = new BookList(MainActivity.this, books);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        bookD = (BookData) adapterView.getAdapter().getItem(i);
                        itemNo = i;
                        startActivity(new Intent(MainActivity.this,itemActivity.class));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}