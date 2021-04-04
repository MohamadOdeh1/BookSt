package com.example.bookst;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LendFragment extends AppCompatActivity {
    private EditText txtBookName;
    private EditText txtAuthor;
    private EditText txtPublisher;
    private Spinner location;
    private Spinner publishYear;
    private Button btnDone;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String id;
    int k = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lend_fragment);
        txtBookName = (EditText) findViewById(R.id.bookName);
        txtAuthor = (EditText) findViewById(R.id.bookAuthor);
        txtPublisher = (EditText) findViewById(R.id.bookPublisher);
        publishYear = (Spinner) findViewById(R.id.publishYear);
        location = (Spinner) findViewById(R.id.bookLoc);
        btnDone = (Button) findViewById(R.id.btnDone);
        Integer[] years = new Integer[122];
        for(int i = 1900; i <= 2021; i++){
            years[k] = i;
            k++;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, years);
        publishYear.setAdapter(adapter);
        String[] locations = new String[3];
        locations [0] = "Iksal";
        locations [1] = "Kfar Kanna";
        locations [2] = "Tel Aviv";
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, locations);
        location.setAdapter(adapter1);
        reference = FirebaseDatabase.getInstance().getReference("books");
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtBookName.getText().toString();
                String author = txtAuthor.getText().toString();
                String publisher = txtPublisher.getText().toString();
                String loc = location.getSelectedItem().toString();
                String year = publishYear.getSelectedItem().toString();
                id = reference.push().getKey();
                BookData bd = new BookData(name,author,publisher,year,id,loc,"true");
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("books");
                reference.child(id).setValue(bd);

            }
        });
    }
}