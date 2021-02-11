package com.example.myapplication;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Signin extends CommonAttributes implements View.OnClickListener {
    private final String TAG = "FB_SIGNIN";
    private ProgressBar signInProgess;
    private Button Submmit;
    private EditText etEmail,etPassword,SubmmitEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        findViewById(R.id.btnsignin).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.ETuser).setOnClickListener(this);

        signInProgess = findViewById(R.id.progressBar);
        etEmail = (EditText) findViewById(R.id.ETuser);
        etPassword = (EditText) findViewById(R.id.ETpassword);
        SubmmitEmail = (EditText) findViewById(R.id.submitemail);
        Submmit = (Button) findViewById(R.id.btnSubmmit);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {

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


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsignin:
                signUserIn();
                break;
            case R.id.register:
                gotocreate(v);
                break;
            case R.id.ETuser:
                Toast.makeText(Signin.this, "Signed In", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public boolean checkFormFields() {
        String email, password;

        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email Required");
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password Required");
            return false;
        }
        return true;
    }

    private void signUserIn() {
        if (!checkFormFields())
            return;
        signInProgess.setVisibility(View.VISIBLE);
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                     signInProgess.setVisibility(View.INVISIBLE);
                     gotoPay();
                    Toast.makeText(Signin.this, "Signed In", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Signin.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if(e instanceof FirebaseAuthInvalidCredentialsException)
                    etPassword.setError("Invalid Password");
                else if (e instanceof FirebaseAuthInvalidUserException)
                    etEmail.setError("No account with this email");
                else{
                    etEmail.setError(e.getLocalizedMessage());
                }
            }
        });
    }

    public void gotocreate(View view) {
        goToCreate();
    }

    public void gobacktomain(View view) {
        goToWelcome();
    }

    public void Reset_Password(View view) {
        SubmmitEmail.setVisibility(View.VISIBLE);
        Submmit.setVisibility(View.VISIBLE);
    }

    public void submmit(View view) {
        String emailAddress = SubmmitEmail.getText().toString();

        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            SubmmitEmail.setVisibility(View.INVISIBLE);
                            Submmit.setVisibility(View.INVISIBLE);
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
}
