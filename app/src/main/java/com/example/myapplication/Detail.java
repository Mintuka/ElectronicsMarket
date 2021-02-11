package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Detail extends CommonAttributes {

    private EditText editTextAdd;
    private TextView textView;
    private DatabaseReference dataRef;
    private DatabaseReference dataRead;
    private FirebaseUser user;
    private Button buttonAdd;
    private String UserId;
    private String value;
    private TextView LaptopsInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        System.out.println(UserId+"check");
        textView = findViewById(R.id.show);
        buttonAdd = findViewById(R.id.Submmit_Deposit);
        editTextAdd = findViewById(R.id.Add_deposit_edit);
        TextView LaptopsTitle = findViewById(R.id.titleDetail);
        TextView LaptopsPrice = findViewById(R.id.priceDetail);
        LaptopsInfo  = findViewById(R.id.info);
        ImageView LaptopsImage = findViewById(R.id.sportsImageDetail);
        LaptopsTitle.setText(getIntent().getStringExtra("title"));
        LaptopsPrice.setText(getIntent().getStringExtra("price"));
        LaptopsInfo.setText(getIntent().getStringExtra("info"));
        Glide.with(this).load(getIntent().getIntExtra("image_resource",0))
                .into(LaptopsImage);
                dataRead = FirebaseDatabase.getInstance().getReference().child("-M-9aK7bgwWRr5IBGPgd");
        // Read from the database
        dataRead.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                value = String.valueOf(dataSnapshot.getValue());
                textView.setText(value);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dataRef = FirebaseDatabase.getInstance().getReference().child("-M-9aK7bgwWRr5IBGPgd").child("(7)Deposit");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener((mAuthListener));
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void login() {
        goToSignIn();
    }

    public void purchase(View view) {
        if (user != null) {
            LaptopsInfo.setText(UserId);
            Toast.makeText(Detail.this,"PURCHASED",Toast.LENGTH_LONG).show();
            int intValue = Integer.parseInt(value);
            String itemPrice = getIntent().getStringExtra("price");
            int itemInteger = Integer.parseInt(itemPrice);
            intValue = intValue - itemInteger;
            String newValue = Integer.toString(intValue);
            if(intValue > itemInteger) {
                dataRef.setValue(newValue);
                textView.setText(getIntent().getStringExtra("price")+" Payed");
            }
            else{
                textView.setText("Deposit is insufficient");
            }
        }
        else {
            login();
            Toast.makeText(Detail.this, "goto_Sign_in", Toast.LENGTH_SHORT).show();
        }
    }

    public void gobacktomain(View view) {
       gotoPay();
    }

    public void signOut(View view) {
        mAuth.signOut();
        Intent gotomain = new Intent(this,MainActivity.class);
        startActivity(gotomain);
    }

    public void addDeposit(View view) {
        String add_Deposit = editTextAdd.getText().toString();
        dataRef.setValue(add_Deposit);
    }

    public void showVisibility(View view) {
          if(user != null) {
              editTextAdd.setVisibility(View.VISIBLE);
              buttonAdd.setVisibility(View.VISIBLE);
          }
          else {
              Toast.makeText(Detail.this, "Sign In First", Toast.LENGTH_SHORT).show();
              login();
          }
          }
}
