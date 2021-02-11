package com.example.myapplication;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CommonAttributes extends AppCompatActivity {
    //Common Fields
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;

    //Common Methods
    public void gotoPay(){
        Intent gotopay = new Intent(this,MainActivity.class);
        startActivity(gotopay);
    }
    public void goToWelcome(){
        Intent gobacktomain = new Intent(this,Welcome.class);
        startActivity(gobacktomain);
    }
    public void goToCreate(){
        Intent intentCreate = new Intent(this, Create.class);
        startActivity(intentCreate);
    }
    public void goToSignIn(){
        Intent gotosignin = new Intent(this, Signin.class);
        startActivity(gotosignin);
    }

}
