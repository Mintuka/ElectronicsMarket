package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Create extends CommonAttributes {

    private DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference firstName,lastName,Email,bankName,accountNo,houseNo,Deposit;
    private EditText uFirstName,uLastName,uEmail,uPassword,uBankName,uAccountNo,uHouseNo,uDeposit;
    private ProgressBar createProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        createProgress = findViewById(R.id.progressBarCreate);

        uFirstName = (EditText) findViewById(R.id.create_First_Name);
        uLastName  = (EditText) findViewById(R.id.create_Last_Name);
        uEmail     = (EditText) findViewById(R.id.ET_user);
        uPassword  = (EditText) findViewById(R.id.ET_password);
        uBankName  = (EditText) findViewById(R.id.Enter_Bank_Name);
        uAccountNo = (EditText) findViewById(R.id.Enter_Account_no);
        uHouseNo   = (EditText) findViewById(R.id.Enter_House_no);
        uDeposit   = (EditText) findViewById(R.id.Enter_Deposit);
        mAuth      = FirebaseAuth.getInstance();
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

    public boolean checkFormFields() {
        String email, password;
        System.out.println("Entered");
        email = uEmail.getText().toString();
        password = uPassword.getText().toString();
        Log.i("email",email);
        if (email.isEmpty()) {
            uEmail.setError("Email Required");
            Toast.makeText( Create.this, "email required", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.isEmpty()) {
            uPassword.setError("Password Required");
            return false;
        }
        return true;
    }



    private void createUserAccount() {
        if (!checkFormFields())
            return;
        createProgress.setVisibility(View.VISIBLE);
        String email = uEmail.getText().toString().trim();
        String password = uPassword.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
    String UserID  = dataRef.push().getKey();
    String First_Name_Value = uFirstName.getText().toString();
    String Last_Name_Value  = uLastName.getText().toString();
    String Email_Value      = uEmail.getText().toString();
    String Bank_Name_Value  = uBankName.getText().toString();
    String Account_no_Value = uAccountNo.getText().toString();
    String House_no_Value   = uHouseNo.getText().toString();
    String Deposit_Value    = uDeposit.getText().toString();

    firstName = dataRef.child(UserID).child("(1)FirstName");
    lastName  = dataRef.child(UserID).child("(2)LastName");
    Email     = dataRef.child(UserID).child("(3)Email");
    bankName  = dataRef.child(UserID).child("(4(BankName");
    accountNo = dataRef.child(UserID).child("(5)AccountNo");
    houseNo   = dataRef.child(UserID).child("(6)HouseNo");
    Deposit   = dataRef.child(UserID).child("(7)Deposit");

        firstName.setValue(First_Name_Value);
        lastName.setValue(Last_Name_Value);
        Email.setValue(Email_Value);
        bankName.setValue(Bank_Name_Value);
        accountNo.setValue(Account_no_Value);
        houseNo.setValue(House_no_Value);
        Deposit.setValue(Deposit_Value);
        createProgress.setVisibility(View.INVISIBLE);
        gotoPay();
                            Toast.makeText( Create.this, "User Was Created", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(Create.this, "Account creation failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if(e instanceof FirebaseAuthUserCollisionException)
                    uEmail.setError("email is already in use");
                else{
                    uEmail.setError(e.getLocalizedMessage());
                }
            }
        });
    }

    public void gobacktomain(View view) {
           goToWelcome();
    }
    public void create_and_pay(View view) {
        createUserAccount();
    }
}
