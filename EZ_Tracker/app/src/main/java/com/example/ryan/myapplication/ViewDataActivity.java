package com.example.ryan.myapplication;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewDataActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    private TextView name;
    private TextView weight;
    private TextView height;
    private TextView dob;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        getDatabase();
        findAllViews();
        showInfo();

        Button backButton = findViewById(R.id.viewBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDataActivity.this, SettingsActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void findAllViews() {
        name = findViewById(R.id.viewName);
        weight = findViewById(R.id.viewWeight);
        height = findViewById(R.id.viewHeight);
        dob = findViewById(R.id.viewDob);

    }

    private void showInfo() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        DatabaseReference namer = FirebaseDatabase.getInstance().getReference(userID);


        namer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String realName = dataSnapshot.child("name").getValue(String.class);
                String realWeight = dataSnapshot.child("weight").getValue(String.class);
                String realHeight = dataSnapshot.child("height").getValue(String.class);
                String realdob = dataSnapshot.child("dob").getValue(String.class);

                name.setText("Name: " + realName);
                weight.setText("Weight: " + realWeight + " kg");
                height.setText("Height: " + realHeight + " cm");
                dob.setText("Date of Birth: " + realdob);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewDataActivity.this, "Data unavailable, please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ViewDataActivity.this, SettingsActivity.class);
        finish();
        startActivity(intent);
    }

    private void getDatabase() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(ViewDataActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        }

    }
}
